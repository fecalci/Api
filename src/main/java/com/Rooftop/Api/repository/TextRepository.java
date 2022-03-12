package com.Rooftop.Api.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Rooftop.Api.model.Text;

@Repository
public interface TextRepository extends JpaRepository <Text,Long> {
	
	Text findByContent(String textHashed);
	
	Text findByParameter(String charsHashed);
	
	Text findByContentAndParameter(String textHashed, String charsHashed);
	
	Optional<Text> findById(Long id);
	
	Optional<Text> findByIdAndIsActive(Long id, Boolean isActive);
	
	List<Text> findAllByParameterAndIsActive(String chars, Boolean isActive, Pageable page);
	
}
