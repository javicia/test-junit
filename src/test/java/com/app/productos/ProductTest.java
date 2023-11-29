package com.app.productos;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
@TestMethodOrder(OrderAnnotation.class)
public class ProductTest {

	@Autowired
	private ProductsRepository repository;
	
	//Test para guardar producto
	@Test
	@Rollback(false)
	@Order(1)
	public void testSaveProduct() {
		Product product = new Product("PC SAMSUNG", 850);
		Product productSave= repository.save(product);
		
		assertNotNull(productSave);
	}
	
	//Test para buscar producto por nombre
	@Test
	@Order(2)
	public void testSeachName() {
		String name = "PC ASUS";
		Product product = repository.findByName(name);
		
		assertThat(product.getName()).isEqualTo(name);
	}
	
	//Test para buscar producto por nombre que no existe
	@Test
	@Order(3)
	public void testSeachNameNoExist() {
		String name = "Pay Station 5";
		Product product = repository.findByName(name);
		
		assertNull(product);
	}
	
	
	//Test para actualizar producto
		@Test
		@Rollback(false)
		@Order(4)
		public void testUpdateProduct() {
			String nameProduct = "TV SONY 4k"; //el nuevo valor
			Product product = new Product(nameProduct, 4500); //valores nuevos
			product.setId(27); //Id del producto a actualizar
			
			repository.save(product);
			Product ProductUpdate = repository.findByName(nameProduct);
			
			assertThat(ProductUpdate.getName()).isEqualTo(nameProduct);
		}
		
		
		//Test para listar producto
		@Test
		@Order(5)
		public void testGetProduct() {
			List<Product> products = (List<Product>) repository.findAll();
			for(Product product : products) {
				System.out.println(product);
			}
			assertThat(products).size().isGreaterThan(0);
		
		}
		
		//Test para eliminar producto
		@Test
		@Rollback(false)
		@Order(6)
		public void testDeleteProduct() {
			Integer id = 26;
			
			boolean isExistBeforeDelete  = repository.findById(id).isPresent();
			repository.deleteById(id);
			
			boolean noExistAfterDelete  = repository.findById(id).isPresent();
			
			assertTrue(isExistBeforeDelete);
			assertFalse(noExistAfterDelete);
			
		}
}
