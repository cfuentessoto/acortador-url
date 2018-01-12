package com.example.acortadorurl.controlador;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.routines.UrlValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import com.example.acortadorurl.modelo.Almacen;
import com.example.acortadorurl.modelo.BeanUrl;
import com.example.acortadorurl.utils.MapUtils;
import com.example.acortadorurl.utils.StringUtils;


/**
 * Implementa los servicios ofrecidos: Inicio, Documentacion, Obtener Url corta, Obtener url Larga, Redireccionar url
 * @author carlos
 *
 */
@Controller
public class Controlador {
	
	public   Logger log = LogManager.getLogger(this.getClass());
	public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	

	
	@Autowired
	private Almacen almacen;
	
	
	@Autowired
	private MapUtils mapUtils;
	
	@Autowired
	private StringUtils stringUtils;



	
	
	/**
	 * Servicio que inicia los valores de la pagina principal y refresca los valores  del almacen en dicha pagina
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/inicio" , method = RequestMethod.GET)
	public String inicio(Map<String, Object> model) {
		model.put("url_larga","");
		model.put("url_corta","");
		model.put("listAlmacen",mapUtils.convertMapToList(almacen.getMapBeanUrl()));
		return "principal";
	}
	
	/**
	 * Servicio que devuelve la pagina de documentacion
	 * @return
	 */
	@RequestMapping(value="/docu" , method = RequestMethod.GET)
	public String docu() {
		return "documentacion";
	}
	
	
   
    
   /**
    * Servicio que redirecciona a la pagina de url larga indicada por el identificador de la corta
    * Aumenta el contador de accesos a dicha direccion corta
    * @param id : identificador de la url corta
    * @param resp : Objeto con la respuesta del servlet que permite hacer la redireccion
    * @throws Exception
    */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public void redirect(@PathVariable String id, HttpServletResponse resp) throws Exception {
    
    	String result=null;
        log.info("Valor id"+id);
        if (almacen.getMapBeanUrl().containsKey(id)){
        	result=almacen.getMapBeanUrl().get(id).getUrlLarga();
        	almacen.getMapBeanUrl().get(id).setNumClicks(almacen.getMapBeanUrl().get(id).getNumClicks()+1);
        	almacen.updateFile();
            resp.sendRedirect(result);
        }
        else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
          

    }
    
    /**
     * Servicio POST que a partir de una url larga devuelve la corta, si existe la extrae del almacen y si no la calcula y la almacena
     * @param model : Objeto que ofrece Spring para traspaso de variables entre vista y controlador
     * @param url_larga : Parametro de entrada sobre el que se quiere calcular su url_corta
     * @return Devuelve un string que es el nombre de un jsp, la plataforma Spring se encarga de ubicarlo y ejecutarlo. En caso de error redirecciona a la pagina de error
     * @throws Exception
     */
	@RequestMapping(value="/acortarPost",method = RequestMethod.POST)
    public String getUrlCortaPost(ModelMap model,@RequestParam String url_larga) throws Exception {
        final UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});
        if (urlValidator.isValid(url_larga)) {
            String id = stringUtils.getCodAlgSumMul(url_larga);
            if (!almacen.getMapBeanUrl().containsKey(id)){
            	String linea=id+";"+url_larga+";"+dateFormat.format(new Date())+";0";
            	almacen.getMapBeanUrl().put(id,new BeanUrl(linea));		
            	almacen.updateFile();
            }
            model.put("url_corta","http://localhost:8080/"+id);
            model.put("url_larga",url_larga);
            model.put("listAlmacen",mapUtils.convertMapToList(almacen.getMapBeanUrl()));
            return "principal";
        } else
        	 model.put("url_larga",url_larga);
            return "salida_error";
    }
    

    
   
	/**
	 * Servicio POST que a partir de un id devuelve una url larga sin redireccionar
	 * @param id : Identificador de la url corta
	 * @return  Un Objeto ResponseEntity que contiene ademas de la url larga resultante otro parametros de la respuesta
	 * @throws Exception
	 */
	@RequestMapping(value = "/urlLarga", method = RequestMethod.POST)
    public ResponseEntity<String> getUrlLarga( @RequestParam String id) throws Exception {
    
    	String result=null;
        log.info("Valor id"+id);
        if (almacen.getMapBeanUrl().containsKey(id)){
        	result=almacen.getMapBeanUrl().get(id).getUrlLarga();
        	almacen.getMapBeanUrl().get(id).setNumClicks(almacen.getMapBeanUrl().get(id).getNumClicks()+1);
        	almacen.updateFile();
        	return new ResponseEntity<>(result, HttpStatus.OK);
        }
        else {
        	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }      

    }
    
    
	
	/**
	 * Servicio get invocado cuando la url empieza por la cadena /acortar/ y despues tiene otra cadena que es la url larga
	 * @param req: Objeto de la peticion del servlet que contiene los parametros de la peticion
	 * @return  Un Objeto ResponseEntity que contiene ademas de la url larga resultante otro parametros de la respuesta
	 * @throws Exception
	 */
	@RequestMapping(path="/acortar/**",method = RequestMethod.GET)
    public ResponseEntity<String> getUrlCorta(HttpServletRequest req) throws Exception {
    	
        final String queryParams = (req.getQueryString() != null) ? "?" + req.getQueryString() : "";
        String url = (req.getRequestURI() + queryParams).substring(1);
        url=url.split("acortar/")[1];
        final UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});
        if (urlValidator.isValid(url)) {
        	 String id = stringUtils.getCodAlgSumMul(url);
            if (!almacen.getMapBeanUrl().containsKey(id)){
            	String linea=id+";"+url+";"+dateFormat.format(new Date())+";0";
            	almacen.getMapBeanUrl().put(id,new BeanUrl(linea));		
            	almacen.updateFile();
            }
            return new ResponseEntity<>("http://localhost:8080/" + id, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    
   
    
   
    
	
	

}
