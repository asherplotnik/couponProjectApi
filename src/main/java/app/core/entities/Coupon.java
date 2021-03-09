package app.core.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * 
 * Coupon class for database access
 * @author ASHER
 *

 *
 */
@Entity
public class Coupon {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private int categoryId;
	private String title; 
	private String description;
	private LocalDate startDate;
	private LocalDate endDate;
	private int amount;
	private double price;
	private String image;
	@ManyToOne
	private Company company;
	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinTable(name = "coupon_customer", joinColumns = @JoinColumn(name = "coupon_id"), inverseJoinColumns = @JoinColumn(name = "customer_id"))
	List<Customer> customers;
	
	public Coupon(int id, int category_id, String title, String description, LocalDate startDate,
			LocalDate endDate, int amount, double price, String image) {
		this.id = id;
		this.categoryId = category_id;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
	}
	public Coupon(int category_id, String title, String description, LocalDate start_date,
			LocalDate end_date, int amount, double price, String image) {
		this.categoryId = category_id;
		this.title = title;
		this.description = description;
		this.startDate = start_date;
		this.endDate = end_date;
		this.amount = amount;
		this.price = price;
		this.image = image;
	}
	
	public Coupon(int id) {
		this.id = id;
	}
	
	public Coupon() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int category_id) {
		this.categoryId = category_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	

	public List<Customer> getCustomers() {
		return customers;
	}
	
	public void addCustomer(Customer customer) {
		if (this.customers == null) {
			this.customers = new ArrayList<>();
		}
		customers.add(customer);
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result + categoryId;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + id;
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coupon other = (Coupon) obj;
		if (id != other.id)
			return false;
		return true;
	}
	

	@Override
	public String toString() {
		return "ID: " + id + " , COMPANY ID: " + company.getId() + ", CATEGORY ID: " + categoryId + " , TITLE: " + title
				+ " , DESCRIPTION: " + description + " , START DATE: " + startDate + " , END DATE: " + endDate + " , AMOUNT: "
				+ amount + " , PRICE: " + price + " , IMAGE: " + image;
	}
	
	
	
	
	
	
}
