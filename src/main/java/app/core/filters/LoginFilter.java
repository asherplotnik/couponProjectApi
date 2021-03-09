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
import app.core.sessions.SessionContext;

public class LoginFilter implements Filter{
	
	private SessionContext sessionContext;

	public LoginFilter(SessionContext sessionContext) {
		super();
		this.sessionContext = sessionContext;
	}


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;	
		String method = req.getMethod();	
		String token = req.getHeader("token");
		String acrh = req.getHeader("access-control-request-headers");
		if (token != null && sessionContext.getSession(token)!=null) {
			System.out.println("LOGIN FILTER PASS-------------");
			chain.doFilter(request, response);
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
