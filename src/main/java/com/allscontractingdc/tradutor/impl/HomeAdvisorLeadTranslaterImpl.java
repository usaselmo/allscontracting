package com.allscontractingdc.tradutor.impl;

import org.springframework.stereotype.Component;

import com.allscontractingdc.model.Client;
import com.allscontractingdc.model.Lead;
import com.allscontractingdc.model.Lead.Vendor;
import com.allscontractingdc.service.Converter;
import com.allscontractingdc.tradutor.FSGenericTranslater;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HomeAdvisorLeadTranslaterImpl implements GenericLeadTranslater, FSGenericTranslater {
	
	private static final int HA_ID = 0;
	private static final int HA_DATE = 1;
	private static final int HA_DESCRIPTION= 2;
	private static final int HA_FEE = 3;
	private static final int HA_TYPE = 4;
	private static final int HA_FIRST_NAME = 5;
	private static final int HA_LAST_NAME = 6;
	private static final int HA_ADDRESS = 7;
	private static final int HA_CITY = 8;
	private static final int HA_STATE = 9;
	private static final int HA_ZIP_CODE = 10;
	private static final int HA_CELL_PHONE = 11;
	private static final int HA_PHONE = 12;
	private static final int HA_EMAIL = 13;

	@Override
	public Lead importedFileLineToEntity(String line) {
		try {
			if(line.contains("Lead #,") && line.contains("Lead Date"))
				return Lead.builder().build();
			String[] splitedLine = line.split(GenericLeadTranslater.LINE_ITEM_SEPARATOR);
			return Lead.builder()
					.id(splitedLine[HA_ID])
					.vendor(Vendor.HOME_ADVISOR)
					.date(Converter.convertToDate(splitedLine[HA_DATE]))
					.description(splitedLine[HA_DESCRIPTION])
					.fee(defineCost(splitedLine[HA_FEE]))
					.type(splitedLine[HA_TYPE])
					.client(Client.builder()
							.address(splitedLine[HA_ADDRESS] + ", " + splitedLine[HA_CITY] + ", " + splitedLine[HA_STATE] + " " + splitedLine[HA_ZIP_CODE])
							.cellPhone(splitedLine[HA_CELL_PHONE])
							.email(splitedLine[HA_EMAIL])
							.name(splitedLine[HA_FIRST_NAME] + " " + splitedLine[HA_LAST_NAME])
							.phone(splitedLine[HA_PHONE])
							.build())
					.build();
		} catch (Exception e) {
			log.error("Line to Lead error: {} - {}", line, e.getMessage());
			return Lead.builder().build();
		}
	}

	@Override
	public Lead localFSFileLineToEntity(String line) {
		return localFileLineToLead(line);
	}

	@Override
	public String entityToLocalFSFileLine(Lead entity) {
		return leadToLocalFileLine(entity);
	}

	@Override
	public boolean isFileFromRightVendor(String originalFileName, Vendor vendor) {
		return originalFileName.toLowerCase().contains("home") && originalFileName.toLowerCase().contains("advisor") && vendor.equals(Vendor.HOME_ADVISOR);
	}

}
