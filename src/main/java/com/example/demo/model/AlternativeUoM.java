package com.example.demo.model;

import javax.persistence.*;


@Entity
@Table(name="AlternativeUoM")
public class AlternativeUoM {
	
	@Id
	@GeneratedValue
	@Column(name = "id", updatable = false, nullable = false)
	private int id;
	private String materialId;
	private String sUoM;
	private float denominator;
	private float numerator;
	
	
	
	public String getMaterialId() {
		return materialId;
	}
	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getsUoM() {
		return sUoM;
	}
	public void setsUoM(String sUoM) {
		this.sUoM = sUoM;
	}
	public float getDenominator() {
		return denominator;
	}
	public void setDenominator(float denominator) {
		this.denominator = denominator;
	}
	public float getNumerator() {
		return numerator;
	}
	public void setNumerator(float numerator) {
		this.numerator = numerator;
	}
	@Override
	public String toString() {
		return "AlternativeUoM [id=" + id + ", materialId=" + materialId + ", sUoM=" + sUoM + ", denominator="
				+ denominator + ", numerator=" + numerator + "]";
	}



	

	
	
}