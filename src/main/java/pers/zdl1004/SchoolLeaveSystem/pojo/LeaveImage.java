package pers.zdl1004.SchoolLeaveSystem.pojo;

public class LeaveImage {
	private Integer id;
	private String path;
	private Leave leave;
	
	public LeaveImage() {}
	
	public LeaveImage(Integer id, String path, Leave leave) {
		this.id = id;
		this.path = path;
		this.leave = leave;
	}
	
	@Override
	public String toString() {
		return "LeaveImage [id=" + id + ", path=" + path + ", leave=" + leave + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Leave getLeave() {
		return leave;
	}
	public void setLeave(Leave leave) {
		this.leave = leave;
	}
}
