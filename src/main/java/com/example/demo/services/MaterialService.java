/**
 * 
 */
package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.AlternativeUoM;
import com.example.demo.model.Materials;
import com.example.demo.repository.AlternativeUoMRepository;
import com.example.demo.repository.MaterialRepository;

/**
 * @author Harshesh.Shah
 *
 */
@Service
public class MaterialService {
	
	@Autowired
	private MaterialRepository mRepository;
	@Autowired
	private AlternativeUoMRepository auRepository;
	
	
	private int i = 0;
	private float nEffectivePrice;
	private boolean validMaterial;
	private boolean validUoM;
	private float fDenominator;
	private float fNumerator;
	
	public void getEffectivePrice(String materialId, String sUoM){
		
	     
	     i = 0;
	     
	     if(mRepository.findByMaterialId(materialId).isEmpty()){

	    	this.setValidMaterial(false);
	    	this.setValidUoM(false);
	    	this.setnEffectivePrice(0);
	    	 
	     }else {
	    	 
	    	 for(Materials m : mRepository.findByMaterialId(materialId)) {
	    		 
	    		 this.setValidMaterial(true);
	    		 
	    		 if(i==0) {
	    			 
	    			 i=1;

	    			 if(sUoM.equalsIgnoreCase(m.getBaseUoM())) {
	    				 
	    				 this.setnEffectivePrice(m.getPrice());
	    				 this.setValidUoM(true);
	    				 
	    			 }else if(sUoM.equalsIgnoreCase(m.getIssueUoM()) || sUoM.equalsIgnoreCase(m.getPurchaseUoM())) {
	    				 
	    				 this.setValidUoM(true);
	    				 for(AlternativeUoM au: auRepository.findByMaterialIdANDSUoM(materialId, sUoM) ) {
	    					 
	    					 if(au.getDenominator()==0) {
	    						 
	    						 if(au.getNumerator()!=0)
	    						 {
	    							 this.setnEffectivePrice( m.getPrice()*(au.getNumerator()));
	    						 }
	    						 
	    					 }else {
	    						 
	    						 this.setnEffectivePrice( m.getPrice()*(au.getNumerator()/au.getDenominator()));
	    					 }
	    					 
	    					 
	    				 }
	    				 
	    			 }else {
	    				 
	    				 this.setValidUoM(false);
	    			     this.setnEffectivePrice(0);
	    			 }//end of else
	    			 
	    		 }//end of if (i=0)
	    	 }//end of for
	    	 
	     }//end of else
		
	}//end of method

	public float getnEffectivePrice() {
		return nEffectivePrice;
	}

	public void setnEffectivePrice(float nEffectivePrice) {
		this.nEffectivePrice = nEffectivePrice;
	}

	public boolean isValidMaterial() {
		return validMaterial;
	}

	public void setValidMaterial(boolean validMaterial) {
		this.validMaterial = validMaterial;
	}

	public boolean isValidUoM() {
		return validUoM;
	}

	public void setValidUoM(boolean validUoM) {
		this.validUoM = validUoM;
	}

	@Override
	public String toString() {
		return "MaterialService [nEffectivePrice=" + nEffectivePrice + ", validMaterial=" + validMaterial
				+ ", validUoM=" + validUoM + "]";
	}
	

}//end of class
