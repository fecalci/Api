package com.Rooftop.Api.service;

import com.Rooftop.Api.model.Text;

public interface TextService {

	Text submitText(String text, Integer chars);
	
	String hashText(String text);
	
	Long validateExistence(String textHashed, String charsHashed);

}
