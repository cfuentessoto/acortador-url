package com.example.acortadorurl;

import java.net.URI;
import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;


/**
 * Clase encargada de lanzar las pruebas de la Aplicacion. Contiene 4 Test lanzados de forma ordena por nombre de metodo
 * @author carlos
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AcortadorUrlApplicationTests {
	
	private MediaType contentType1 = new MediaType(MediaType.TEXT_HTML.getType(),
	            MediaType.TEXT_HTML.getSubtype(),
	            Charset.forName("utf8"));
	private MediaType contentType2 = new MediaType(MediaType.TEXT_PLAIN.getType(),
            MediaType.TEXT_PLAIN.getSubtype(),
            Charset.forName("utf8"));

	private MockMvc mockMvc;
	
	@Autowired
    private WebApplicationContext webApplicationContext;
    
   
	
	@Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        
    }

	/**
	 * Prueba para redireccionar una url larga a partir de la url corta por metodo get
	 * @throws Exception
	 */
	@Test
	public void test30urlLarga() throws Exception {
		System.out.println("getUrlLargaByGet");
		URI uri=new URI("/a18171");
        mockMvc.perform(get(uri))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("https://www.metromadrid.es/es/viaja_en_metro/la_red_al_dia/index.html"));
	}
	
	/**
	 * Prueba para obtener urlLarga a partir de una corta por metodo post
	 * @throws Exception
	 */
	@Test
	public void test40urlLarga() throws Exception {
		System.out.println("getUrlLargaByPost");
		URI uri=new URI("/urlLarga");
        mockMvc.perform(post(uri)
        		.param("id", "a18171"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(contentType2))
        .andExpect(content().string("https://www.metromadrid.es/es/viaja_en_metro/la_red_al_dia/index.html"));
	}
	
	/**
	 * Prueba para obtener una url corta a partir de una corta por metodo get. Se lanza en primer lugar
	 * @throws Exception
	 */
	@Test
    public void test10getIdByGet() throws Exception {
		System.out.println("getIdByGet");
		URI uri=new URI("/acortar/https://www.metromadrid.es/es/viaja_en_metro/la_red_al_dia/index.html");
        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType1))
                .andExpect(content().string("http://localhost:8080/a18171"));
    }
	
	/**
	 * Prueba para obtener metodo url corta a partir de una corta por metodo post.
	 * @throws Exception
	 */
	@Test
    public void test20getIdByPost() throws Exception {
		System.out.println("getIdByPost");
		URI uri=new URI("/acortarPost");
        mockMvc.perform(post(uri)
        		.param("url_larga", "https://www.metromadrid.es/es/viaja_en_metro/la_red_al_dia/index.html"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("url_corta", "http://localhost:8080/a18171"));
    }

}
