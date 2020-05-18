package com.allscontractingdc.service;

import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.allscontractingdc.model.Lead;
import com.allscontractingdc.model.Lead.Vendor;
import com.allscontractingdc.repo.fsimpl.LeadRepository;
import com.allscontractingdc.tradutor.FSGenericTranslaterDispatcher;
import com.allscontractingdc.tradutor.impl.GenericLeadTranslaterImpl;

@Service
public class LeadService {

	private static final int LEADS_PER_PAGE = 5;
	@Autowired	LeadRepository leadRepo;
	@Autowired	FSGenericTranslaterDispatcher tradutorFinder;
	@Autowired 	GenericLeadTranslaterImpl leadTranslaterImpl;

	public List<Lead> listLeads(int pageRange) throws Exception {
		if(pageRange < 0)
			return StreamSupport.stream(leadRepo.findAll().spliterator(), false).collect(Collectors.toList());
		else
			return StreamSupport.stream(leadRepo.findAll().spliterator(), false).skip(pageRange*LEADS_PER_PAGE).limit(LEADS_PER_PAGE).collect(Collectors.toList());
	}

	public void saveLeadFile(MultipartFile file, Vendor vendor) throws Exception {
		if(!tradutorFinder.dispatch(vendor).isFileFromRightVendor(file.getOriginalFilename(), vendor))
			throw new Exception("File and Vendor do not match.");
		List<String> lines = Arrays.asList(new String(file.getBytes()).split(System.lineSeparator()));
		List<Lead> leads = lines.stream() 
				.map(line -> tradutorFinder.dispatch(vendor).importedFileLineToEntity(line))
				.filter(lead -> !StringUtils.isEmpty(lead.getId()))
				.collect(Collectors.toList());
		if(leads.isEmpty())
			throw new Exception("Found no Leads in this file"); 
		//save all
		leads.stream().forEach(lead->this.leadRepo.save(lead));
	}

	public void drop() throws Exception {
		Files.write(leadRepo.getPersistenceFile(), "".getBytes(), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
	}

	public long getLeadsTotal() throws Exception {
		return StreamSupport.stream(leadRepo.findAll().spliterator(), false).count();
	}

}
