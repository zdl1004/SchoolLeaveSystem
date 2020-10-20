package pers.zdl1004.SchoolLeaveSystem.pojo;

public class Grade {
	private Integer id;
	private Integer year;

	@Override
	public String toString() {
		return "Grade [id=" + id + ", year=" + year + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

}
