package com.allscontractingdc.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.allscontractingdc.model.Proposal;

@RestController
@RequestMapping("proposals")
public class ProposalController {

	@PostMapping(value="{id}/sendbyemail")
	public void sendByEmail(@RequestBody Proposal proposal) {
		System.out.println(proposal);
	}
}
