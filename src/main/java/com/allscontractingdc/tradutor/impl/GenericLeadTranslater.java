package com.allscontractingdc.tradutor.impl;

import java.io.IOException;
import java.math.BigDecimal;

import com.allscontractingdc.model.Lead;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface GenericLeadTranslater {
	
	public static final String LINE_ITEM_SEPARATOR = ";";
	public static final String COMMA = ";";

	default BigDecimal defineCost(String cost) {
		try {
			BigDecimal bd = BigDecimal.valueOf(Float.valueOf(cost.replace("$", "")));
			return bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		} catch (Exception e) {
			return BigDecimal.ZERO;
		}
	}
	
	default String leadToLocalFileLine(Lead lead){		
		try {
			String obj = new ObjectMapper().writeValueAsString(lead);
			return obj+System.lineSeparator();
		} catch (JsonProcessingException e) {
			System.out.println("Erro ao converter Lead pra string : " + lead + " - Erro: " + e.getMessage() );
			return "{}";
		}
	}

	default Lead localFileLineToLead(String line) {
		try {
			Lead lead = new ObjectMapper().readValue(line, Lead.class);
			return lead;
		} catch (IOException e) {
			System.out.println("Erro ao converter string pra Lead: " + line + " - Erro: " + e.getMessage() );
			return Lead.builder().build();
		}
	}
	
}
