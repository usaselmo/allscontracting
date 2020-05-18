package com.allscontractingdc.tradutor;

import java.io.Serializable;

import com.allscontractingdc.model.Lead.Vendor;

public interface Translater<E extends Serializable> {

	E importedFileLineToEntity(String line, Class<E> clazz);
	
	E localFSFileLineToEntity(String line, Class<E> clazz);
	
	String entityToLocalFSFileLine(E entity);
	
	boolean isFileFromRightVendor(String originalFileName, Vendor vendor);

}
