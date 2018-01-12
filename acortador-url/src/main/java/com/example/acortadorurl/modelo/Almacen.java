package com.example.acortadorurl.modelo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.acortadorurl.utils.FileUtils;






/**
 * Clase encargada del almacenamiento y gestion de los datos que son BeanUrls
 * @author carlos
 *
 */
@Component
public class Almacen {
	
	public   Logger log = LogManager.getLogger(this.getClass());
	
	@Autowired
	private FileUtils fileUtils;
	
	File fichAlmacen;
	HashMap<String,BeanUrl> mapBeanUrl;
	
	@Value("${almacen.path}")
	private String almacenPath = "/tmp/almacen.csv";
	
	/**
	 * Es la ultima tarea ejecutado al principio en la construccion del contexto
	 * Se encarga de construir el almacen si no existe y de almacenarlo en memoria dinamica
	 * @throws Exception
	 */
	@PostConstruct
	public  void initAlmacen() throws Exception{
		try{
			fichAlmacen=new File(almacenPath);
		
			if (!fichAlmacen.exists()) fichAlmacen.createNewFile();
			mapBeanUrl=new HashMap<String,BeanUrl>();
			List<String> lineas=fileUtils.getLinesFile(fichAlmacen);
			for (String linea:lineas){
				BeanUrl beanUrl=new BeanUrl(linea);
				mapBeanUrl.put(beanUrl.getUrlCorta(),beanUrl);			
			}
		}catch (Exception e){
			log.error("No se ha podido inicializar Almacen. Revisar si existe ruta:"+almacenPath);
			log.error("Configurar correctamente la ruta almacen en el fichero applications.properties",e);
			throw e;
		}
		
	}
	
	/**
	 * Metodo encargado de actualizar el almacen cuando es invocado
	 */
	public void updateFile() {
		File fichAlmacenAux=new File(almacenPath+"_tmp");
		List<String> lineas=new ArrayList<String>();
		for (Entry<String,BeanUrl> entry: mapBeanUrl.entrySet()){
			lineas.add(entry.getValue().serialize());
		}
		fileUtils.setLinesFile(fichAlmacenAux, lineas);
		fichAlmacen.delete();
		fichAlmacenAux.renameTo(fichAlmacen);
	}
	
	public File getFichAlmacen() {
		return fichAlmacen;
	}
	public void setFichAlmacen(File fichAlmacen) {
		this.fichAlmacen = fichAlmacen;
	}
	public HashMap<String, BeanUrl> getMapBeanUrl() {
		return mapBeanUrl;
	}
	public void setMapBeanUrl(HashMap<String, BeanUrl> mapBeanUrl) {
		this.mapBeanUrl = mapBeanUrl;
	}
	
	

}
