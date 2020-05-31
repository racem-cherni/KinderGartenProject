package tn.esprit.spring.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import tn.esprit.spring.repository.ProductRepository;


@Service
public class RecommandService {

	@Autowired
	private ProductRepository ProductRepository;

	private static int user_population = 96; // max 96
	private static int neighborhood = 3;
	private static int nbr_rec = 20; //nbr produit à recommander
	private static int product_population = 20; // max 20
	private static boolean update = false;
	
	public static void setUser_population(int user_population) {
		RecommandService.user_population = user_population;
	}

	public static void setNeighborhood(int neighborhood) {
		RecommandService.neighborhood = neighborhood;
	}

	public static void setNbr_rec(int nbr_rec) {
		RecommandService.nbr_rec = nbr_rec;
	}

	public static void setProduct_population(int product_population) {
		RecommandService.product_population = product_population;
	}

	public static void setUpdate(boolean update) {
		RecommandService.update = update;
	}

	public static double similarity(List<Double> p1, List<Double> p2) {

		double scalar = 0;
		double module1 = 0;
		double module2 = 0;

		Iterator<Double> iterator1 = p1.iterator();
		Iterator<Double> iterator2 = p2.iterator();

		while (iterator1.hasNext() && iterator2.hasNext()) {

			double current_value1 = iterator1.next();
			double current_value2 = iterator2.next();

			scalar += current_value1 * current_value2;
			module1 += Math.pow(current_value1, 2);
			module2 += Math.pow(current_value2, 2);
		}

		module1 = Math.sqrt(module1);
		module2 = Math.sqrt(module2);

		return scalar / (module1 * module2);
	}

	public static List<Double> generateNormlizedVector(List<Integer> users, List<Object[]> vector) {

		int sum = 0;
		double avg = 0;

		List<Double> final_vector = new ArrayList<Double>();

		for (Object[] obj : vector)
			sum += (int) obj[1];

		avg = sum / ((double) (vector.size()));

		for (Object[] obj : vector)
			obj[1] = ((int) obj[1] - avg);

		boolean test = false;

		for (int u : users) {
			for (Object[] obj : vector) {
				if (u == ((BigInteger) obj[0]).intValue()) {
					test = true;
					final_vector.add((double) obj[1]);
					break;
				}

			}

			if (test == false)
				final_vector.add(0.0);
			test = false;

		}
		return final_vector;
	}

	public static int findIndex(Map<Integer, List<Double>> sim, int id) {

		int index = 0;

		for (Map.Entry<Integer, List<Double>> m : sim.entrySet()) {
			if (m.getKey() == id)
				return index;
			index++;
		}

		return index;

	}

	public Map<Integer, Double> generate_neighborhood(Map<Integer, List<Double>> sim,
			Map<Integer, Integer> user_ratings, int product) {

		Map<Integer, Double> unsortedMap = new HashMap<Integer, Double>();
		Map<Integer, Double> finalvector = new HashMap<Integer, Double>();
		
		int count = 0;

		int index = findIndex(sim, product);
		
		for (Map.Entry<Integer, List<Double>> m : sim.entrySet()) {
			for (Map.Entry<Integer, Integer> u : user_ratings.entrySet()) {
				if (m.getKey() == u.getKey())
					unsortedMap.put(m.getKey(), m.getValue().get(index) + 1);
			}
		}

		Map<Integer, Double> sortedMap = unsortedMap.entrySet().stream()
				.sorted(Entry.<Integer, Double>comparingByValue().reversed())
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		
		Iterator<Map.Entry<Integer, Double>> iterator = sortedMap.entrySet().iterator();

		Random r = new Random();
		
		int ran = r.nextInt((this.neighborhood - 1) + 1) + 1;

		while (count < ran && iterator.hasNext()) {
			
			Map.Entry<Integer, Double> a = iterator.next();
			finalvector.put(a.getKey(), a.getValue());
			count++;
		}

	
    	return finalvector;
	}

