package com.allscontractingdc.tradutor.impl;

import java.util.Arrays;
import java.util.Base64;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import com.allscontractingdc.model.Client;
import com.allscontractingdc.model.Lead;
import com.allscontractingdc.model.Lead.Vendor;
import com.allscontractingdc.service.Converter;
import com.allscontractingdc.tradutor.FSGenericTranslater;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class NetworxLeadTranslaterImpl implements GenericLeadTranslater, FSGenericTranslater{
	
	//private static final int NX_Subscription = 0;
	private static final int NX_Date = 1;
	private static final int NX_Name = 2;
	private static final int NX_Phone_Number = 3;
	private static final int NX_Email = 4;
	private static final int NX_Zip_Code = 5;
	private static final int NX_City = 6;
	private static final int NX_State = 7;
	private static final int NX_Address = 8;
	private static final int NX_Additional_Information = 9;
	private static final int NX_Task = 10;
	private static final int NX_Cost = 11;
	//private static final int NX_Credited = 12;
	//private static final int NX_Denied = 13;
	//private static final int NX_Call_Transfer = 14;
	//private static final int NX_Status = 15;

	@Override
	public Lead importedFileLineToEntity(String line) {
		if(line.contains("Subscription;Date;Name"))
			return Lead.builder().build();
		String[] splitedLine = line.split(GenericLeadTranslater.LINE_ITEM_SEPARATOR);
		IntStream.range(0, splitedLine.length)
			.forEach(index->{
				splitedLine[index] = splitedLine[index].replace("\"=\"\"", "").replace("\"\"\"", "");
			});
		return buildLead(splitedLine); 
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
		return originalFileName.toLowerCase().contains("networx") && vendor.equals(Vendor.NETWORX);
	}

	private Lead buildLead(String[] splitedLine) {
		try {
			return Lead.builder()
					.id(defineId(splitedLine))
					.vendor(Vendor.NETWORX)
					.date(Converter.convertToDate(splitedLine[NX_Date]))
					.description(splitedLine[NX_Additional_Information])
					.fee(defineCost(splitedLine[NX_Cost]))
					.type(splitedLine[NX_Task])
					.client(Client.builder()
							.address(splitedLine[NX_Address] + ", " + splitedLine[NX_City] + ", " + splitedLine[NX_State] + " " + splitedLine[NX_Zip_Code])
							.cellPhone(splitedLine[NX_Phone_Number])
							.email(splitedLine[NX_Email])
							.name(splitedLine[NX_Name]+"")
							.phone("")
							.build())
					.build();
		} catch (Exception e) { 
			log.error("Line to Lead error {} {}", e.getMessage(), Arrays.asList(splitedLine));
			return Lead.builder().build();
		}
	}

	private String defineId(String[] splitedLine) {
		return 
		Base64.getEncoder().encodeToString((splitedLine[NX_Email]+splitedLine[NX_Phone_Number]).getBytes());
	}

}
