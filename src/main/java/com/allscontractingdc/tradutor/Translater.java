package com.allscontractingdc.tradutor;

import com.allscontractingdc.model.Lead;
import com.allscontractingdc.model.Lead.Vendor;

public interface FSGenericTranslater {

	Lead importedFileLineToEntity(String line);
	
	Lead localFSFileLineToEntity(String line);
	
	String entityToLocalFSFileLine(Lead entity);
	
	boolean isFileFromRightVendor(String originalFileName, Vendor vendor);

}
