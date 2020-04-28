/**
 * 
 */
package com.example.demo.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.BoMt1;
import com.example.demo.repository.BoMt1Repository;

/**
 * @author Harshesh
 *
 */
@Service
public class InsertPreviousBoM {
	
	@Autowired
	private BoMt1Repository bRepository;
	@Autowired
	private MaterialService mService;

	
	
	
	public boolean insertBoMs(String path, String sModule, String sLoggedUser, int nBoMv) {
		
		boolean bSuccess = false;
		
		try {
			
			
	        Date dCurrentDate = new Date();
	        float nEffectivePrice;
			BufferedReader reader = null;
			reader = new BufferedReader(new FileReader(path));
			reader.readLine();
			String line = "";
									
			while((line = reader.readLine()) != null) {
	
				String[] fields = line.split(",");
				if(fields.length > 0) {

					BoMt1 bom = new BoMt1();
					if(fields[0].isEmpty()==false)
						bom.setHeaderMaterial(fields[0]);//heade5
					if(fields[1].isEmpty()==false)
						bom.setValidFrom(fields[1]);//valid from
					if(fields[3].isEmpty()==false)
						bom.setReasonForChange(fields[3]);//reason for change
					if(fields[6].isEmpty()==false)
						bom.setItemNumber(Integer.valueOf(fields[6]));//item number
					if(fields[8].isEmpty()==false)
						bom.setComponentMaterial(fields[8]);//component material
					if(fields[9].isEmpty()==false)
						bom.setQuantity(Float.valueOf(fields[9]));;//Quantity
					if(fields[10].isEmpty()==false)
						bom.setUom(fields[10]);//uom
					
					System.out.println(fields[0]+ fields[8]+ sModule+ nBoMv);
					
					if(bRepository.findByHeaderMaterialANDComponentMaterialANDDesign_ModuleANDBOM_Version(fields[0], fields[8], sModule, nBoMv).isEmpty()) {
						
						
						
						mService.getEffectivePrice(fields[0], "EA");
						bom.setValid_Header(mService.isValidMaterial());
						bom.setValid_H_UoM(mService.isValidUoM());
						
						mService.getEffectivePrice(fields[8], fields[10]);
						bom.setValid_Component(mService.isValidMaterial());
						bom.setValid_C_UoM(mService.isValidUoM());
						
						nEffectivePrice = mService.getnEffectivePrice();
						System.out.println(nEffectivePrice);
						
							bom.setPrice(Float.valueOf(fields[9])*nEffectivePrice);	
							bom.setDesign_Module(sModule);
							bom.setBOM_Version(nBoMv);
							bom.setSource("SAP");
							bom.setCreated(dCurrentDate);
							bom.setCreatedBy(sLoggedUser);
							bom.setModified(dCurrentDate);
							bom.setModifiedBy(sLoggedUser);
							
							System.out.println(Float.valueOf(fields[9]));
							System.out.println(Float.valueOf(fields[9])*nEffectivePrice);
							
						
						bRepository.save(bom);
						
						
						bSuccess = true;
						
					}else {
						
						bSuccess = false;
						
					}
					
					
					
				}
			}
		
			reader.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		System.out.println(bSuccess);
		return bSuccess;

		
		
	}
}
