package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.util.FileCopyUtils;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.ResourceUtils;

import com.example.demo.model.BoMt1;
import com.example.demo.repository.BoMt1Repository;
import com.example.demo.services.BoMt1Service;
import com.example.demo.services.CustomProperties;
import com.example.demo.services.SetBoMVersionService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProfileControllerTest {
	
	@Autowired
	private BoMt1Repository boMt1Repository;
	@Autowired
	private BoMt1Service bomService; 
	@Autowired
	private SetBoMVersionService bDetails;
	@Autowired
	private CustomProperties customProperties;


	@Test
	final void test() {
		
		try {
		
			System.out.println("1");
			String UPLOADED_FOLDER = "C:\\files\\test\\";
			 URL localPackage = this.getClass().getResource("");
			    URL urlLoader = ProfileControllerTest.class.getProtectionDomain().getCodeSource().getLocation();
			    String localDir = localPackage.getPath();
			    String loaderDir = urlLoader.getPath();
			    String is = new ClassPathResource("/revit.csv").getPath();//InputStream
			    //System.out.printf("loaderDir = %s\n localDir = %s\n", loaderDir, localDir);
			int i =1;
			int nBoMv;
			int nMaxI=10;
		/*	Path path_R = Paths.get(UPLOADED_FOLDER+ "R\\" + "Revit.csv");
			Path path_H = Paths.get(UPLOADED_FOLDER+ "H\\" + "HSBCAD.csv");
			Path path_S1 = Paths.get(UPLOADED_FOLDER+ "S\\" + "SAP1.csv");
			Path path_S2 = Paths.get(UPLOADED_FOLDER+ "S\\" + "SAP2.csv"); 
																			*/

			String path_R = new ClassPathResource("/Revit.csv").getPath();
			String path_H = new ClassPathResource("/HSBCAD.csv").getPath();
			String path_S1 = new ClassPathResource("/SAP1.csv").getPath();
			String path_S2 = new ClassPathResource("/SAP2.csv").getPath();
			
			
			System.out.println("2");
			//find the latest version of Test BOM
			String sTestBoM = "J_TEST"+String.valueOf(i);
			//Comment added
			boolean bExist;
			bExist = boMt1Repository.findByDesign_Module(sTestBoM).isEmpty();
			while(!bExist) {
				
				i = i+1;
				System.out.println(i);
				sTestBoM = "J_TEST"+String.valueOf(i);
				System.out.println(sTestBoM);
				bExist = boMt1Repository.findByDesign_Module(sTestBoM).isEmpty();
				
			}

			bDetails.getBoMBasicDetails(sTestBoM);
			nBoMv = bDetails.getnBoMVersion();
			
			nMaxI = bomService.saveBoM1tData(path_R, "REVIT", sTestBoM, nBoMv, nMaxI, "JTest", "Auto Test");
			nMaxI = bomService.saveBoM1tData(path_H, "HSB", sTestBoM, nBoMv, nMaxI, "JTest", "Auto Test");
			/*
			nMaxI = bomService.saveBoM1tData(path_R.toString(), "REVIT", sTestBoM, nBoMv, nMaxI, "JTest", "Auto Test");
			nMaxI = bomService.saveBoM1tData(path_H.toString(), "HSB", sTestBoM, nBoMv, nMaxI, "JTest", "Auto Test");
			*/
			
			System.out.println("3");
			//compare with expected results
			//this.checkBoM(sTestBoM, nBoMv, path_S1.toString());
			this.checkBoM(sTestBoM, nBoMv, path_S1);
			
			System.out.println("4");
			//create another version of same bom
			bDetails.getBoMBasicDetails(sTestBoM);
			nBoMv = bDetails.getnBoMVersion();
			
			nMaxI = bomService.saveBoM1tData(path_R, "REVIT", sTestBoM, nBoMv, nMaxI, "JTest", "Auto Test");
			nMaxI = bomService.saveBoM1tData(path_H, "HSB", sTestBoM, nBoMv, nMaxI, "JTest", "Auto Test");
		/*
			nMaxI = bomService.saveBoM1tData(path_R.toString(), "REVIT", sTestBoM, nBoMv, nMaxI, "JTest", "Auto Test");
			nMaxI = bomService.saveBoM1tData(path_H.toString(), "HSB", sTestBoM, nBoMv, nMaxI, "JTest", "Auto Test");
		*/	
			//compare with expected results
			//this.checkBoM(sTestBoM, nBoMv, path_S2.toString());
			this.checkBoM(sTestBoM, nBoMv, path_S2);
			//System.out.printf("loaderDir = %s\n localDir = %s\n", loaderDir, localDir);

			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}//END TEST METHOD
	
	private void checkBoM(String sTestBoM, int nBoMv, String path) {
		
		try {
			BufferedReader reader = null;
		
			reader = new BufferedReader(new FileReader(path.toString()));
			reader.readLine();
			String line = "";
			int nTotalRec =0;
			int nItemNumber=0;
			System.out.println("while");					
			while((line = reader.readLine()) != null) {
	
				System.out.println("if");	
				String[] fields = line.split(",");
				if(fields.length > 0) {
					
					nTotalRec++;
					
					BoMt1 bom_e = new BoMt1();

					System.out.println("itemNumber");
					nItemNumber= Integer.valueOf(fields[6]);//item number
						
					assertNotNull(boMt1Repository.findByDesign_ModuleANDitemNumberANDBOM_Version(sTestBoM, nItemNumber, nBoMv).get(0),"No BOM record found");
						
					bom_e = boMt1Repository.findByDesign_ModuleANDitemNumberANDBOM_Version(sTestBoM, nItemNumber, nBoMv).get(0);

					assertEquals(bom_e.getHeaderMaterial(), fields[0]);//headerMaterial

					assertEquals(bom_e.getComponentMaterial(), fields[8]);//component material

					assertEquals(bom_e.getQuantity(), Float.valueOf(fields[9]));//Quantity

					assertEquals(bom_e.getBOM_Version(), Integer.valueOf(fields[13]));//BOM Version
					
					

					assertEquals(bom_e.getPrice(), Float.valueOf(fields[14]));//price

					assertEquals(bom_e.getSource(), fields[15]);//Source	

					assertEquals(bom_e.getRevision(), Integer.valueOf(fields[20]));//Revision

					assertEquals(bom_e.isValid_Component(), Boolean.valueOf(fields[21]));//valid component
	
					assertEquals(bom_e.isValid_Header(), Boolean.valueOf(fields[22]));//valid header

					assertEquals(bom_e.isValid_C_UoM(), Boolean.valueOf(fields[23]));//valid uom
					
				}
			}
			
			System.out.println("asset completed");
			assertEquals(nTotalRec, boMt1Repository.findByDesign_ModuleANDBOM_Version(sTestBoM, nBoMv).size());
			
			reader.close();
			System.out.println("reader closed");
			
			
	
		} catch (Exception e) {
			
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
			
	}

}
