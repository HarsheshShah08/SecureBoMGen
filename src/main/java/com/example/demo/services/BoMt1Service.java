/**
 * 
 */
package com.example.demo.services;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.time.*;

import javax.transaction.Transactional;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.AlternativeUoM;
import com.example.demo.model.BoMt1;
import com.example.demo.model.Materials;
import com.example.demo.model.User;
import com.example.demo.repository.AlternativeUoMRepository;
import com.example.demo.repository.BoMt1Repository;
import com.example.demo.repository.MaterialRepository;

/**
 * @author Harshesh.Shah
 *
 */
@Service
public class BoMt1Service {
	
	@Autowired
	private BoMt1Repository bomt1;
	@Autowired
	private MaterialService mService;

	
	public int saveBoM1tData(String path, String sCAD, String sModule, int nBoMv, int nMaxI, String sLoggedUser, String reasonForChange)  {
		
		int nItemIdentifier = nMaxI;
		Date dCurrentDate = new Date();
		float nEffectivePrice =0;
		
		try {

			BufferedReader reader = null;
			reader = new BufferedReader(new FileReader(path));
			reader.readLine();
			String line = "";
			float nQuantity;
			boolean bValidCompMaterial;
			boolean bValidCompUoM;
			boolean bValidHeaderMaterial;
			boolean bValidHeaderUoM;
						
			if(sCAD=="REVIT") {
								
				while((line = reader.readLine()) != null) {
		
				String[] fields = line.split(",");
				if(fields.length > 0) {
					
					BoMt1 b = new BoMt1();
					b.setHeaderMaterial(fields[0]);
					b.setComponentMaterial(fields[1]);
					nQuantity = Float.valueOf(fields[2]);
					b.setUom(fields[3]);
					b.setSource(sCAD);
					b.setDesign_Module(sModule);
					BoMt1 bTemp = new BoMt1();	
					
					mService.getEffectivePrice(fields[0], "EA");
					bValidHeaderMaterial = mService.isValidMaterial();
					bValidHeaderUoM = mService.isValidUoM();
					
					
					mService.getEffectivePrice(fields[1], fields[3]);
					bValidCompMaterial = mService.isValidMaterial();
					bValidCompUoM = mService.isValidUoM();
			
					nEffectivePrice = mService.getnEffectivePrice();

					
					boolean isEmpty = bomt1.findByHeaderMaterialANDComponentMaterialANDDesign_Module(fields[0], fields[1], sModule).isEmpty();
					if(isEmpty){
						
						b.setQuantity(nQuantity);
						b.setReasonForChange(reasonForChange);
						b.setPrice(nQuantity*nEffectivePrice);
						b.setBOM_Version(nBoMv);
						b.setCreatedBy(sLoggedUser);
						b.setCreated(dCurrentDate);
						b.setModified(dCurrentDate);
						b.setModifiedBy(sLoggedUser);
						b.setValid_Header(bValidHeaderMaterial);
						b.setValid_H_UoM(bValidHeaderUoM);
						b.setValid_Component(bValidCompMaterial);
						b.setValid_C_UoM(bValidCompUoM);
						b.setRevision(1);
						while(bomt1.findByDesign_ModuleANDitemNumber(sModule, nItemIdentifier).isEmpty()==false) {
							System.out.println(sModule+" "+nItemIdentifier+" "+bomt1.findByDesign_ModuleANDitemNumber(sModule, nItemIdentifier).isEmpty());
							nItemIdentifier = nItemIdentifier+1;
						 }
						b.setItemNumber(nItemIdentifier);
						bomt1.save(b);

						
						nItemIdentifier = nItemIdentifier+1;
					}else{
								
						/*int nPrevVersion = bomt1.findByHeaderMaterialANDComponentMaterialANDDesign_Module(fields[0], fields[1], sModule).get(0).getBOM_Version();
						int nPrevItemNumber = bomt1.findByHeaderMaterialANDComponentMaterialANDDesign_Module(fields[0], fields[1], sModule).get(0).getItemNumber();
						int nPrevRevisionNumber = bomt1.findByHeaderMaterialANDComponentMaterialANDDesign_Module(fields[0], fields[1], sModule).get(0).getRevision();
						float nPrevQ = bomt1.findByHeaderMaterialANDComponentMaterialANDDesign_Module(fields[0], fields[1], sModule).get(0).getQuantity();
						int nTempId = bomt1.findByHeaderMaterialANDComponentMaterialANDDesign_Module(fields[0], fields[1], sModule).get(0).getId();*/
						
						bTemp= bomt1.findByHeaderMaterialANDComponentMaterialANDDesign_Module(fields[0], fields[1], sModule).stream()
					            .findFirst()
					            .get();
						System.out.println(bTemp.toString()); 
						int nPrevVersion = bTemp.getBOM_Version();
						int nPrevItemNumber = bTemp.getItemNumber();
						int nPrevRevisionNumber = bTemp.getRevision();
						float nPrevQ = bTemp.getQuantity();
						int nTempId = bTemp.getId();
									
									if(nBoMv==nPrevVersion) {
										
										nQuantity = nQuantity + nPrevQ;
										//bTemp = bomt1.findById(nTempId).get();
										bTemp.setId(nTempId);
										bTemp.setItemNumber(nPrevItemNumber);
										bTemp.setSource("REVIT");
										bTemp.setQuantity(nQuantity);
										bTemp.setModified(dCurrentDate);
										bTemp.setModifiedBy(sLoggedUser);
										bTemp.setPrice(nQuantity*nEffectivePrice);
										bTemp.setRevision(nPrevRevisionNumber+1);
										bTemp.setValid_Header(bValidHeaderMaterial);
										bTemp.setValid_H_UoM(bValidHeaderUoM);
										bTemp.setValid_Component(bValidCompMaterial);
										bTemp.setValid_C_UoM(bValidCompUoM);
										bomt1.save(bTemp);
									}else{
										 b.setReasonForChange(reasonForChange);
										 b.setQuantity(nQuantity);
									     b.setSource("REVIT");
										 b.setCreatedBy(sLoggedUser);
										 b.setCreated(dCurrentDate);
										 b.setQuantity(nQuantity);
										 b.setPrice(nQuantity*nEffectivePrice);
										 b.setModified(dCurrentDate);
										 b.setModifiedBy(sLoggedUser);
										 b.setBOM_Version(nBoMv);
										 b.setRevision(nPrevRevisionNumber+1);
										 b.setItemNumber(nPrevItemNumber);
										 b.setValid_Header(bValidHeaderMaterial);
										 b.setValid_H_UoM(bValidHeaderUoM);
										 b.setValid_Component(bValidCompMaterial);
										 b.setValid_C_UoM(bValidCompUoM);
										 boolean bCreateBoMRec = bomt1.findByDesign_ModuleANDitemNumberANDBOM_Version(sModule, nPrevItemNumber, nBoMv).isEmpty();
										if(bCreateBoMRec) {
											bomt1.save(b);
										}
										
										 
									}//end of else
								
								}//end of else			
			
						}//end if
				
				}//end while
				
				
			}else if(sCAD=="HSB") {
				
				while((line = reader.readLine()) != null) {

				String[] fields = line.split(",");
				if(fields.length > 0) {
					
					
					
						BoMt1 b = new BoMt1();	
						BoMt1 bTemp = new BoMt1();	
						b.setHeaderMaterial(fields[2]);
					    b.setComponentMaterial(fields[3]);
					    nQuantity = Float.valueOf(fields[5]); 
					    b.setUom(fields[11]);
						b.setModified(dCurrentDate);
						b.setModifiedBy(sLoggedUser);
					    b.setDesign_Module(sModule);
				 
						mService.getEffectivePrice(fields[2], "EA");
						
						bValidHeaderMaterial = mService.isValidMaterial();
						bValidHeaderUoM = mService.isValidUoM();
						
						mService.getEffectivePrice(fields[3], fields[11]);
						
						
						bValidCompMaterial = mService.isValidMaterial();
						bValidCompUoM = mService.isValidUoM();
						
						nEffectivePrice = mService.getnEffectivePrice();
						System.out.println("Comp Validation "+fields[3]+ " " +bValidHeaderMaterial+" "+bValidHeaderUoM+" "+nEffectivePrice);
				     
				     /*Set UoM End*/

				     boolean isEmpty = bomt1.findByHeaderMaterialANDComponentMaterialANDDesign_Module(fields[2], fields[3], sModule).isEmpty();

						if(isEmpty) {
									 b.setReasonForChange(reasonForChange);
									 b.setQuantity(nQuantity);
									 b.setPrice(nQuantity*nEffectivePrice);
									 b.setSource("HSB");
									 b.setBOM_Version(nBoMv);
									 b.setCreatedBy(sLoggedUser);
									 b.setCreated(dCurrentDate);
								     b.setValid_Header(bValidHeaderMaterial);
									 b.setValid_H_UoM(bValidHeaderUoM);
									 b.setValid_Component(bValidCompMaterial);
									 b.setValid_C_UoM(bValidCompUoM);
								     b.setRevision(1);
									while(bomt1.findByDesign_ModuleANDitemNumber(sModule, nItemIdentifier).isEmpty()==false) {
										System.out.println(sModule+" "+nItemIdentifier+" "+bomt1.findByDesign_ModuleANDitemNumber(sModule, nItemIdentifier).isEmpty());
										nItemIdentifier = nItemIdentifier+1;
									 }
									b.setItemNumber(nItemIdentifier);
									bomt1.save(b);
									
									nItemIdentifier = nItemIdentifier+1;
								
							}else {
							
								/*int nPrevVersion = bomt1.findByHeaderMaterialANDComponentMaterialANDDesign_Module(fields[2], fields[3], sModule).get(0).getBOM_Version();
								int nPrevItemNumber = bomt1.findByHeaderMaterialANDComponentMaterialANDDesign_Module(fields[2], fields[3], sModule).get(0).getItemNumber();
								int nPrevRevisionNumber = bomt1.findByHeaderMaterialANDComponentMaterialANDDesign_Module(fields[2], fields[3], sModule).get(0).getRevision();
								float nPrevQ = bomt1.findByHeaderMaterialANDComponentMaterialANDDesign_Module(fields[2], fields[3], sModule).get(0).getQuantity();
								System.out.println(bomt1.findByHeaderMaterialANDComponentMaterialANDDesign_Module(fields[2], fields[3], sModule).get(0).toString());
								int nTempId = bomt1.findByHeaderMaterialANDComponentMaterialANDDesign_Module(fields[2], fields[3], sModule).get(0).getId();*/
								//System.out.println(bomt1.findByHeaderMaterialANDComponentMaterialANDDesign_Module(fields[2], fields[3], sModule).get(0).toString());
								//int nTempId = bTemp.getId();
								
							            
								bTemp= bomt1.findByHeaderMaterialANDComponentMaterialANDDesign_Module(fields[2], fields[3], sModule).stream()
								            .findFirst()
								            .get();
								System.out.println(bTemp.toString()); 
								
								int nPrevVersion = bTemp.getBOM_Version();
								int nPrevItemNumber = bTemp.getItemNumber();
								int nPrevRevisionNumber = bTemp.getRevision();
								float nPrevQ = bTemp.getQuantity();
								int nTempId = bTemp.getId();
								

								System.out.println(nTempId);

									if(nBoMv == nPrevVersion) {
								
										nQuantity = nQuantity + nPrevQ;
										//bTemp.setId(nTempId);
										bTemp.setItemNumber(nPrevItemNumber);
										if(bTemp.getSource()=="HSB") {
											bTemp.setSource("HSB");
										}else {
											bTemp.setSource("BOTH");
										}
										bTemp.setReasonForChange(reasonForChange);
										bTemp.setQuantity(nQuantity);
										bTemp.setPrice(nQuantity*nEffectivePrice);
										bTemp.setModified(dCurrentDate);
										bTemp.setModifiedBy(sLoggedUser);
										bTemp.setValid_Header(bValidHeaderMaterial);
										bTemp.setValid_H_UoM(bValidHeaderUoM);
										bTemp.setValid_Component(bValidCompMaterial);
										bTemp.setValid_C_UoM(bValidCompUoM);
										bTemp.setRevision(nPrevRevisionNumber+1);
										bomt1.save(bTemp);
										
									}else {
										b.setReasonForChange(reasonForChange);
										b.setQuantity(nQuantity);
										b.setPrice(nQuantity*nEffectivePrice);
									    b.setSource("HSB");
										b.setCreatedBy(sLoggedUser);
										b.setBOM_Version(nBoMv);
										b.setCreated(dCurrentDate);
										b.setRevision(nPrevRevisionNumber+1);
										b.setItemNumber(nPrevItemNumber);
										b.setValid_Header(bValidHeaderMaterial);
										b.setValid_H_UoM(bValidHeaderUoM);
										b.setValid_Component(bValidCompMaterial);
										b.setValid_C_UoM(bValidCompUoM);
										if(bomt1.findByDesign_ModuleANDitemNumberANDBOM_Version(sModule, nPrevItemNumber, nBoMv).isEmpty()) {
											bomt1.save(b);
										}
									}//end inner else															
						
							}//end outer else
						}

					}//end if(fields.length > 0)			
				
			}//else if(sCAD=="HSB") {
			
			reader.close();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		return nItemIdentifier;
		
	}

	
}
