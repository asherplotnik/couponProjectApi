package app.core.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import app.core.security.JwtUtil;

public class TokenFilter implements Filter{
	
	private JwtUtil jwtUtil;

	public TokenFilter(JwtUtil jwtUtil) {
		super();
		this.jwtUtil = jwtUtil;
	}
	
	public void setHeaders(HttpServletResponse res) {
		res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
		res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Headers","*");
        res.setHeader("Access-Control-Expose-Headers","*");
    }
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;	
		String method = req.getMethod();	
		String token = req.getHeader("token");
		String acrh = req.getHeader("access-control-request-headers");
		String url = req.getRequestURI();
			
		
		if (token != null ) {
			try{
				jwtUtil.isTokenExpired(token); 
				int userType = jwtUtil.extractUserType(token);
				if(url.contains("/admin")) {
					if(userType == 0) {
						System.out.println("ADMIN FILTER PASS-------------");
						chain.doFilter(request, response);
					} else {
						System.out.println("ADMIN FILTER FAILL-------------");
						setHeaders(res);
						res.sendError(HttpStatus.UNAUTHORIZED.value(), "you are not an admin");
					}
					
				} else if(url.contains("/company")) {
					if(userType == 1) {
						System.out.println("COMPANY FILTER PASS-------------");
						chain.doFilter(request, response);
					} else {
						System.out.println("COMPANY FILTER FAILL-------------");
						setHeaders(res);
						res.sendError(HttpStatus.UNAUTHORIZED.value(), "you are not a company");
					}
				} else if(url.contains("/customer")){
					if(userType == 2) {
						System.out.println("CUSTOMER FILTER PASS-------------");
						chain.doFilter(request, response);
					} else {
						System.out.println("CUSTOMER FILTER FAILL-------------");
						setHeaders(res);
						res.sendError(HttpStatus.UNAUTHORIZED.value(), "you are not a customer");
					}
				} 
			} catch(Exception e) {
				setHeaders(res);
				res.sendError(HttpStatus.UNAUTHORIZED.value(), "you are not authorized");
			}
			
		} else {
			if(acrh != null && method.equals("OPTIONS")) {
		    	System.out.println("PREFLIGHT-------------");
		    	 res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
		    	 res.setHeader("Access-Control-Allow-Origin", "*");
		         res.setHeader("Access-Control-Allow-Headers","*");
		         res.setHeader("Access-Control-Expose-Headers","*");
		         res.setStatus(HttpStatus.OK.value());
		    } else {
		    	if (url.contains("/api/")) {
		    		System.out.println("LOGIN FILTER FAILL-------------");
		    		res.sendError(HttpStatus.UNAUTHORIZED.value(), "you are not logged in");
		    	} else {
		    		System.out.println("FILTER PASS-------------");
		    		chain.doFilter(request, response);	
		    	}
		    	
		    }
		}
	}
	

}

