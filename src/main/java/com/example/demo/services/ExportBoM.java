package com.example.demo.services;

import java.io.FileWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.BoMt1;
import com.example.demo.repository.BoMt1Repository;
@Service
public class ExportBoM {

	@Autowired
	private BoMt1Repository bomt2;
	 
public String exportBoMRecords(String module, int bomV, String filePath) {
		
		System.out.println(module);
		System.out.println(bomV);
		System.out.println(filePath);
		String sapBoMFileName = filePath+module+"_Final.csv";
		try {
			
				 FileWriter fileWriter = null;
				// System.out.println("before file writer");
				 fileWriter = new FileWriter(sapBoMFileName);
				 fileWriter.append("Header Material, Valid From, Alternative BOM, Reason for Change, Base Quantity, Bom Status, Item Number, Item Cat, Component Material, Quantity, Uom, Header_Component, Module Number, BoM Version, Price, Source, Created By, Modified By, Created, Modified, Revision, Valid Component, Valid Header, Valid UoM \n");

				// System.out.println("before for loop");
				 int i=0;
				 
				 System.out.println(bomt2.findByDesign_ModuleANDitemNumber(module, bomV).isEmpty());
				 
				 
		    for(BoMt1 rs1 : bomt2.findByDesign_ModuleANDBOM_Version(module, bomV)) {
		    	
		    	//System.out.println("loop: "+i);
		    	
		    	fileWriter.append(rs1.getHeaderMaterial());
			    fileWriter.append(",");
			    fileWriter.append(rs1.getValidFrom());
			    fileWriter.append(",");
			    fileWriter.append(String.valueOf(rs1.getAltBom()));
			    fileWriter.append(",");
			    fileWriter.append(rs1.getReasonForChange());
			    fileWriter.append(",");
			    fileWriter.append(String.valueOf(rs1.getBaseQ()));
			    fileWriter.append(",");
			    fileWriter.append(String.valueOf(rs1.getBomStatus()));
			    fileWriter.append(",");
			    fileWriter.append(String.valueOf(rs1.getItemNumber()));
			    fileWriter.append(",");
			    fileWriter.append(rs1.getItemCat());
			    fileWriter.append(",");
			    fileWriter.append(rs1.getComponentMaterial());
			    fileWriter.append(",");
			    fileWriter.append(String.valueOf(rs1.getQuantity()));
			    fileWriter.append(",");
			    fileWriter.append(rs1.getUom());
			    fileWriter.append(",");
			    fileWriter.append(rs1.getHeaderMaterial()+rs1.getComponentMaterial());
			    fileWriter.append(",");
			    fileWriter.append(rs1.getDesign_Module());
			    fileWriter.append(",");
			    fileWriter.append(String.valueOf(rs1.getBOM_Version()));
			    fileWriter.append(",");
			    fileWriter.append(String.valueOf(rs1.getPrice()));
			    fileWriter.append(",");
			    fileWriter.append(rs1.getSource());
			    fileWriter.append(",");	
			    fileWriter.append(rs1.getCreatedBy());
			    fileWriter.append(",");
			    fileWriter.append(rs1.getModifiedBy());
			    fileWriter.append(",");
			    fileWriter.append(String.valueOf(rs1.getCreated()));
			    fileWriter.append(",");
			    fileWriter.append(String.valueOf(rs1.getModified()));
			    fileWriter.append(",");
			    fileWriter.append(String.valueOf(rs1.getRevision()));
			    fileWriter.append(",");
			    fileWriter.append(String.valueOf(rs1.isValid_Component()));
			    fileWriter.append(",");
			    fileWriter.append(String.valueOf(rs1.isValid_Header()));
			    fileWriter.append(",");
			    fileWriter.append(String.valueOf(rs1.isValid_C_UoM()));
			    fileWriter.append(",");
			    fileWriter.append("\n");
			   // System.out.println("loop : "+i+" end");

		    }

		    fileWriter.close();
		    return sapBoMFileName;
			
		}catch (Exception ee) {
			System.out.println(ee);
			return ee.getMessage();
		}
		
		
		
		
	}

}
