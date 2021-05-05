package app.core.utils;

public class UserPayload {
	public String token;
	public int userType;
	public String name;
	
	public UserPayload(String token, String name, int userType){
		this.token = token;
		this.name = name;
		this.userType = userType;
	}
}
