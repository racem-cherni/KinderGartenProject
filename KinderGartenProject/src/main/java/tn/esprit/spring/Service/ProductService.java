package tn.esprit.spring.Service;

import java.util.List;

import tn.esprit.spring.entities.Product;

public interface ProductService {
	
	//////////////CRUD////////////////
	List<Product> retrieveAllProducts();
	Product addProduct(Product p);
	void deleteProduct(int id);
	Product updateProduct(Product u);
	Product retrieveProduct(int id);
	///////////////////////////////
	
	Product getBestSeller();
	//void deleteProductByRef(int id);
	
}
