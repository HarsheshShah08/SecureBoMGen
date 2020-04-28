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

import javax.transaction.Transactional;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.BoMt1;
import com.example.demo.model.User;
import com.example.demo.repository.BoMt1Repository;

/**
 * @author Harshesh.Shah
 *
 */
@Service
public class SetBoMVersionService {
	
	@Autowired
	private BoMt1Repository bomt1;
	
	private int nBoMVersion;
	private int nMaxItem;
	
	public int getnBoMVersion() {
		return nBoMVersion;
	}

	public void setnBoMVersion(int nBoMVersion) {
		this.nBoMVersion = nBoMVersion;
	}

	public int getnMaxItem() {
		return nMaxItem;
	}

	public void setnMaxItem(int nMaxItem) {
		this.nMaxItem = nMaxItem;
	}
	
	
	public void getBoMBasicDetails(String sModule)  {
		

		boolean isEmpty = bomt1.findByDesign_Module(sModule).isEmpty();
		if(isEmpty) {
				
			
			this.setnBoMVersion(1);
			this.setnMaxItem(10);
			
			
			
		}else {

			
			for(BoMt1 n : bomt1.findByDesign_Module(sModule)) {
				
				System.out.println(n.getBOM_Version());

				this.setnBoMVersion(n.getBOM_Version()+1);
				this.setnMaxItem(n.getItemNumber()+1);
				break;
			
			}
		}
		
		
	}

	

}
