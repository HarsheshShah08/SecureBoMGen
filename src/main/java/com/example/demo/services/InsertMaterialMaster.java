/**
 * 
 */
package com.example.demo.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Materials;
import com.example.demo.repository.MaterialRepository;

/**
 * @author Harshesh
 *
 */
@Service
public class InsertMaterialMaster {
	
	private boolean bSuccess = false;
	
	@Autowired
	private MaterialRepository mRepository;
	
	public boolean insertMaterials(String path) {
		
		try {

			BufferedReader reader = null;
			reader = new BufferedReader(new FileReader(path));
			reader.readLine();
			String line = "";
			if(!mRepository.findAll().isEmpty()) {
				mRepository.deleteAll();
			}
			
									
			while((line = reader.readLine()) != null) {
	
				String[] fields = line.split(",");
				if(fields.length > 0) {

					Materials material = new Materials();
					if(fields[1].isEmpty()==false)
						material.setMaterialId(fields[0]);
					if(fields[1].isEmpty()==false)
						material.setBaseUoM(fields[1]);
					if(fields[2].isEmpty()==false )
						material.setPurchaseUoM(fields[2]);
					if(fields[3].isEmpty()==false)
						material.setPrice(Float.valueOf(fields[3]));
					if(fields[4].isEmpty()==false)
						material.setIssueUoM(fields[4]);
					if(mRepository.findByMaterialId(fields[0]).isEmpty()) {
						
						mRepository.save(material);
						bSuccess = true;
						
					}else {
						
						bSuccess = false;
					}
					
					
				}
			}
		
			reader.close();
			return bSuccess;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			bSuccess = false;
			e.printStackTrace();
			return bSuccess;
			
		}
		
		
		
	}
}
