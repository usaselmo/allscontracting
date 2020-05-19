package com.allscontracting.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@javax.persistence.Entity(name="lead")
public class Lead implements Entity<String> {

	private static final long serialVersionUID = 2718925984228018742L;
	
	@Id
	private String id;
		
	private Vendor vendor;
	
	@Temporal(value = TemporalType.DATE)
	private Date date;
	
	private String description;
	private BigDecimal fee;
	private String type;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private Client client;
	
	//private List<Proposal> proposals;

	public enum Vendor {
		HOME_ADVISOR, NETWORX
	}
}
