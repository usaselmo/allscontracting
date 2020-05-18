package com.allscontractingdc.repo.fsimpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allscontractingdc.exception.LeadsException;
import com.allscontractingdc.model.Lead;
import com.allscontractingdc.tradutor.FSGenericTranslater;
import com.allscontractingdc.tradutor.FSGenericTranslaterDispatcher;
import com.allscontractingdc.tradutor.impl.GenericLeadTranslaterImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LeadRepository extends AbstractGenericFSRepository<Lead, String> { 

	@Autowired	FSGenericTranslaterDispatcher translaterDispatcher;
	@Autowired  GenericLeadTranslaterImpl leadTranslater;

	@Override
	public Path getPersistenceFile() {
		return Paths.get("C:\\Users\\Anselmo.asr\\Google Drive\\All's Contracting\\app\\leads.txt");
	}
	
	@Override
	public <S extends Lead> S save(S entity) {
		if(!this.existsById(entity.getId())) {
			try {
				FSGenericTranslater translater = this.translaterDispatcher.dispatch(entity.getVendor());
				String line = translater.entityToLocalFSFileLine(entity);
				saveToFile(line);
			} catch (LeadsException e) {
				e.printStackTrace();
			}
		}
		return entity;
	}

	@Override
	public Optional<Lead> findById(String id) {
		try {
			return Files.readAllLines(getPersistenceFile()).stream()
					.filter(line -> leadTranslater.localFileLineToLead(line).getId().equalsIgnoreCase(id))//TODO leadTranslater is wrong
					.map(line -> leadTranslater.localFileLineToLead(line)).findFirst();//TODO leadTranslater is wrong
		} catch (IOException e) {
			log.error(e.getMessage());
			return Optional.empty();
		}	
	}

	@Override
	public List<Lead> findAll(){
		try {
			return Files.readAllLines(getPersistenceFile())
					.stream()
					.map(line -> leadTranslater.localFileLineToLead(line))//TODO leadTranslater is wrong
					.sorted( (a,b)-> b.getDate().compareTo(a.getDate()) )
					.collect(Collectors.toList());
		} catch (IOException e) {
			return Collections.emptyList();
		}
	}

	@Override
	public long count() {
		return Stream.of(this.findAll()).count();
	}

	@Override
	public void delete(Lead entity) {
		// TODO Auto-generated method stub
		log.error("delete method not implemented yet");
	}

	@Override
	public boolean existsById(String id) {
		return this.findById(id).isPresent();
	}

}
