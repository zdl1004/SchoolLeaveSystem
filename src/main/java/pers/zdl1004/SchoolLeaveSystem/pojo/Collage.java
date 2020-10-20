package pers.zdl1004.SchoolLeaveSystem.pojo;

public class Collage {
	private Integer id;
	private String name;
	
	public Collage() {}

	public Collage(Integer id, String name) {
		this.id = id;
		this.name = name;
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


	@Override
	public String toString() {
		return "Collage [id=" + id + ", name=" + name + "]";
	}


	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof Collage)) {
			return false;
		}
		Collage collage = (Collage)obj;
		if(id == null || collage.id == null) {
			return false;
		}
		return id.equals(collage.id);
	}
	
	@Override
	public int hashCode() {
		if(id == null) {
			return -1;
		}
		return id;
	}



}
