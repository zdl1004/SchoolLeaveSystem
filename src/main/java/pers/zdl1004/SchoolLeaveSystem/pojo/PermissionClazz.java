package pers.zdl1004.SchoolLeaveSystem.pojo;

public class PermissionClazz {
	private Integer id;
	private User user;
	private Clazz clazz;

	public PermissionClazz(Integer id, User user, Clazz clazz) {
		this.id = id;
		this.user = user;
		this.clazz = clazz;
	}

	public PermissionClazz(){

	}
	@Override
	public String toString() {
		return "PermissionClazz [id=" + id + ", user=" + user + ", clazz=" + clazz + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Clazz getClazz() {
		return clazz;
	}

	public void setClazz(Clazz clazz) {
		this.clazz = clazz;
	}

}
