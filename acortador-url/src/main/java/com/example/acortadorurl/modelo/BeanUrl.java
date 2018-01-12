package com.example.acortadorurl.modelo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;



/**
 * Unidad de dato manejada en la aplicacion
 * @author carlos
 *
 */
@Component
public class BeanUrl {
	
	String urlCorta;
	String urlLarga;
	Date fechaInicio;
	Integer numClicks;
	
	public BeanUrl(){};
	public BeanUrl(String linea) throws Exception{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String[] array=linea.split(";");
		urlCorta=array[0];
		urlLarga=array[1];
		fechaInicio=dateFormat.parse(array[2]);
		numClicks=Integer.valueOf(array[3]);
	}
	public String serialize(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return (urlCorta+";"+urlLarga+";"+dateFormat.format(fechaInicio)+";"+numClicks);
	}
	
	
	public String getUrlCorta() {
		return urlCorta;
	}
	public void setUrlCorta(String urlCorta) {
		this.urlCorta = urlCorta;
	}
	public String getUrlLarga() {
		return urlLarga;
	}
	public void setUrlLarga(String urlLarga) {
		this.urlLarga = urlLarga;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Integer getNumClicks() {
		return numClicks;
	}
	public void setNumClicks(Integer numClicks) {
		this.numClicks = numClicks;
	}
	
	
	

}
