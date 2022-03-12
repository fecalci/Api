package com.Rooftop.Api.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.Rooftop.Api.exception.ApiError;
import com.Rooftop.Api.exception.EmptyJsonResponse;
import com.Rooftop.Api.model.ResponseDto;
import com.Rooftop.Api.model.Text;
import com.Rooftop.Api.model.dto.TextDto;
import com.Rooftop.Api.repository.TextRepository;
import com.Rooftop.Api.service.TextService;

@Controller
@CrossOrigin(origins ="*")	
public class textController {
	
	@Autowired 
	private TextService textService;
	
	@Autowired 
	private TextRepository textRepository;
	
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
	
	//Settings para permitir CORS Origins
	@Bean
	public WebMvcConfigurer configure() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/*").allowedOrigins("http://localhost:8080");
			}
			
		};
	}
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.GET, value="/text/{id}")
	public ResponseEntity getTextById(@PathVariable("id")  Long textId) {
		Optional<Text> text = textService.getText(textId);	
		if(text.isEmpty()) {
			ApiError error = new ApiError();
			return ResponseEntity.ok(error);
		}
		return ResponseEntity.ok(text);
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/text", produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getText(Integer chars, Integer page, Integer rpp) {
		//Valido la información recibida según reglas de negocio
		chars = (chars == null || chars < 2) ? 2: chars.intValue();
		page = (page == null || page <= 1) ? 1: chars.intValue();
		rpp = (rpp == null || rpp <= 10) ? 10 : rpp > 100 ? 100: rpp.intValue();  
		//Utilizo el char hasheado ya que así lo tengo almacenado en db
		String charsHashed = textService.hashText(chars.toString());
		//Creo la paginación según lo recibido. Utilizo -1 para página porque utiliza subíndice 0
		Pageable pageWithElements = PageRequest.of(page-1, rpp);
		List<Text> textPaginated = textRepository.findAllByParameter(charsHashed, pageWithElements);
		//Para cada objecto Text recibido seteo el parámetro al recibido, para no devolver el HASH en el request según reglas de negocio.
		for(Text element : textPaginated) {
			element.setParameter(chars.toString());
		}
		return new ResponseEntity<>(textPaginated, HttpStatus.OK);
	}
	
	
	@RequestMapping(method = RequestMethod.DELETE, value="/text/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity deleteTextById(@PathVariable("id") Long textId) {		
		Optional<Text> text = textRepository.findById(textId);
		if(text.isEmpty() || !text.get().getIsActive()) {
			ApiError error = new ApiError();
			return ResponseEntity.ok(error);
		}
		text.get().setIsActive(false);
		textRepository.save(text.get());
		return ResponseEntity.ok(new EmptyJsonResponse());
	}

}

