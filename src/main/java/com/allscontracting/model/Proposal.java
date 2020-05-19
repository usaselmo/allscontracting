package com.allscontracting.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@javax.persistence.Entity(name = "proposal")
public class Proposal implements Entity<Long> {

	private static final long serialVersionUID = -8804397870000139075L;

	@Id
	@GeneratedValue
	private Long id;

	// @OneToMany(mappedBy = "proposal_id")
	// private List<Item> items;

	// @OneToMany(mappedBy = "proposal_id")
	// private List<Line> lines;

	private BigDecimal total;

	private String fileName;

	@ManyToOne
	private Lead lead;

  @OneToMany(mappedBy = "proposal", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Line> lines;
	
  @OneToMany(mappedBy = "proposal", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Item> items;
	
}
