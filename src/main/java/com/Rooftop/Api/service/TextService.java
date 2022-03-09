package com.Rooftop.Api.service;

import java.util.List;

public interface TextService {

	void submitText(String text, Integer chars);
	
	String hashText(String text);
	
	Long validateExistence(String textHashed, String charsHashed);

}
