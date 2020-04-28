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

import com.example.demo.model.AlternativeUoM;
import com.example.demo.model.BoMt1;
import com.example.demo.model.User;


/**
 * @author Harshesh.Shah
 *
 */


public interface AlternativeUoMRepository extends JpaRepository <AlternativeUoM, Integer> {
	
	
	
	
	@Query("FROM AlternativeUoM WHERE materialId = ?1 AND  sUoM = ?2")
    ArrayList<AlternativeUoM> findByMaterialIdANDSUoM(String materialId, String sUoM);
	
	
	

}

