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
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;	
		String method = req.getMethod();	
		String token = req.getHeader("token");
		String acrh = req.getHeader("access-control-request-headers");
		String url = req.getRequestURI();
		
		if(url.contains("/login")){
			System.out.println("LOGIN FILTER PASS-------------");
			chain.doFilter(request, response);
			return;
		}
		
		if (token != null ) {
			if (jwtUtil.isTokenExpired(token)) {
				res.sendError(HttpStatus.UNAUTHORIZED.value(), "you are not uthorized");
			}
			if(req.getRequestURI().contains("/admin")) {
				if(jwtUtil.extractUserType(token)==0) {
					System.out.println("ADMIN FILTER PASS-------------");
					chain.doFilter(request, response);					
				} else {
					System.out.println("ADMIN FILTER FAILL-------------");
					res.sendError(HttpStatus.UNAUTHORIZED.value(), "you are not an admin");
				}
				
			} else if(req.getRequestURI().contains("/company")) {
				if(jwtUtil.extractUserType(token)==1) {
					System.out.println("COMPANY FILTER PASS-------------");
					chain.doFilter(request, response);					
				} else {
					System.out.println("COMPANY FILTER FAILL-------------");
					res.sendError(HttpStatus.UNAUTHORIZED.value(), "you are not a company");
				}
			} else if(req.getRequestURI().contains("/customer")){
				if(jwtUtil.extractUserType(token)==2) {
					System.out.println("CUSTOMER FILTER PASS-------------");
					chain.doFilter(request, response);					
				} else {
					System.out.println("CUSTOMER FILTER FAILL-------------");
					res.sendError(HttpStatus.UNAUTHORIZED.value(), "you are not a customer");
				}
			} else {
				System.out.println("LOGIN FILTER PASS-------------");
				chain.doFilter(request, response);
			}
		} else {
			if(acrh != null && method.equals("OPTIONS")) {
		    	System.out.println("PREFLIGHT-------------");
		    	 res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
		    	 res.setHeader("Access-Control-Allow-Origin", "*");
		         res.setHeader("Access-Control-Allow-Headers","*");
		         res.sendError(HttpStatus.OK.value(), "preflight");
		    } else {
		    	System.out.println("LOGIN FILTER FAILL-------------");
		    	res.sendError(HttpStatus.UNAUTHORIZED.value(), "you are not logged in");
		    }
		}
	}
	

}

