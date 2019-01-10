package com.orm.v_1.RelationshipMapper.test_entities;

import java.util.List;

import com.orm.v_1.RelationshipMapper.annotations.Column;
import com.orm.v_1.RelationshipMapper.annotations.Entity;
import com.orm.v_1.RelationshipMapper.annotations.ForeignKey;
import com.orm.v_1.RelationshipMapper.annotations.ManyToOne;
import com.orm.v_1.RelationshipMapper.annotations.OneToMany;
import com.orm.v_1.RelationshipMapper.annotations.PrimaryKey;
import com.orm.v_1.RelationshipMapper.annotations.Table;

@Entity
@Table(name = "address")
public class Address {
	
	@PrimaryKey(autoIncrement = true)
	private Long id;
	
	@ForeignKey(name = "fk_city_of_address")
	@ManyToOne(column = @Column(name = "city_id", nullable = true))
	private City city;
	
	private String street;
	
	private Integer number;
	
	@OneToMany(mappedBy = "address")
	private List<User> users;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public City getCity() {
		return city;
	}
	
	public void setCity(City city) {
		this.city = city;
	}
	
	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public Integer getNumber() {
		return number;
	}
	
	public void setNumber(Integer number) {
		this.number = number;
	}
	
	public List<User> getUsers() {
		return users;
	}
	
	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", city=" + city + ", street=" + street + ", number=" + number + "]";
	}

}
