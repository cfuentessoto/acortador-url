package com.example.acortadorurl.utils;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Utilidades de Fichero
 * @author carlos
 *
 */
@Component
public class FileUtils {
	
	
	public   Logger log = LogManager.getLogger(this.getClass());
	

	
	/**
	 * Devuelve el contenido de un fichero en una lista de lineas
	 * @param file :Fichero a transforma en lineas
	 * @return : Lista de lineas
	 */
	public  List<String> getLinesFile(File file){
		
		Scanner s=null;
		List<String> lineasFichero=new ArrayList<String>();
		try {
			
			s = new Scanner(file);

			// Leemos linea a linea el fichero
			while (s.hasNextLine()) {
				lineasFichero.add(s.nextLine()); 	// Guardamos la linea en una lista	
			}
			return lineasFichero;

		} catch (Exception ex) {
			log.error("Error: " + ex.getMessage());
			return lineasFichero;
		} finally {
			// Cerramos el fichero tanto si la lectura ha sido correcta o no
			try {
				if (s != null)
					s.close();
			} catch (Exception ex2) {
				log.error("Error 2: " + ex2.getMessage());
				return lineasFichero;
			}
		}
	}
	
	/**
	 * Escribe en un fichero la lista de lineas pasadas como parametro
	 * @param file : Fichero donde insertar
	 * @param lineas : Lista de lineas a insertar
	 */
	public void setLinesFile(File file,List<String> lineas){
		FileWriter fichero = null;
		try {

			fichero = new FileWriter(file.getAbsolutePath());

			// Escribimos linea a linea en el fichero
			for (String linea : lineas) {
				fichero.write(linea + "\n");
			}

			fichero.close();

		} catch (Exception ex) {
			log.error("Error: " + ex.getMessage());
		}
	}
	
	/**
	 * Escribe una linea en un fichero
	 * @param file
	 * @param linea
	 */
	public void setLineFile(File file,String linea){
		FileWriter fichero = null;
		try {

			fichero = new FileWriter(file.getAbsolutePath());

			// Escribimos 1 linea en el fichero

			fichero.append(linea + "\n");



			fichero.close();

		} catch (Exception ex) {
			log.error("Error: " + ex.getMessage());
		}
	}
	
		
	

}
