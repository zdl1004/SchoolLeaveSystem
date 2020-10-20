package pers.zdl1004.SchoolLeaveSystem.pojo;

public class PermissionCollage {
	private Integer id;
	private User user;
	private Collage collage;

	public PermissionCollage(Integer id, User user, Collage collage) {
		this.id = id;
		this.user = user;
		this.collage = collage;
	}

	public PermissionCollage(){

	}

	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return "PermissionCollage{" +
				"id=" + id +
				", user=" + user +
				", collage=" + collage +
				'}';
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
	public Collage getCollage() {
		return collage;
	}
	public void setCollage(Collage collage) {
		this.collage = collage;
	}
	
	
}
