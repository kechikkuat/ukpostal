package com.geopostal.ukpostal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GeoUkPostalApplication {

	public static void main(String[] args) {
		try {
			SpringApplication.run(GeoUkPostalApplication.class, args);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
