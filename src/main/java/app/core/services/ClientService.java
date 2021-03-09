package app.core.services;

import org.springframework.beans.factory.annotation.Autowired;

import app.core.couponProjectExceptions.DaoException;
import app.core.repositories.CompanyRepository;
import app.core.repositories.CouponRepository;
import app.core.repositories.CustomerRepository;

public abstract class ClientService {
	@Autowired
	protected CustomerRepository customerRepository;
	@Autowired
	protected CouponRepository couponRepository;
	@Autowired
	protected CompanyRepository companyRepository;
	
	public ClientService(String username, String password) throws DaoException {
	}
	
	public ClientService() {
	}


	
	
	
	
}
