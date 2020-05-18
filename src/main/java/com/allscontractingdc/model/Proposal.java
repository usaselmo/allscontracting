package com.allscontractingdc.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Proposal implements Entity<Long> {

	private static final long serialVersionUID = -8804397870000139075L;
	private Long id;
	private List<Item> items;
	private BigDecimal total;
	private String fileName;

}
