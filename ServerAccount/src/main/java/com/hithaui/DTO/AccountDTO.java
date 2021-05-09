package com.hithaui.DTO;

public class AccountDTO {
	
	private String username;
	
	private String password;
	
	private String avt;

	public AccountDTO(String username, String password, String avt) {
		super();
		this.username = username;
		this.password = password;
		this.avt = avt;
	}

	public AccountDTO() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAvt() {
		return avt;
	}

	public void setAvt(String avt) {
		this.avt = avt;
	}
	
	

}
