package pers.zdl1004.SchoolLeaveSystem.pojo;

import pers.zdl1004.SchoolLeaveSystem.type.UserType;

public class User {
	private Integer id;
	private String username;
	private String password;
	private UserType type;
	private String name;
	private String telephone;
	private Clazz clazz;
	private String clientToken;
	private String clientId;

	public User() {}

	public User(Integer id, String username, String password, UserType type, String name, String telephone, Clazz clazz, String clientToken, String clientId) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.type = type;
		this.name = name;
		this.telephone = telephone;
		this.clazz = clazz;
		this.clientToken = clientToken;
		this.clientId = clientId;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", type=" + type
				+ ", name=" + name + ", telephone=" + telephone + ",clazz=" + clazz + ", clientToken=" + clientToken + ", clientId="
				+ clientId + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Clazz getClazz() {
		return clazz;
	}

	public void setClazz(Clazz clazz) {
		this.clazz = clazz;
	}

	public String getClientToken() {
		return clientToken;
	}

	public void setClientToken(String clientToken) {
		this.clientToken = clientToken;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
}
