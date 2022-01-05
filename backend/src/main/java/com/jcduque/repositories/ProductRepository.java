package com.jcduque.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jcduque.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
