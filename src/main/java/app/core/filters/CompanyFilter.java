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

public class CompanyFilter implements Filter {

	private SessionContext sessionContext;

	public CompanyFilter(SessionContext sessionContext) {
		super();
		this.sessionContext = sessionContext;
	}

	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String token = req.getHeader("token");
		if (token != null && (int) sessionContext.getSession(token).getAttribute("usertype") == 1) {
			chain.doFilter(request, response);
		} else {
			System.out.println("COMPANY FILTER FAILL -------------");
			res.sendError(HttpStatus.UNAUTHORIZED.value(), "you are not a Company");
		}
	}

}
