package com.example.acortadorurl.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import com.example.acortadorurl.modelo.BeanUrl;

/**
 * Utilidades de Mapas de memoria
 * @author carlos
 *
 */
@Component
public class MapUtils {
	
	
	/**
	 * Convierte un HashMap de BeanUrl en lista BeanUrl
	 * @param map : Hashmap de BeanUrl 
	 * @return : Lista de BeanUrl
	 */
	public List<BeanUrl> convertMapToList(HashMap<String,BeanUrl> map){
		
		List<BeanUrl> list=new ArrayList<BeanUrl>();
		for (Entry<String,BeanUrl> entry:map.entrySet()){
			list.add(entry.getValue());
		}
		return list;
	}

}
