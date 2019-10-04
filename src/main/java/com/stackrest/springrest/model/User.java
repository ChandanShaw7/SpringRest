package com.stackrest.springrest.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**/
@Entity
@Table(name="userinfo")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int Id;
	@NotBlank
	private String userName;
	@NotBlank
//	@JsonIgnore
//	@RestResource(exported = false)
	private String password;
	private long contactNo;
	private String city;
	private int zip;
	

	public User() {
		
	}
	
	public User(int Id, String userName, String password, int contactNo,String city,int zip) {
		this.Id = Id;
		this.userName = userName;
		this.password = password;
		this.contactNo = contactNo;
		this.city = city;
		this.zip = zip;
	}
	
	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}
	
	public long getContactNo() {
		return contactNo;
	}
	public void setContactNo(long contactNo) {
		this.contactNo = contactNo;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUsername(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}
	@Override
    public String toString() {
        return String.format(
                "Customer[userName='%s', contactNo='%s']",
                userName, contactNo);
    }
}
