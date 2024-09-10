package com.codewithme.blog.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="Category")
public class Category {
	
		@Id
		@GeneratedValue(strategy= GenerationType.IDENTITY)
		private Integer CId;
		private String CTitle;
		private String CDescription;
		
		@OneToMany(mappedBy = "category", cascade = CascadeType.ALL,fetch= FetchType.LAZY )
		private List<Post> post = new ArrayList<>();
		
}
