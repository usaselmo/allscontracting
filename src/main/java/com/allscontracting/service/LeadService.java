package com.allscontracting.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.allscontracting.model.Lead;
import com.allscontracting.model.Proposal;
import com.allscontracting.model.Lead.Vendor;
import com.allscontracting.repo.fsimpl.LeadRepository;
import com.allscontracting.tradutor.Translater;
import com.allscontracting.tradutor.TranslaterDispatcher;

@Service
public class LeadService {

	private static final int LEADS_PER_PAGE = 5;
	@Autowired	LeadRepository leadRepo;
	@Autowired	TranslaterDispatcher tradutorFinder;

	public List<Lead> listLeads(int pageRange) throws Exception {
		if(pageRange < 0)
			return StreamSupport.stream(leadRepo.findAll().spliterator(), false).collect(Collectors.toList());
		else
			return StreamSupport.stream(leadRepo.findAll().spliterator(), false).skip(pageRange*LEADS_PER_PAGE).limit(LEADS_PER_PAGE).collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	public void saveLeadFile(MultipartFile file, Vendor vendor) throws Exception {
		if(!tradutorFinder.dispatch(vendor).isFileFromRightVendor(file.getOriginalFilename(), vendor))
			throw new Exception("File and Vendor do not match.");
		List<String> lines = Arrays.asList(new String(file.getBytes()).split(System.lineSeparator()));
		Translater<Lead> translater = (Translater<Lead>) tradutorFinder.dispatch(vendor);
		List<Lead> leads = lines.stream() 
				.map(line -> translater.importedFileLineToEntity(line, Lead.class))
				.filter(lead -> !StringUtils.isEmpty(lead.getId()))
				.collect(Collectors.toList());
		if(leads.isEmpty())
			throw new Exception("Found no Leads in this file. Make sure ';' is the file delimiter. "); 
		//save all
		leads.stream().forEach(lead->this.leadRepo.save(lead));
	}

	public void drop() throws Exception {
		Files.write(leadRepo.getPersistenceFile(), "".getBytes(), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
	}

	public long getLeadsTotal() throws Exception {
		return StreamSupport.stream(leadRepo.findAll().spliterator(), false).count();
	}

	public List<Proposal> findLeadProposals(String id) throws IOException {

		Lead lead = this.leadRepo.findById(id).orElse(Lead.builder().build());
		File folder = LeadRepository.PROPOSALS_FOLDER.toFile();
		
		return Stream.of(folder.list())
			.filter(file->file.toString().startsWith(lead.getClient().getName()) && file.toString().endsWith(".pdf"))
			.map(file->Proposal.builder().id(38974893L).fileName(file.toString()).build())
			.collect(Collectors.toList());

		//return Arrays.asList(Proposal.builder().total(BigDecimal.valueOf(120.36)).build());
	}

}
