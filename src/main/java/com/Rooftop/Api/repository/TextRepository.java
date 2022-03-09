package com.Rooftop.Api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Rooftop.Api.model.Text;

@Repository
public interface TextRepository extends JpaRepository <Text,Long> {
	
	Text findByContent(String textHashed);
	
	Text findByParameter(String charsHashed);
	
	Text findByParameterAndContent(String textHashed, String charsHashed);
	
}
