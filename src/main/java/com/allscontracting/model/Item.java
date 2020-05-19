package com.allscontracting.model;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@javax.persistence.Entity
public class Item implements Entity<Long>{

	private static final long serialVersionUID = -7942592928182519301L;

	@Id @GeneratedValue
	private Long id;
	
	private String title;

	//@OneToMany(mappedBy="item_id")
	//private List<Line> lines;
}
