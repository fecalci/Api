package com.Rooftop.Api.serviceImpl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Rooftop.Api.model.Text;
import com.Rooftop.Api.repository.TextRepository;
import com.Rooftop.Api.service.TextService;

@Service
public class TextServiceImpl implements TextService{
	
	@Autowired 
	private TextRepository textRepository;
	
	
	public Text submitText(String text, Integer chars) {			
		Text myText = new Text();
		
		//Hasheo el texto y parametros recibidos, para validarlos y guardarlos en base de datos.
		String hashedText = this.hashText(text);
		String hashedParameter = this.hashText(chars.toString());		
		Long idExistence = this.validateExistence(hashedText, hashedParameter);
		if (idExistence != null) {
			myText.setId(idExistence);
			return myText;
		}
		else {
			//Creo un HashMap para almacenar los key-values
			HashMap<String, Integer> map = new HashMap<>();
			//Recorro el texto para analizarlo y contar repeticiones
			for(int i = 0; i < text.length()-(chars-1);++i) {
				String myString = text.substring(i, i + chars);
				if(!map.containsKey(myString)) {
					map.put(myString, 1);
				}
				else {
					map.put(myString, map.get(myString) + 1);
				}
			}
			myText.setContent(hashedText);
			myText.setParameter(hashedParameter);
			myText.setResult(map);
			textRepository.save(myText);
		}
		return myText;
		
	}
	
	public String hashText(String text){
		
		String myHash = "";
		
			try {
				byte[] bytesOfMessage = text.getBytes();
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] theMD5Digest = md.digest(bytesOfMessage);
				myHash = DatatypeConverter.printHexBinary(theMD5Digest).toUpperCase();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}						
				
		return myHash;
	}
	
	public Long validateExistence(String textHashed, String charsHashed) {		
		Text validate = textRepository.findByContentAndParameter(textHashed, charsHashed);
		if(validate != null){
			return validate.getId();
		}		
		return null;
	}

}
