package com.allscontracting.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item implements Entity<Long>{
	private static final long serialVersionUID = -7338099408168237954L;
	private Long id;
	private List<Line> lines;
}
