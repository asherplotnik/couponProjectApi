package app.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import app.core.couponProjectExceptions.DaoException;
import app.core.entities.JsonPassword;
import app.core.loginManager.LoginManager;
import app.core.services.ClientService;
import app.core.sessions.Session;
import app.core.sessions.SessionContext;
@CrossOrigin
@RestController
public class LoginController {
	
	@Autowired
	private SessionContext sessionContext;
	@Autowired
	ApplicationContext ctx; 
	@PostMapping("/login/{email}/{userType}")
	public String login(@RequestBody JsonPassword password, @PathVariable String email, @PathVariable int userType) {
		LoginManager loginManager = ctx.getBean(LoginManager.class);
		ClientService service;
		try {
			service = loginManager.login(email, password.password, userType);
			Session session = sessionContext.createSession();
			session.setAttribute("email", email);
			session.setAttribute("usertype", userType );
			session.setAttribute("service", service );
			return session.token;
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"login failed password or email invalid");
		}
	}

}
