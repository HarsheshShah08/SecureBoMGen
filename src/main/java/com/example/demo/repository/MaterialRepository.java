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
import com.example.demo.model.Materials;
import com.example.demo.model.User;


/**
 * @author Harshesh.Shah
 *
 */


public interface MaterialRepository extends JpaRepository <Materials, Integer> {
	
	
	
	
	//public ArrayList<BoMt1> findByItemNumberAndDesign_ModuleOrderByItemNumberAsc(int itemNumber, String Design_Module);
	@Query("FROM Materials WHERE materialId = ?1")
    ArrayList<Materials> findByMaterialId(String materialId);
	

	

}

