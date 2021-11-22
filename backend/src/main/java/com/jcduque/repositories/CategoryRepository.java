package com.jcduque.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jcduque.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
