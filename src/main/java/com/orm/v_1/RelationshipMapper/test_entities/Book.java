package com.orm.v_1.RelationshipMapper.test_entities;

import com.orm.v_1.RelationshipMapper.annotations.Column;
import com.orm.v_1.RelationshipMapper.annotations.Entity;
import com.orm.v_1.RelationshipMapper.annotations.ForeignKey;
import com.orm.v_1.RelationshipMapper.annotations.JoinType;
import com.orm.v_1.RelationshipMapper.annotations.ManyToOne;
import com.orm.v_1.RelationshipMapper.annotations.PrimaryKey;
import com.orm.v_1.RelationshipMapper.annotations.SqlType;
import com.orm.v_1.RelationshipMapper.annotations.Table;

@Entity
@Table(name = "book")
public class Book {
	
	@PrimaryKey(autoIncrement = true)
	@Column(name = "id", type = SqlType.BIGINT)
	private Long id;

	@Column(unique = true)
	private String uniqueNumber;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@ForeignKey(name = "fk_book_of_user")
	@ManyToOne(column = @Column(name = "user_id", nullable = true), joinType = JoinType.INNER)
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUniqueNumber() {
		return uniqueNumber;
	}

	public void setUniqueNumber(String uniqueNumber) {
		this.uniqueNumber = uniqueNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", uniqueNumber=" + uniqueNumber + ", title=" + title + ", user=" + user + "]";
	}

}
