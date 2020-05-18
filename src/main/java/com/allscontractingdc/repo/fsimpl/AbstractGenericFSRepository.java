package com.allscontractingdc.repo.fsimpl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;

import com.allscontractingdc.exception.LeadsException;
import com.allscontractingdc.repo.Repository;

public abstract class AbstractGenericFSRepository<E, ID> implements Repository<E, ID> {

	abstract Path getPersistenceFile();

	protected void saveToFile(String dataToWriteToFile) throws LeadsException {
		File leadFile = getPersistenceFile().toFile();
		if (!leadFile.exists()) {
			createFile(leadFile);
		}
		try {
			FileWriter crunchifyWriter;
			crunchifyWriter = new FileWriter(leadFile.getAbsoluteFile(), true);
			BufferedWriter bufferWriter = new BufferedWriter(crunchifyWriter);
			bufferWriter.write(dataToWriteToFile.toString());
			bufferWriter.close();
		} catch (Exception e) {
			throw new LeadsException("Could not save data to Leads File");
		}
	}

	private void createFile(File crunchifyFile) throws LeadsException {
		try {
			File directory = new File(crunchifyFile.getParent());
			if (!directory.exists()) {
				directory.mkdirs();
			}
			crunchifyFile.createNewFile();
		} catch (Exception e) {
			throw new LeadsException("Could not create File: " + crunchifyFile);
		}
	}
}
