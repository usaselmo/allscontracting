package com.allscontractingdc.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Lead implements Entity<String>{

	private static final long serialVersionUID = 2718925984228018742L;
	private String id;
	private Vendor vendor;
	private Date date;
	private String description;
	private BigDecimal fee;
	private String type;
	private Client client;
	private List<Proposal> proposals;
	
	public enum Vendor{
		HOME_ADVISOR, NETWORX
	}
}
