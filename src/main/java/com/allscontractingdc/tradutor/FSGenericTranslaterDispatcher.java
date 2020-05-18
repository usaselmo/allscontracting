package com.allscontractingdc.tradutor;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allscontractingdc.model.Lead.Vendor;
import com.allscontractingdc.tradutor.impl.HomeAdvisorLeadTranslaterImpl;
import com.allscontractingdc.tradutor.impl.NetworxLeadTranslaterImpl;

@Component
public class FSGenericTranslaterDispatcher {
	
	private Map<Vendor, FSGenericTranslater> strategies = new HashMap<>();
	@Autowired HomeAdvisorLeadTranslaterImpl ha;
	@Autowired NetworxLeadTranslaterImpl nx;
	
	@PostConstruct
	void init() {
		this.strategies.put(Vendor.HOME_ADVISOR, this.ha);
		this.strategies.put(Vendor.NETWORX, this.nx);
	}
	
	public FSGenericTranslater dispatch(Vendor vendor) {
		return this.strategies.get(vendor);
	}

}
