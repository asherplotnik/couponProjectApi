package app.core.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SpaFilter implements Filter{
	

	public SpaFilter() {
		super();
	}


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;	
		String path = req.getRequestURI();
	    System.out.println("out"+ path +"____________________________");
		if ("/".equals(path)) {
			System.out.println("if"+ path +"____________________________");
	    	chain.doFilter(request, response);
	    	return;
		} else {
			System.out.println("else"+ path +"____________________________");
	    	res.sendRedirect("/");
	    	chain.doFilter(request, res);
	    }
	
	}
	

}

