package app.core.security;

import java.util.function.Function;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {
	
	private String signatureAlgorithm = SignatureAlgorithm.HS256.getJcaName();
	
	@Value("${jwt.token}")
	private String encodedSecretKey;
	private Key decodedSecretKey; 
	@PostConstruct
	private void init()
	{
		decodedSecretKey = new SecretKeySpec(Base64.getDecoder().decode(encodedSecretKey),
				this.signatureAlgorithm);
	}
	
	public JwtUtil() {
		
	}
		
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	public String extractName(String token) {
		final Claims claims = extractAllClaims(token);
		return (String) claims.get("Name");
	}

	public int extractId(String token) {
		final Claims claims = extractAllClaims(token);
		return (int) claims.get("UserId");
	}
	
	public int extractUserType(String token) {
		final Claims claims = extractAllClaims(token);
		return (int) claims.get("UserType");
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}
	

	private Claims extractAllClaims(String token) throws ExpiredJwtException {
		JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(this.decodedSecretKey).build();
		return jwtParser.parseClaimsJws(token).getBody();
	}

	public Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	public String generateToken(String userEmail, String name , int userType, int id) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("Name", name);
		claims.put("UserId", id);
		claims.put("UserType", userType);
		return createToken(claims, userEmail,name ,userType, id);
	}
	
	private String createToken(Map<String, Object> claims, String subject, String name, int userType, int id) {
		Instant now = Instant.now(); 
		return Jwts.builder().setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(Date.from(now))
				.setExpiration(Date.from(now.plus(10, ChronoUnit.HOURS))).signWith(this.decodedSecretKey)
				.signWith(decodedSecretKey)
				.compact();
	}
	
	public Boolean validateToken(String token, String userEmail) {
		final String username = extractUsername(token);
		return (username.equals(userEmail) && !isTokenExpired(token));
	}
	
}
