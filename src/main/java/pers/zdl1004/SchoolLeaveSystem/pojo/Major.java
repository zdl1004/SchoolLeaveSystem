package pers.zdl1004.SchoolLeaveSystem.pojo;
//学院、专业
public class Major {
	private Integer id;
	private String name;
	private Collage collage;
	
	public Major() {}

	public Major(Integer id, String name, Collage collage) {
		this.id = id;
		this.name = name;
		this.collage = collage;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof Major)) {
			return false;
		}
		Major major = (Major)obj;
		if(id == null || major.id == null) {
			return false;
		}
		return id.equals(major.id);
	}
	
	@Override
	public int hashCode() {
		if(id == null) {
			return -1;
		}
		return id;
	}

	@Override
	public String toString() {
		return "Major [id=" + id + ", name=" + name + ", collage=" + collage + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collage getCollage() {
		return collage;
	}

	public void setCollage(Collage collage) {
		this.collage = collage;
	}

}
