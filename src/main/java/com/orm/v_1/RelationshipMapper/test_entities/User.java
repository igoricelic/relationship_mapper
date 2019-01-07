package com.orm.v_1.RelationshipMapper.test_entities;

import java.util.Date;

import com.orm.v_1.RelationshipMapper.annotations.Column;
import com.orm.v_1.RelationshipMapper.annotations.Entity;
import com.orm.v_1.RelationshipMapper.annotations.ForeignKey;
import com.orm.v_1.RelationshipMapper.annotations.JoinType;
import com.orm.v_1.RelationshipMapper.annotations.ManyToOne;
import com.orm.v_1.RelationshipMapper.annotations.OneToOne;
import com.orm.v_1.RelationshipMapper.annotations.PrimaryKey;
import com.orm.v_1.RelationshipMapper.annotations.SqlType;
import com.orm.v_1.RelationshipMapper.annotations.Table;

@Entity
@Table(name = "user")
public class User {
	
	@PrimaryKey(autoIncrement = true)
	@Column(name = "id", type = SqlType.BIGINT)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	private Date createdAt;
	
	@ForeignKey(name = "fk_address_of_user")
	@ManyToOne(column = @Column(name = "address_id", nullable = true), joinType = JoinType.INNER)
	private Address address;
	
	@ForeignKey(name = "fk_tax_number_of_user")
	@OneToOne(column = @Column(name = "tax_number_id", nullable = true), joinType = JoinType.LEFT)
	private TaxNumber taxNumber;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	public Address getAddress() {
		return address;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}
	
	public TaxNumber getTaxNumber() {
		return taxNumber;
	}
	
	public void setTaxNumber(TaxNumber taxNumber) {
		this.taxNumber = taxNumber;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", createdAt=" + createdAt
				+ ", address=" + address + ", taxNumber=" + taxNumber + "]";
	}

}
