package com.app.productos;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.app.products.ProductsAppTestApplication;
import com.app.products.model.Product;
import com.app.products.repository.ProductsRepository;

@SpringBootTest(classes = ProductsAppTestApplication.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ProductTest {

	@Autowired
	private ProductsRepository repository;
	
	//Test para guardar producto
	@Test
	@Rollback(false)
	public void testSaveProduct() {
		Product product = new Product("TV Samsung 4k", 1500);
		Product productSave= repository.save(product);
		
		assertNotNull(productSave);
	}
	
	//Test para buscar producto por nombre
	@Test
	public void testSeachName() {
		String name = "TV Samsung 4k";
		Product product = repository.findByName(name);
		
		assertThat(product.getName()).isEqualTo(name);
	}
	
	//Test para buscar producto por nombre que no existe
	@Test
	public void testSeachNameNoExist() {
		String name = "Pay Station 5";
		Product product = repository.findByName(name);
		
		assertNull(product);
	}
	
	
	//Test para actualizar producto
		@Test
		@Rollback(false)
		public void testUpdateProduct() {
			String nameProduct = "TV LG 4k"; //el nuevo valor
			Product product = new Product(nameProduct, 4500); //valores nuevos
			product.setId(1); //Id del producto a actualizar
			
			repository.save(product);
			Product ProductUpdate = repository.findByName(nameProduct);
			
			assertThat(ProductUpdate.getName()).isEqualTo(nameProduct);
		}
}
