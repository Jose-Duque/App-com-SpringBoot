package com.jcduque.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jcduque.dto.CategoryDTO;
import com.jcduque.dto.ProductDTO;
import com.jcduque.dto.ProductDTO;
import com.jcduque.entities.Category;
import com.jcduque.entities.Product;
import com.jcduque.entities.Product;
import com.jcduque.repositories.CategoryRepository;
import com.jcduque.repositories.ProductRepository;
import com.jcduque.services.exceptions.EntidadeNaoEncontrada;

@Service
public class ProductService {

	@Autowired
	ProductRepository repository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
		
		Page<Product> list = repository.findAll(pageRequest);
		List<Category> listCategories = categoryRepository.findAll();
		
		return list.map(x -> new ProductDTO(x, x.getCategories()));
	}
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
	
		Optional<Product> obj = repository.findById(id);
		Product entity = obj.orElseThrow(() -> new EntidadeNaoEncontrada("ID não encontrado"));
		return new ProductDTO(entity, entity.getCategories());
	}
	
	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		copyToEntity(dto, entity);
		entity = repository.save(entity);
		return new ProductDTO(entity);
	}
	
	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			@SuppressWarnings("deprecation")
			Product entity = repository.getOne(id);
			copyToEntity(dto, entity);
			entity = repository.save(entity);
			return new ProductDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new EntidadeNaoEncontrada("ID Not found");
		}
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} 
		catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontrada("ID Deletado " + id);
		}
		catch (DataIntegrityViolationException e) {
			
		}
	}
	
	private void copyToEntity(ProductDTO productDTO, Product entity) {
		entity.setName(productDTO.getName());
		entity.setPrice(productDTO.getPrice());
		entity.setDescription(productDTO.getDescription());
		entity.setDate(productDTO.getDate());
		entity.setImgUrl(productDTO.getImgUrl());
		
		entity.getCategories().clear();
		for (CategoryDTO cat : productDTO.getCategories()) {
			Category category = categoryRepository.getOne(cat.getId());
			entity.getCategories().add(category);
		}
	}
}
