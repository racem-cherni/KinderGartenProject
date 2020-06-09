package tn.esprit.spring.repository;



import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.foodmedrecwithgramage;
import tn.esprit.spring.entities.foodsandtheircallories;
@Repository
public interface FoodmedrecwithgramageRepository extends JpaRepository<foodmedrecwithgramage, Long> {
	@Query("select e from foodmedrecwithgramage e "
			+ "join e.foodsandtheircallories t "
			+"join t.KinderGarten s "
			+ "where s.id=:idkinder ")
		
	public List<foodmedrecwithgramage> foodbyidkinder(@Param("idkinder")long idkinder);
	
	@Query("select e from foodmedrecwithgramage e "
			+ "join e.foodsandtheircallories t "
			+"join t.KinderGarten s "
			+ "where s.id=:idkinder "
			+"GROUP BY e.publishedDate")
	public List<foodmedrecwithgramage> foodbyidkinder1(@Param("idkinder")long idkinder);
	
	
	@Query("select e from foodmedrecwithgramage e "
			+ "join e.MedicalRec t "
			+"join t.child c "
			+ "where c.id=:idchild")
	public List<foodmedrecwithgramage> foodbychildid(@Param("idchild")long idchild);
	
	@Query("select e.foodsandtheircallories from foodmedrecwithgramage e "
			+ "join e.MedicalRec t "
			+"join t.child c "
			+ "where c.id=:idchild")
	public List<foodsandtheircallories> foodcalorbychildid(@Param("idchild")long idchild);
	
	@Query("select SUM(e.gramsneeded) from foodmedrecwithgramage e "			
			+ "where e.publishedDate=:date")
	public float calculgramsforthisday(@Param("date")Date date);
	
}
