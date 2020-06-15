package com.example.demo;

import java.io.File;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.example.demo.controller.ProfileController;
import com.example.demo.repository.AlternativeUoMRepository;
import com.example.demo.repository.MaterialRepository;
import com.example.demo.services.InsertAltUoM;
import com.example.demo.services.InsertMaterialMaster;

@Component
public class AppInitializator {

	private static final Logger logger=LogManager.getLogger(AppInitializator.class);
	
	@Autowired
	InsertMaterialMaster insertMM;
	@Autowired
	InsertAltUoM insertAUoM;
	@Autowired
	private MaterialRepository mRepository;
	@Autowired
	private AlternativeUoMRepository altUoMRepository;
	 
    @PostConstruct
    private void init() {
    	logger.info("AppInitializator initialization logic ...");
    	new File(ProfileController.UPLOADED_FOLDER).mkdir();
        // ...
    	if(mRepository.findAll().isEmpty()) {
    		String path = new ClassPathResource("/MaterialMaster.csv").getPath();
        	insertMM.insertMaterials(path);
		}
    	if(altUoMRepository.findAll().isEmpty()) {
    		String path = new ClassPathResource("/AlternativeUoM.csv").getPath();
        	insertAUoM.insertAltUoMMaterials(path);
		}
    	
    }
	
}
