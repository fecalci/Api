package com.Rooftop.Api.controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.Rooftop.Api.model.ResponseDto;
import com.Rooftop.Api.model.Text;
import com.Rooftop.Api.model.dto.TextDto;
import com.Rooftop.Api.service.TextService;

@Controller
@CrossOrigin(origins ="*")	
public class textController {
	
	@Autowired 
	private TextService textService;
	
	@RequestMapping(method = RequestMethod.POST, value="/text", produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto> submitText(@RequestBody TextDto textDto) {		
		//Valido la cantidad de chars especificados
			Integer chars = (textDto.getParameter() == null || textDto.getParameter() < 2) ? 2 : textDto.getParameter() > textDto.getContent().length() ?  
					textDto.getContent().length() : textDto.getParameter().intValue();
			Text text = textService.submitText(textDto.getContent(),chars);				
			ResponseDto submitResponse = new ResponseDto();
			submitResponse.setId(text.getId());
			submitResponse.setUrl("/text/" + submitResponse.getId());
		
		return ResponseEntity.ok(submitResponse);
	}
	
	
	@Bean
	public WebMvcConfigurer configure() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/*").allowedOrigins("http://localhost:8080");
			}
			
		};
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value="/text/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getTextById(@PathVariable("id")  Long textId) {
		return new ResponseEntity<>("text", HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/text", produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getText(Integer chars, Integer page, Integer rpp) {
		chars = (chars == null || chars < 2) ? 2: chars.intValue();
		page = (page == null || page < 1) ? 1: chars.intValue();
		rpp = (rpp ==null || rpp < 10) ? 10 : rpp > 100 ? 100: rpp.intValue();  
		return new ResponseEntity<>("text", HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value="/text/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteTextById(@PathVariable("id") Long textId) {
		return new ResponseEntity<>("text", HttpStatus.OK);
	}

}

