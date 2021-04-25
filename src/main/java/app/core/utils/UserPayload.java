package app.core.utils;

public class UserPayload {
	public String token;
	public int userType;
	
	public UserPayload(String token, int userType){
		this.token = token;
		this.userType = userType;
	}
}
