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
import app.core.utils.UserPayload;
@CrossOrigin
@RestController
public class LoginController {
	
	@Autowired
	ApplicationContext ctx; 
	@PostMapping("/login/{email}")
	public UserPayload login(@RequestBody JsonPassword password, @PathVariable String email) {
		LoginManager loginManager = ctx.getBean(LoginManager.class);
		try {
			return loginManager.login(email, password.password);
		} catch (DaoException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"LOGIN FAILED - Password or email invalid");
		}
	}

}
