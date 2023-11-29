package com.app.products.repository;

import org.springframework.data.repository.CrudRepository;

import com.app.products.model.Product;


public interface ProductsRepository extends CrudRepository<Product, Integer> {

	public Product findByName(String name);
}
