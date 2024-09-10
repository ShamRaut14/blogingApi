package com.codewithme.blog.entity;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "Post")
@NoArgsConstructor
public class Post {
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	
	  @Column(name="post_title",length = 100, nullable = false)
	private String title;
	
	@Column(name="post_Content",length = 10000, nullable = false)
	private String content;
	
	@Column(name="post_Image",length = 1000, nullable = false)
	private String image;
	
	private Date addedDate;
	
	@ManyToOne
	@JoinColumn(name= "CategoryCId")
	private Category category;
	@ManyToOne
	private User user;
	@OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
	private Set<Comment> comments = new HashSet<>();
	
}
