package com.jcduque.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jcduque.dto.CategoryDTO;
import com.jcduque.entities.Category;
import com.jcduque.repositories.CategoryRepository;
import com.jcduque.services.exceptions.EntidadeNaoEncontrada;

import net.bytebuddy.implementation.bytecode.Throw;

@Service
public class CategoryService {
	
	@Autowired
	CategoryRepository repository;

	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaged(Pageable pageable) {
		
		Page<Category> list = repository.findAll(pageable);
		return list.map(x -> new CategoryDTO(x));
	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
	
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(() -> new EntidadeNaoEncontrada("ID não encontrado"));
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
			Category entity = repository.getOne(id);
			entity.setName(dto.getName());
			entity = repository.save(entity);
			return new CategoryDTO(entity);
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
}
