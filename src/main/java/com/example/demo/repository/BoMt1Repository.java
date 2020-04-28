/**
 * 
 */
package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.BoMt1;
import com.example.demo.model.User;


/**
 * @author Harshesh.Shah
 *
 */


public interface BoMt1Repository extends JpaRepository <BoMt1, Integer> {
	
	
	@Query("FROM BoMt1 WHERE Design_Module = ?1 ORDER BY BOM_Version DESC")
    ArrayList<BoMt1> findByDesign_Module(String Design_Module);
	
	@Query("FROM BoMt1 WHERE headerMaterial = ?1 AND  componentMaterial = ?2 AND Design_Module =?3 AND BOM_Version = ?4 ORDER BY BOM_Version DESC")
    ArrayList<BoMt1> findByHeaderMaterialANDComponentMaterialANDDesign_ModuleANDBOM_Version(String headerMaterial, String componentMaterial, String Design_Module, int BOM_Version);
	
	@Query("FROM BoMt1 WHERE Design_Module = ?1 AND itemNumber =?2 AND BOM_Version = ?3")
    ArrayList<BoMt1> findByDesign_ModuleANDitemNumberANDBOM_Version(String Design_Module, int itemNumber, int BOM_Version);
	
	@Query("FROM BoMt1 WHERE headerMaterial = ?1 AND  componentMaterial = ?2 AND Design_Module =?3 ORDER BY BOM_Version DESC")
    ArrayList<BoMt1> findByHeaderMaterialANDComponentMaterialANDDesign_Module(String headerMaterial, String componentMaterial, String Design_Module);
	
	@Query("FROM BoMt1 WHERE Design_Module = ?1 AND itemNumber =?2")
    ArrayList<BoMt1> findByDesign_ModuleANDitemNumber(String Design_Module, int itemNumber);
	
	@Query("FROM BoMt1 WHERE Design_Module = ?1 AND BOM_Version = ?2")
    ArrayList<BoMt1> findByDesign_ModuleANDBOM_Version(String Design_Module, int BOM_Version);
	

	

}

