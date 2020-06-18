package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import tn.esprit.spring.entities.Rating;
import tn.esprit.spring.entities.Parent;
import tn.esprit.spring.entities.Child;
import tn.esprit.spring.entities.KinderGarten;


public interface RatingRepository extends  CrudRepository<Rating, Integer> {
	@Query("Select "
			+ "rec from Rating rec  "
			+ "join rec.jardin u "
			+ "where u=:userd")
    public List<Rating> getAllRatingByJardinn(@Param("userd") KinderGarten user);
	
	@Query("Select "
			+ "u from Rating rat Join rat.jardin u "
			+ "GROUP BY u "
			+ "ORDER BY ( "
			+ "SUM(rat.rating_note)/Count(rat.rating_note)) DESC  "
			)
    public List<KinderGarten> getAllJardinTrierParNotee();
	
	@Query("Select "
			+ "rec from Rating rec  "
			+ "join rec.parent u "
			+ "where u=:userd")
    public List<Rating> getAllRatingByParentt(@Param("userd") Parent user);

	
	@Query("Select "
			+ "SUM(rat.rating_note) from Rating rat  "
			+ "join rat.jardin u "
			+ "where u=:userd")
	public int getsommeRatingByJardinn(@Param("userd") KinderGarten user);
	
	@Query("Select "
			+ "COUNT(rat) from Rating rat  "
			+ "join rat.jardin u "
			+ "where u=:userd")
	public int getcountRatingByJardinn(@Param("userd") KinderGarten user);

	@Query("Select "
			+ "rec from Rating rec  "
			+ "where rec.jardin=:userd and rec.parent=:userdd ")
	public List<Rating> verifierexistance(@Param("userd") KinderGarten user, @Param("userdd") Parent userr);

	@Query("Select "
			+ "u,(SUM(rat.rating_note)/COUNT(rat)) as note from Rating rat Join rat.jardin u "
			+ "GROUP BY u "
			+ "ORDER BY ( "
			+ "SUM(rat.rating_note)/Count(rat.rating_note)) DESC  "
			)
	public List<Object> getJardinNoteTrierParNote();

	@Query("Select "
			+ "u,(SUM(rat.note_nourriture)/COUNT(rat)) as note from Rating rat Join rat.jardin u "
			+ "GROUP BY u "
			+ "ORDER BY ( "
			+ "SUM(rat.note_nourriture)/Count(rat)) DESC  "
			)
	public List<Object> getJardinTrierParNoteNourriture();

	@Query("Select "
			+ "u,(SUM(rat.note_maitres)/COUNT(rat)) as note from Rating rat Join rat.jardin u "
			+ "GROUP BY u "
			+ "ORDER BY ( "
			+ "SUM(rat.note_maitres)/Count(rat)) DESC  "
			)
	public List<Object> getJardinTrierParNoteMaitre();
	
	@Query("Select "
			+ "u,(SUM(rat.note_activites)/COUNT(rat)) as note from Rating rat Join rat.jardin u "
			+ "GROUP BY u "
			+ "ORDER BY ( "
			+ "SUM(rat.note_activites)/Count(rat)) DESC  "
			)
	public List<Object> getJardinTrierParNoteActivites();

	
	//////////////////////////////////////
	/////////////////////////////////////
	////////////////////////////
	
	@Query("Select "
			+ "(SUM(rat.rating_note)/COUNT(rat)) as note from Rating rat Join rat.jardin u "
			+ "where u=:userd "
			+ "ORDER BY ( "
			+ "rat.rating_date)  DESC  "
			)
	public Double getnoteByJardin(@Param("userd") KinderGarten jardin);

	
	@Query("Select "
			+ "(SUM(rat.note_nourriture)/COUNT(rat.note_nourriture)) as note from Rating rat Join rat.jardin u "
			+ "where u=:userd ")
	public Double getnoteNourritureByJardin(@Param("userd") KinderGarten user);

	
	@Query("Select "
			+ "(SUM(rat.note_activites)/COUNT(rat.note_activites)) as note from Rating rat Join rat.jardin u "
			+ "where u=:userd ")
	public Double getnoteActiviteByJardin(@Param("userd") KinderGarten jardin);

	
	@Query("Select "
			+ "(SUM(rat.note_maitres)/COUNT(rat.note_maitres)) as note from Rating rat Join rat.jardin u "
			+ "where u=:userd ")
	public Double getnoteMaitreByJardin(@Param("userd") KinderGarten jardin);

	
	@Query("Select "
			+ "rat.note_activites as note from Rating rat Join rat.jardin u "
			+ "where u=:userd and rat.parent=:userf ")
	public Double getnoteActiviteByJardinParent(@Param("userd") KinderGarten jardin,@Param("userf") Parent parent);

	
	@Query("Select "
			+ "rat.note_maitres as note from Rating rat Join rat.jardin u "
			+ "where u=:userd and rat.parent=:userf ")
	public Double getnoteMaitreByJardinParent(@Param("userd") KinderGarten jardin,@Param("userf") Parent parent);

	
	@Query("Select "
			+ "rat.note_nourriture from Rating rat  "
			+ "where rat.jardin=:userd and rat.parent=:userf ")
	public Double getnoteNourritureByJardinParent(@Param("userd") KinderGarten jardin,@Param("userf") Parent parent);

	
	@Query("Select "
			+ "rat.rating_description from Rating rat  "
			+ "where rat.jardin=:userd and rat.parent=:userf ")
	public String getratesdetailByJardinParent(@Param("userd") KinderGarten jardin,@Param("userf") Parent parent);

	@Query("Select "
			+ "c from Child c  "
			+ "where c.kindergarten=:userd and c.parents=:userf ")
	public Child parentOfJardin(@Param("userf") Parent parent, @Param("userd") KinderGarten jardin);

	@Query("Select "
			+ "k from KinderGarten k  ")
	public List<KinderGarten> getAllJardin();

	@Query("Select "
			+ "rat from Rating rat  "
			+ "where rat.jardin=:userd ")
	public List<Rating> getEvaluatioByKinder(@Param("userd") KinderGarten jardin);

}
