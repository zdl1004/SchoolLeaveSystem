package pers.zdl1004.SchoolLeaveSystem.pojo;

public class Clazz {
	private Integer id;//id序号
	private Integer no;//班级，1班二班
	private Grade grade;//年级
	private Major major;//专业
	
	public Clazz() {}

	public Clazz(Integer id, Integer no, Grade grade, Major major) {
		super();
		this.id = id;
		this.no = no;
		this.grade = grade;
		this.major = major;
	}
//获取班级全名方法
	public String getFullName() {
		//如：2015级网络工程1班
		return grade.getYear() + "级" + major.getCollage().getName() + major.getName() + no + "班";
	}
	
	@Override
	public int hashCode() {
		if(id != null) {
			return id;
		}
		return -1;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		if(!(obj instanceof Clazz)) {
			return false;
		}
		Clazz clazz = (Clazz) obj;
		if(id == null || clazz.id == null) {
			return false;
		}
		return id.equals(clazz.id);
	}



	/*重写set,get,tostring方法*/
	@Override
	public String toString() {
		return "Class [id=" + id + ", no=" + no + ", grade=" + grade + ", major=" + major + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

}
