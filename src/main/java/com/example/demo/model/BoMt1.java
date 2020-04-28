package com.example.demo.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.*;


@Entity
@Table(name="SAPBoM1")
public class BoMt1 {
	
	
	@Id
	@GeneratedValue
	@Column(name = "id", updatable = false, nullable = false)
	 private int id;
	 private String headerMaterial;
	 private String validFrom = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
	 private int altBom = 1;
	 private String reasonForChange = "create/update.. ";
	 private int baseQ = 1;
	 private int bomStatus = 1;
	 private int itemNumber;
	 private String itemCat = "L";
	 private String componentMaterial;
	 private float quantity;
	 private String uom;
	 private String Source;
	 private String BoMName;
	 private String Design_Module;
	 private boolean Valid_Header, Valid_H_UoM, Valid_Component, Valid_C_UoM;	
	 private int BOM_Version;
	 private int Revision;
	 private String createdBy;
	 private String modifiedBy;
	 private Date created;
	 private Date modified;
	 private float price;

	 
	 
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}
	public int getAltBom() {
		return altBom;
	}
	public void setAltBom(int altBom) {
		this.altBom = altBom;
	}
	public String getReasonForChange() {
		return reasonForChange;
	}
	public void setReasonForChange(String reasonForChange) {
		this.reasonForChange = reasonForChange;
	}
	public int getBaseQ() {
		return baseQ;
	}
	public void setBaseQ(int baseQ) {
		this.baseQ = baseQ;
	}
	public int getBomStatus() {
		return bomStatus;
	}
	public void setBomStatus(int bomStatus) {
		this.bomStatus = bomStatus;
	}
	public int getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(int itemNumber) {
		this.itemNumber = itemNumber;
	}
	public String getItemCat() {
		return itemCat;
	}
	public void setItemCat(String itemCat) {
		this.itemCat = itemCat;
	}	 
	public String getSource() {
		return Source;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public void setSource(String source) {
		Source = source;
	}
	public String getBoMName() {
		return BoMName;
	}
	public boolean isValid_Header() {
		return Valid_Header;
	}
	public void setValid_Header(boolean valid_Header) {
		Valid_Header = valid_Header;
	}
	public boolean isValid_H_UoM() {
		return Valid_H_UoM;
	}
	public void setValid_H_UoM(boolean valid_H_UoM) {
		Valid_H_UoM = valid_H_UoM;
	}
	public boolean isValid_Component() {
		return Valid_Component;
	}
	public void setValid_Component(boolean valid_Component) {
		Valid_Component = valid_Component;
	}
	public boolean isValid_C_UoM() {
		return Valid_C_UoM;
	}
	public void setValid_C_UoM(boolean valid_C_UoM) {
		Valid_C_UoM = valid_C_UoM;
	}
	public int getBOM_Version() {
		return BOM_Version;
	}
	public void setBOM_Version(int bOM_Version) {
		BOM_Version = bOM_Version;
	}
	public int getRevision() {
		return Revision;
	}
	public void setRevision(int revision) {
		Revision = revision;
	}
	public void setBoMName(String boMName) {
		BoMName = boMName;
	}
	public String getDesign_Module() {
		return Design_Module;
	}
	public void setDesign_Module(String design_Module) {
		Design_Module = design_Module;
	}
	public float getQuantity() {
		return quantity;
	}
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHeaderMaterial() {
		return headerMaterial;
	}
	public void setHeaderMaterial(String headerMaterial) {
		this.headerMaterial = headerMaterial;
	}
	public String getComponentMaterial() {
		return componentMaterial;
	}
	public void setComponentMaterial(String componentMaterial) {
		this.componentMaterial = componentMaterial;
	}
	@Override
	public String toString() {
		return "BoMt1 [id=" + id + ", headerMaterial=" + headerMaterial + ", validFrom=" + validFrom + ", altBom="
				+ altBom + ", reasonForChange=" + reasonForChange + ", baseQ=" + baseQ + ", bomStatus=" + bomStatus
				+ ", itemNumber=" + itemNumber + ", itemCat=" + itemCat + ", componentMaterial=" + componentMaterial
				+ ", quantity=" + quantity + ", uom=" + uom + ", Source=" + Source + ", BoMName=" + BoMName
				+ ", Design_Module=" + Design_Module + ", Valid_Header=" + Valid_Header + ", Valid_H_UoM=" + Valid_H_UoM
				+ ", Valid_Component=" + Valid_Component + ", Valid_C_UoM=" + Valid_C_UoM + ", BOM_Version="
				+ BOM_Version + ", Revision=" + Revision + ", createdBy=" + createdBy + ", modifiedBy=" + modifiedBy
				+ ", created=" + created + ", modified=" + modified + ", price=" + price + "]";
	}










	
	
}