	public static Map<Integer, Map<Integer, Double>> extractSimilarityVector(int productid,
			Map<Integer, List<Double>> sims) {

		Map<Integer, Map<Integer, Double>> product = new HashMap<Integer, Map<Integer, Double>>();
		Map<Integer, Double> productsims = new HashMap<Integer, Double>();

		List<Double> aa = sims.get(productid);

		Iterator<Double> iterator1 = aa.iterator();
		Iterator<Map.Entry<Integer, List<Double>>> iterator2 = sims.entrySet().iterator();

		while (iterator1.hasNext() && iterator2.hasNext())
			productsims.put(iterator2.next().getKey(), iterator1.next());

		product.put(productid, productsims);

		return product;
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, Double> getRecommandation(Long connected_user) {

		Map<Integer, List<Double>> ratings = new HashMap<Integer, List<Double>>();
		Map<Integer, List<Double>> sim = new HashMap<Integer, List<Double>>();
		
		Map<Integer, Double> predicted_ratings = new HashMap<Integer, Double>();

		List<Integer> products = ProductRepository.getAllRatedProducts(product_population);
		List<Integer> users = ProductRepository.getTopUsers(connected_user, user_population);
		
		users.add(0, connected_user.intValue()); // ajotu de lutilisateur
		// connecté a l'indice 0

		List<Integer> users_products_not_rated = new ArrayList<Integer>(products);
		users_products_not_rated.removeAll(ProductRepository.getRatedProductsByUser(connected_user)); //products not rated by uconnected_user
		
		ObjectMapper mapper = new ObjectMapper();
		
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		
		for (int product : products) {

			List<Object[]> product_rating = ProductRepository.getProductVector(product);
			ratings.put(product, generateNormlizedVector(users, product_rating));

		}

		if (update) { //update matrice similarity in similarity.json
			
			///// Similarity matrix
			List<Double> product_similarity = new ArrayList<Double>();

			for (Map.Entry<Integer, List<Double>> pro : ratings.entrySet()) {
				for (Map.Entry<Integer, List<Double>> pro2 : ratings.entrySet())
					product_similarity.add(similarity(pro.getValue(), pro2.getValue()));

				sim.put(pro.getKey(), product_similarity);
				product_similarity = new ArrayList<Double>();
				
				try {
					mapper.writeValue(new File("similarity.json"), sim);
				} catch (JsonGenerationException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				} catch (JsonMappingException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				} catch (IOException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
			}
			
			
		} else { //read similarity.json
			
			try {
				Map<String, List<Double>> a = mapper.readValue(new FileInputStream("similarity.json"), Map.class);

				for (Map.Entry<String, List<Double>> aa : a.entrySet())
					sim.put(Integer.parseInt(aa.getKey()), aa.getValue());


			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		List<Object[]> user_ratings = ProductRepository.getUserVector(connected_user);// fih
																						// toutes
																						// les
																						// ratings
																						// mta3
																						// el
																						// user
		Map<Integer, Integer> user_ratings_tomap = new HashMap<Integer, Integer>(); /// bech
																					/// ne7seb
																					/// bih
																					/// predictions

		for (Object[] obj : user_ratings)
			user_ratings_tomap.put((int) obj[0], (int) obj[1]); /// raditou map
																/// bech naje
																/// ne5dem

		//System.out.println(user_ratings_tomap);
		/*System.out.println(generate_neighborhood(sim, user_ratings_tomap, 1));
		System.out.println(prediction(generate_neighborhood(sim, user_ratings_tomap, 1),user_ratings_tomap));*/
		
		
		/* TODO
		 * ena tawa 5arajt vecteur ratings product mta3 user w nafs prodcts similarity par rapport à un produit 
		 * ne7seb predections pour chaque users_products_not_rated
		 * na3mel tri
		 * 
		 * 
		 */
		
		for (int product : users_products_not_rated) {
			predicted_ratings.put(product, prediction(generate_neighborhood(sim, user_ratings_tomap, product), user_ratings_tomap));
		}
		
		//System.out.println(predicted_ratings);

		Map<Integer, Double> unsortedmap = new HashMap<Integer, Double>();
		
		for (Map.Entry<Integer, Double> a : predicted_ratings.entrySet()){
			if (!a.getValue().isNaN())
				unsortedmap.put(a.getKey(), a.getValue());
		}
		
		//System.out.println(unsortedmap);
		
		
		Map<Integer, Double> sortedMap = unsortedmap.entrySet().stream()
				.sorted(Entry.<Integer, Double>comparingByValue())
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		
		//System.out.println(sortedMap);
		
		Iterator<Map.Entry<Integer, Double>> iterator = sortedMap.entrySet().iterator();
		Map<Integer, Double> final_list = new HashMap<Integer, Double>();
		
		int count = 0;
	
		while (count < nbr_rec && iterator.hasNext()) {
			
			Map.Entry<Integer, Double> a = iterator.next();
			final_list.put(a.getKey(), a.getValue());
			count++;
		}
		

		
		//System.out.println(final_list);
		

		return final_list;
	}

	private Double prediction(Map<Integer, Double> generate_neighborhood, Map<Integer, Integer> user_ratings_tomap) {
		
		double sum = 0;
		double coef = 0;
		
		for (Map.Entry<Integer, Double> u : generate_neighborhood.entrySet()) {
			sum += (u.getValue() * user_ratings_tomap.get(u.getKey()));
			coef +=	u.getValue();	
		}
	
		return sum/coef;
	}
}
