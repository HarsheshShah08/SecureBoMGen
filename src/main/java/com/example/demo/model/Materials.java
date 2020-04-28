package com.example.demo.model;

import javax.persistence.*;


@Entity
@Table(name="Materials")
public class Materials {
	
	@Id
	@GeneratedValue
	@Column(name = "id", updatable = false, nullable = false)
	private int id;
	@Column(unique = true)
	private String materialId;
	private String baseUoM;
	private String issueUoM;
	private String purchaseUoM;
	private float price;
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMaterialId() {
		return materialId;
	}
	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
	public String getBaseUoM() {
		return baseUoM;
	}
	public void setBaseUoM(String baseUoM) {
		this.baseUoM = baseUoM;
	}
	public String getIssueUoM() {
		return issueUoM;
	}
	public void setIssueUoM(String issueUoM) {
		this.issueUoM = issueUoM;
	}
	public String getPurchaseUoM() {
		return purchaseUoM;
	}
	public void setPurchaseUoM(String purchaseUoM) {
		this.purchaseUoM = purchaseUoM;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}

	
	@Override
	public String toString() {
		return "Materials [id=" + id + ", materialId=" + materialId + ", baseUoM=" + baseUoM + ", issueUoM=" + issueUoM
				+ ", purchaseUoM=" + purchaseUoM + ", price=" + price + "]";
	}
	
	
	
}