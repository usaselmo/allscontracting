package com.allscontractingdc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Line implements Entity<Long>{

	private static final long serialVersionUID = -8805126155137619614L;
	private Long id;
	private String value;
}
