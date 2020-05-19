package tn.esprit.spring.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Product;
import tn.esprit.spring.repository.ProductRepository;
import tn.esprit.spring.repository.SaleRatingHistoryRepository;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.repository.OrderRepository;


@Service
public class ProductServiceImp implements ProductService {

	@Autowired
	private ProductRepository ProductRepository;

	@Autowired
	private SaleRatingHistoryRepository SaleRatingHistoryRepository;

	@Autowired
	private UserRepository UserRepository;

	@Autowired
	private OrderRepository OrderRepository;
	
	@Autowired
	private RecommandService RecommandService;
	
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

	@Override
	public Double retrieveMinPrice(int id) {

		return ProductRepository.getMinPrice(id);
	}

	@Override
	public List<Product> getRandomProducts() {

		List<Product> p = ProductRepository.getProductsOnSale();
		List<Product> randomp = new ArrayList<Product>();
		
		Set<Integer> ids = new HashSet<Integer>();
		
		Random r = new Random();

		for (int i = 0; i<3 ; i++) {
			int index = r.nextInt(((p.size()-1) - 0) + 1) + 0;
			Product pp = p.get(index);
			p.remove(index);
			randomp.add(pp);
		}

		
		for (Product pro : randomp)
			System.out.println(pro.toString());
		
		return randomp;
	}

	@Override
	public List<Product> getBestSellers(int limit) {

		List<Integer> productsid = ProductRepository.getBestSeller(limit);
		List<Product> products = new ArrayList<Product>();

		for (int a : productsid) {

			products.add(this.retrieveProduct(a));

		}

		return products;
	}

	@Override
	public int getOfferNumbers(int id) {

		return ProductRepository.getOffersNumber(id);
	}

	////////////////// TEST///////////////////
	@Override
	public void generateRatings() {

		/*logger.warn("starting");

		List<Order> dispatchedorders = OrderRepository.getDispatchedOrders();

		SaleRatingHistory rate = new SaleRatingHistory();

		logger.warn("Found " + dispatchedorders.size() + " dispatched orders");

		Random r = new Random();

		logger.warn("random " + r);

		for (Order or : dispatchedorders) {

			for (PanierProduct pp : or.getPanier().getOrderedoffers()) {

				logger.warn("order " + or.getId());
				logger.warn("PanierProduct Offer ID" + pp.getOffer().getId());
				logger.warn("User id:" + or.getUser().getId());
				rate = new SaleRatingHistory(new RateUsersPK(pp.getOffer().getId(), or.getUser().getId()), or.getUser(),
						pp.getOffer(), new Date(), null);

				int chance = r.nextInt((10 - 0) + 1) + 0;

				logger.warn("chance " + chance);

				if (chance <= 7)
					rate.setRating((r.nextInt((4 - 2) + 1) + 2));
				else if (chance == 9)
					rate.setRating(1);
				else
					rate.setRating(5);

			}

			SaleRatingHistoryRepository.save(rate);

		*/
		
	/*System.out.print("]");		
	System.out.print("\n");
	System.out.print("[ ");*/
	
	/*List<Integer> users = ProductRepository.getTopUsers(new Long(1), 50);
	List<Double> a = r.generateNormlizedVector(users,ProductRepository.getProductVector(10));
		
	System.out.println(a);
	System.out.println(a.size());*/	
	
	/*System.out.print("]");
	System.out.print("\n");*/
			
		}
	@Override
	public int getProductRating(int id) {
		
		return (int) Math.ceil(ProductRepository.getProductRating(id));
	}

	@Override
	public List<Integer> getAllRatedProducts() {
		
		return null;
	}

	@Override
	public List<Object[]> getProductVector(int id) {

		return ProductRepository.getProductVector(id);
	}

	@Override
	public List<Integer> getTopUsers(Long id, int user_population) {

		return ProductRepository.getTopUsers(id, user_population);
	}

	@Override
	public int getSoldNumber(int id) {
		
		return ProductRepository.getSoldNumber(id);
	}

	@Override
	public Map<Product, Double> getRecommandation(Long user, int nbr_rec, boolean update, int neigh, int products_pop, int user_pop) {
		
		RecommandService.setNbr_rec(nbr_rec);
		RecommandService.setUpdate(update);
		RecommandService.setNeighborhood(neigh);
		RecommandService.setProduct_population(products_pop);
		RecommandService.setUser_population(user_pop);
		
		Map<Integer, Double> ids = RecommandService.getRecommandation(user);
		
		Map<Product, Double> products = new HashMap<Product, Double>();
		for (Map.Entry<Integer, Double> id : ids.entrySet())
			products .put(this.retrieveProduct(id.getKey()), id.getValue());
		
		Map<Product, Double> sorted_products = products.entrySet().stream()
				.sorted(Entry.<Product, Double>comparingByValue().reversed())
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		
		return sorted_products;
	}

}
