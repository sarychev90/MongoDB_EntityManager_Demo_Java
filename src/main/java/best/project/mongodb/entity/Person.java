package best.project.mongodb.entity;

import java.util.List;

public class Person {
	
	private String firstName;
	private String surName;
	private String patronymicName;
	private int age;
	private String proffesion;
	private String maritalStatus;
	private List<String> hobbies;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSurName() {
		return surName;
	}
	public void setSurName(String surName) {
		this.surName = surName;
	}
	public String getPatronymicName() {
		return patronymicName;
	}
	public void setPatronymicName(String patronymicName) {
		this.patronymicName = patronymicName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getProffesion() {
		return proffesion;
	}
	public void setProffesion(String proffesion) {
		this.proffesion = proffesion;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public List<String> getHobbies() {
		return hobbies;
	}
	public void setHobbies(List<String> hobbies) {
		this.hobbies = hobbies;
	}
	@Override
	public String toString() {
		return "Person [firstName=" + firstName + ", surName=" + surName + ", patronymicName=" + patronymicName
				+ ", age=" + age + ", proffesion=" + proffesion + ", maritalStatus=" + maritalStatus + ", hobbies="
				+ hobbies + "]";
	}
}
