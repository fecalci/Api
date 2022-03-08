package controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class textController {
	
	@RequestMapping(method = RequestMethod.POST, value="/text", produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> submitText(String text, Integer chars) {
		chars = (chars == null || chars < 2) ? 2: chars.intValue();
		return new ResponseEntity<>("text", HttpStatus.OK);
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
