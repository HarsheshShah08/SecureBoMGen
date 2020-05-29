package com.example.demo.services;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
@ConfigurationProperties(prefix = "genbom.test")
@Configuration("customProperties")
public class CustomProperties {

	private String files;

	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}
}




