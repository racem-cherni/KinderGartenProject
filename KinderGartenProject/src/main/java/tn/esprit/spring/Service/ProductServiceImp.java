package tn.esprit.spring.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Product;
import tn.esprit.spring.repository.ProductRepository;

@Service
public class ProductServiceImp implements ProductService {

	@Autowired
	private ProductRepository ProductRepository;

	@Override
	public Product addProduct(Product p) {

		return ProductRepository.save(p);
	}

	@Override
	public void deleteProduct(int id) {

		ProductRepository.deleteById(id);
	}

	@Override
	public List<Product> retrieveAllProducts() {

		List<Product> products = (List<Product>) ProductRepository.findAll();
		return products;
	}

	@Override
	public Product updateProduct(Product u) {
		
		return ProductRepository.save(u);
	}

	@Override
	public Product retrieveProduct(int id) {
		Product p = ProductRepository.findById((id)).orElse(null);
		return p;
	}
	
	public int getTotalProducts() {
		return ProductRepository.gettotalproducts();
	}

	@Override
	public Product getBestSeller() {

		
		
		return retrieveProduct(ProductRepository.getBestSellerById());
	}

}
