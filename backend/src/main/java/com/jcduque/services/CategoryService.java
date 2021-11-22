package com.jcduque.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jcduque.entities.Category;
import com.jcduque.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	CategoryRepository repository;

	
	public List<Category> finsAll() {
		
		List<Category> list = repository.findAll();
		return list;
	}
}
