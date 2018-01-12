package com.example.acortadorurl.utils;

import java.nio.charset.StandardCharsets;

import javax.swing.border.MatteBorder;

import org.springframework.stereotype.Component;

import com.google.common.hash.Hashing;

/**
 * Metodos de transformacion y procesado de Cadenas
 * @author carlos
 *
 */
@Component
public class StringUtils 
{
	/**
	 * Genera un numero aleatorio para relacionarlo con la url larga
	 * @return Cadena aleatoria generada de longitud 5
	 */
	public String getRandomNumber() {

		return String.format("%05h",String.format("%X",(int) (Math.random() * 99999) + 1));

	}
	
	/**
	 * Genera un identificador hash que relaciona univocamente a la url larga
	 * @param urlLarga
	 * @return Cadena con el codigo Hah Generado
	 */
	public String getCodHash(String urlLarga){
		
		return Hashing.murmur3_32().hashString(urlLarga, StandardCharsets.UTF_8).toString();
	}
	
	/**
	 * Algoritmo propio que calcula un identeficador univoco sumando el valor de cada caracter y multiplicandolo por su posicion elevada al cuadrado
	 * @param urlLarga
	 * @return
	 */
	public String getCodAlgSumMul (String urlLarga) {
		
		Long acumulado=new Long(9999); //Valor inicial
		
		//Sumar el código ascii de cada caracter y lo multiplicamos por su posición elevada al cuadrado
		for (int i=0;i<urlLarga.length();i++){
			acumulado+=urlLarga.charAt(i)*(long)Math.pow(i, 2);
		}
		
		//Formatear en hexadecimal
		return String.format("%h",(long) acumulado);
			
	}
	
}

