package tn.esprit.spring.Service;

import java.util.List;
import java.util.Map;

import tn.esprit.spring.entities.Product;

public interface ProductService {
	
	List<Product> retrieveAllProducts();
	Product addProduct(Product p);
	void deleteProduct(int id);
	Product updateProduct(Product u);
	Product retrieveProduct(int id);
	Double retrieveMinPrice(int id);
	List<Product> getRandomProducts();
	List<Product> getBestSellers(int limit);
	int getOfferNumbers(int id);
	Product getBestSeller();
	int getProductRating(int id);
	int getSoldNumber(int id);
	Map<Product, Double> getRecommandation(Long user, int nbr_rec, boolean update, int neigh, int products_pop, int user_pop);
	List<Product> searchProducts(String msg);
	
	///////////////TEST////////////////
	void generateRatings();
	
	//////////////RECOMMANDATION//////////////
	public List<Integer> getAllRatedProducts();
	public List<Object[]> getProductVector(int id);
	public List<Integer> getTopUsers(Long id, int limit);
	
}
