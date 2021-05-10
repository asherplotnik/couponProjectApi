package app.core.services;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import app.core.couponProjectExceptions.DaoException;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Customer;

@Service
@Transactional
public class AdminService extends ClientService {
	private boolean loggedIn = false;

//	public AdminService(String email, String password) throws DaoException {
//		if (!login(email,password)) {
//			throw new DaoException("Login admin failed!!!");
//		}
//	}

	public AdminService() {
	}
	@Override
	public boolean login(String email, String password) throws DaoException {
		try {
			if (email.equals("admin@admin.com") && password.equals("admin")) {
				loggedIn = true;
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw new DaoException("Login Failed  !!!");
		}
	}

	public Company addCompany(Company company) throws DaoException {
		try {
			if (company.getName().length() == 0 || company.getEmail().length() == 0) {
			}
			String check = checkDuplicateCompany(company);
			if (check.equals("name")) {
				throw new DaoException("Failed to add - Company name exists already!!!");
			}
			if (check.equals("email")) {
				throw new DaoException("Failed to add - Company email exists already!!!");
			}
			System.out.println("Company saved Successfully");
			return companyRepository.save(company);
		} catch (Exception e) {
			throw new DaoException(e.getLocalizedMessage());
		}
	}

	private String checkDuplicateCompany(Company company) {
		String email = company.getEmail();
		String name = company.getName();

		if (companyRepository.getByName(name) != null) {
			return "name";
		}
		if (companyRepository.getByEmail(email) != null) {
			return "email";
		}
		return "ok";
	}

	public Company deleteCompany(int id) throws DaoException {
		try {
			// database foreign key restrictions on delete cascade will automatically delete
			// all coupons and all purchases
			// with permission from Eldar
			Optional<Company> temp = companyRepository.findById(id);
			if (temp.isEmpty()) {
				throw new DaoException("failed to delete - company id not found");
			}
			companyRepository.deleteById(id);
			System.out.println("Company deleted Successfully");
			return temp.get();
		} catch (Exception e) {
			throw new DaoException(e.getLocalizedMessage());
		}
	}

	public List<Company> getAllCompanies() throws DaoException {
		try {
			return companyRepository.findAll();
		} catch (Exception e) {
			throw new DaoException(e.getLocalizedMessage());
		}
	}

	public Company getOneCompany(int id) throws DaoException {
		try {
			Optional<Company> opt = companyRepository.findById(id);
			if (opt.isPresent()) {
				return opt.get();
			} else {
				throw new DaoException("Company does not exists");
			}
		} catch (Exception e) {
			throw new DaoException(e.getLocalizedMessage());
		}
	}

	public List<Coupon> getCompanyCoupons(int id) throws DaoException {
		try {
			List<Coupon> list = couponRepository.getCouponsByCompanyId(id);
			if (list.size() > 0) {
				return list;
			} else {
				throw new DaoException("coupon list is empty");
			}
		} catch (Exception e) {
			throw new DaoException(e.getLocalizedMessage());
		}
	}

	public Customer addCustomer(Customer customer) throws DaoException {
		try {
			if (customerRepository.getByEmail(customer.getEmail()) == null) {
				System.out.println("Customer added successfuly.");
				return customerRepository.save(customer);
			} else {
				throw new DaoException("Customer's email exists already!!!");
			}
		} catch (Exception e) {
			throw new DaoException(e.getLocalizedMessage());
		}
	}

	public Company updateCompany(Company company) throws DaoException {
		try {
			System.out.println(company);
			Optional<Company> opt = companyRepository.findById(company.getId());
			if (opt.isEmpty()) {
				throw new DaoException("Update Failed - company not found");
			}
			Company companyDb = opt.get();
			if (!companyDb.getName().equals(company.getName())) {
				throw new DaoException("Can not change company name !!!");
			}
			// check if found other company with same email but different id
			Company dupCompany = companyRepository.getByEmail(company.getEmail());
			if (dupCompany != null && dupCompany.getId() != company.getId()) {
				throw new DaoException("Can not change company email - duplicate email found!!!");
			}
			companyDb.setEmail(company.getEmail());
			companyDb.setPassword(company.getPassword());
			System.out.println("Company updated Successfully");
			return companyDb;
		} catch (Exception e) {
			throw new DaoException(e.getLocalizedMessage());
		}
	}

	public Customer updateCustomer(Customer customer) throws DaoException {
		try {
			Optional<Customer> opt = customerRepository.findById(customer.getId());
			if (opt.isEmpty()) {
				throw new DaoException("Update Failed - customer not found");
			}
			Customer customerDb = opt.get();
			// check if found other customer with same email but different id
			Customer dupEmailCustomer = customerRepository.getByEmail(customer.getEmail());
			if (dupEmailCustomer != null && dupEmailCustomer.getId() != customer.getId()) {
				throw new DaoException("Customer's email exists already!!!");
			}
			customerDb.setFirst_name(customer.getFirst_name());
			customerDb.setLast_name(customer.getLast_name());
			customerDb.setEmail(customer.getEmail());
			customerDb.setPassword(customer.getPassword());
			System.out.println("Customer updated successfuly.");
			return customerDb;
		} catch (Exception e) {
			throw new DaoException(e.getLocalizedMessage());
		}
	}

	public Customer deleteCustomer(int id) throws DaoException {
		try {
			// database foreign key restrictions on delete cascade will automatically delete
			// all purchases
			// with permission of Eldar
			Optional<Customer> temp = customerRepository.findById(id);
			if (temp.isEmpty()) {
				throw new DaoException("Delete Failed - Customer not found");
			}
			customerRepository.deleteById(id);
			System.out.println("Customer deleted successfuly.");
			return temp.get();
		} catch (Exception e) {
			throw new DaoException(e.getLocalizedMessage());
		}
	}

	public List<Customer> getAllCustomers() throws DaoException {
		try {
			return customerRepository.findAll();
		} catch (Exception e) {
			throw new DaoException("Failed to get customers !!!");
		}
	}

	public Customer getOneCustomer(int id) throws DaoException {
		try {
			Optional<Customer> opt = customerRepository.findById(id);
			if (opt.isPresent()) {
				return opt.get();
			} else {
				throw new DaoException("Customer does not exists");
			}
		} catch (Exception e) {
			throw new DaoException(e.getLocalizedMessage());
		}
	}

	public List<Coupon> getCustomerCoupons(int id) throws DaoException {
		try {
			List<Coupon> list = couponRepository.getCouponsByCustomersId(id);
			if (list.size() > 0) {
				return list;
			} else {
				throw new DaoException("coupon list is empty");
			}
		} catch (Exception e) {
			throw new DaoException(e.getLocalizedMessage());
		}
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public String getAdminDetails() {
		return ("ADMIN");
	}
}
