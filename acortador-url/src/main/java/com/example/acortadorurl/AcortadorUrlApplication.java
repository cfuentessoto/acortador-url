package com.example.acortadorurl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import com.example.acortadorurl.modelo.Almacen;
import com.example.acortadorurl.utils.FileUtils;

/**
 * Clase que arranca la aplicacion spring al completo: Contexto, Servicios, etc
 * @author carlos
 *
 */
@SpringBootApplication
public class AcortadorUrlApplication extends SpringBootServletInitializer {
	
	public   static Logger log = LogManager.getLogger(AcortadorUrlApplication.class);
	


	/**
	 * Main que arranca la aplicacion
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(AcortadorUrlApplication.class, args);
	}
	
	

}
