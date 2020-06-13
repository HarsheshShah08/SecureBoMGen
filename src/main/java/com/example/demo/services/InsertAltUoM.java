/**
 * 
 */
package com.example.demo.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.AlternativeUoM;
import com.example.demo.model.Materials;
import com.example.demo.repository.AlternativeUoMRepository;
import com.example.demo.repository.MaterialRepository;

/**
 * @author Harshesh
 *
 */
@Service
public class InsertAltUoM {
	
	@Autowired
	private AlternativeUoMRepository altUoMRepository;
	private boolean bSuccess = false;
	
	public boolean insertAltUoMMaterials(String path) {
		
		try {

			BufferedReader reader = null;
			reader = new BufferedReader(new FileReader(path));
			reader.readLine();
			String line = "";
			if(!altUoMRepository.findAll().isEmpty()) {
				altUoMRepository.deleteAll();
			}
			
									
			while((line = reader.readLine()) != null) {
	
				String[] fields = line.split(",");
				if(fields.length > 0) {

					AlternativeUoM altUoM = new AlternativeUoM();
					if(fields[1].isEmpty()==false)
						altUoM.setMaterialId(fields[0]);
					if(fields[1].isEmpty()==false)
						altUoM.setsUoM(fields[1]);
					if(fields[2].isEmpty()==false )
						altUoM.setDenominator(Float.valueOf(fields[2]));
					if(fields[3].isEmpty()==false)
						altUoM.setNumerator(Float.valueOf(fields[3]));
					
					if(altUoMRepository.findByMaterialIdANDSUoM(fields[0], fields[1]).isEmpty()) {
						
						altUoMRepository.save(altUoM);
						bSuccess = true;
						
					}else {
						
						bSuccess = false;
						System.out.println(fields[0]+fields[1]);
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
