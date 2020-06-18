package tn.esprit.spring.repository;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Lesjours;
import tn.esprit.spring.entities.Parent;
import tn.esprit.spring.entities.Rdv;
import tn.esprit.spring.entities.Reunion;
import tn.esprit.spring.entities.Reunion_dispo;
import tn.esprit.spring.entities.Reunion_feedback;


public interface ReunionRepository extends CrudRepository<Reunion, Integer>{
	
	@Query("Select "
			+ "re from Reunion re  "
			+ "join re.jardin u "
			+ "where u=:userd ORDER BY reun_date ASC ")
    public List<Reunion> getAllReunionByJardinn(@Param("userd") KinderGarten user);
	
	@Query("Select "
			+ "re from Reunion re  "
			+ "join re.parents u "
			+ "where u=:userd  ORDER BY reun_date ASC")
    public List<Reunion> getAllReunionByParentt(@Param("userd") Parent user);

	
	

	
	@Query("Select "
			+ "re.reun_id from Reunion re  "
			+ "where re.jardin=:userd ORDER BY reun_id DESC")
	public List<Integer> getReun_idOrdered(@Param("userd") KinderGarten user);

	
	@Query("Select "
			+ "re from Reunion re  "
			+ "join re.jardin u "
			+ "where u=:userd and   re.reun_date < CURDATE() ORDER BY reun_date ASC ")
	public List<Reunion> getOldReunionByJardin(@Param("userd") KinderGarten user);
	
	
	@Query("Select "
			+ "re from Reunion re  "
			+ "join re.jardin u "
			+ "where u=:userd and   re.reun_date=CURDATE() ORDER BY reun_date ASC ")
	public List<Reunion> getTodayReunionByJardin(@Param("userd") KinderGarten user);
	
	
	@Query("Select "
			+ "re from Reunion re  "
			+ "join re.jardin u "
			+ "where u=:userd and re.reun_date>=CURDATE() ORDER BY reun_date ASC ")
	public List<Reunion> getUpcomingReunionByJardinn(@Param("userd") KinderGarten user);
	
	
	@Query("Select "
			+ "re from Reunion re  "
			+ "join re.parents u "
			+ "where u=:userd and   re.reun_date=CURDATE() ORDER BY reun_date ASC ")
	public List<Reunion> getTodayReunionByParent(@Param("userd") Parent user);
	@Query("Select "
			+ "re from Reunion re  "
			+ "join re.parents u "
			+ "where u=:userd and   re.reun_date>=CURDATE() ORDER BY reun_date ASC ")
	public List<Reunion> getUpcomingReunionByParent(@Param("userd") Parent user);
	

	
	@Query("Select "
			+ "re from Reunion re  "
			+ "join re.jardin u "
			+ "where u=:userj and  re.reun_date>=:dateD and re.reun_date<:dateF ORDER BY reun_date ASC ")
	public List<Reunion> getUpcomingReunionFromDateUntildate(@Param("userj") KinderGarten jardin,@Param("dateD") Date date1,@Param("dateF")Date date2);

	@Query("Select "
			+ "re from Reunion re  "
			+ "join re.jardin u "
			+ "where u=:userd  and re.reun_id!=:id and re.reun_date=:date ORDER BY reun_date ASC")
	public List<Reunion> getReunionsBydateDestinct(@Param("id") int reun_id,@Param("userd") KinderGarten user , @Param("date") Date reun_date);

	@Query("Select "
			+ "re from Reunion re  "
			+ "where re.etat_R=0")
    public List<Reunion> getAllReunionNotRenewed();
	
	
	
	/////////////////
	/////////////////////JSF1
	@Query("Select "
			+ "p from Parent p  ")
    public List<Reunion> getParentbyReunion(@Param("userd") int id);

	

	
	@Query("Select "
			+ "re from Reunion re  "
			+ "where  re.reun_id=:id ")
	public Reunion getReunionByReunID(@Param("id") int reun_id);

	@Query("Select "
			+ "re.parents from Reunion re  "
			+ "where  re.reun_id=:id ")
	public List<Parent> getParentByReunID(@Param("id") int reun_id);

	@Query("Select "
			+ "p from Parent p  ")
	public List<Parent> getAllParent();


//////////////////////////////////
////////////////////////////////////////
///////////////////JSF ///////////////////

@Query("Select "
		+ "rd.heured from Reunion_dispo rd  "
		+ "join rd.jardin u "
		+ "where u=:userj and rd.jour=:lejour  ")
public Time getHeureD(@Param("userj") KinderGarten jardin,@Param("lejour") Lesjours dimanche);

@Query("Select "
		+ "rd.heuref from Reunion_dispo rd  "
		+ "join rd.jardin u "
		+ "where u=:userj and rd.jour=:lejour  ")
public Time getHeureF(@Param("userj") KinderGarten jardin,@Param("lejour") Lesjours dimanche);



@Query("Select "
		+ "re from Reunion re  "
		+ "join re.jardin u "
		+ "where u=:userd  and re.reun_date=:date ORDER BY reun_date ASC")
public List<Reunion> getReunionsBydate(@Param("userd") KinderGarten user , @Param("date") Date reun_date);


@Query("Select "
		+ "re from Reunion re  "
		+ "join re.jardin u "
		+ "where u=:userd  and re.reun_date=:date  ORDER BY reun_date ASC")
public List<Reunion> getReunionsBydateForUpdate(@Param("userd") KinderGarten user , @Param("date") Date reun_date);


@Query("Select "
		+ "rec from Reunion_feedback rec  "
		+ "join rec.reunion r "
		+ "where r.reun_id=:id")
public List<Reunion_feedback> getRdvfbByRdv(@Param("id") int reun_id);

@Query("Select "
		+ "r.parent from Reunion_feedback r  "
		+ "where  r.reunfb_id=:id ")
public Parent getParentByFbID(@Param("id") int idrdv);

@Query("Select "
		+ "COUNT(rec) from Reunion_feedback rec  "
		+ "join rec.reunion r "
		+ "where r.reun_id=:id")
public int getCountfbByRdv(@Param("id") int reun_id);

@Query("Select "
		+ "rec from Reunion_feedback rec  "
		+ "join rec.reunion r "
		+ "where rec.parent=:userd and r.reun_id=:id")
public List<Reunion_feedback> ByRdvApETParent(@Param("id") int reun_idattach, @Param("userd") Parent p);


@Query("Select "
		+ "re from Reunion re  "
		+ "join re.jardin u "
		+ "where u=:userd and DATEDIFF(re.reun_date,CURDATE() ) <= 7 and DATEDIFF(re.reun_date,CURDATE())>=0 ORDER BY reun_date ASC ")
public List<Reunion> getWeekReunionByJardin(@Param("userd") KinderGarten user);

@Query("Select "
		+ "re from Reunion re  "
		+ "join re.jardin u "
		+ "where u=:userd and DATEDIFF(re.reun_date,CURDATE() ) <= 31 and DATEDIFF(re.reun_date,CURDATE())>=0 ORDER BY reun_date ASC ")
public List<Reunion> getMonthReunionByJardin(@Param("userd") KinderGarten user);

@Query("Select "
		+ "re.reun_description from Reunion re  "
		+ "where  re.reun_id=:id ")
public String getDescriptionByReun_id(@Param("id") int reun_idattach);


@Query("Select "
		+ "rec from Reunion_feedback rec  "
		+ "join rec.reunion r "
		+ "where r.reun_id=:id")
public List<Reunion_feedback> getFbByRdv(@Param("id") int reun_idattach);

@Query("Select "
		+ "p from Parent p Join Child c on c.parents.id=p.id where c.kindergarten=:userd ORDER BY p.firstName ASC ")
public List<Parent> getParentOfkindergarten(@Param("userd") KinderGarten jardin);

@Query("Select "
		+ "rec from Reunion_feedback rec  "
		+ "join rec.reunion r "
		+ "where rec.parent=:userd and r.reun_id=:id")
public List<Reunion_feedback> getReunfbByReunETParent(@Param("id") int reun_idattach, @Param("userd") Parent p);

@Query("Select "
		+ "rec from Reunion_feedback rec  "
		+ "join rec.reunion r "
		+ "where r.reun_id=:id")
public List<Reunion_feedback> getReunfbByReun(@Param("id") int reun_idattach);

@Query("Select "
		+ "re from Reunion re  "
		+ "join re.jardin u "
		+ "where u=:userd and re.etat_R=0 and re.reun_date<CURDATE() ORDER BY reun_date ASC ")
public List<Reunion> getReunionToRenew(@Param("userd") KinderGarten jardin);

@Query("Select "
		+ "k from KinderGarten k  ")
public List<KinderGarten> getAllJardin();




@Query("Select "
		+ "re from Reunion re  "
		+ "join re.parents u "
		+ "where u=:userd and re.reun_title like %:search% ORDER BY reun_date ASC")
public List<Reunion> findReunionByTitle(@Param("search") String searchString,@Param("userd") Parent p);

@Query("Select "
		+ "re from Reunion re  "
		+ "join re.parents u "
		+ "where u=:userd and re.jardin.KinderGartenName like %:search% ORDER BY reun_date ASC")
public List<Reunion> findReunionByKinderGarten(@Param("search") String searchString ,@Param("userd") Parent p);

@Query("Select "
		+ "re from Reunion re  "
		+ "join re.parents u "
		+ "where (u.firstName like %:search% or u.lastName like %:search%) and re.jardin=:userd ORDER BY reun_date ASC")
public List<Reunion> findReunionByParentName(@Param("search") String searchString ,@Param("userd") KinderGarten k);

@Query("Select "
		+ "re from Reunion re  "
		+ "join re.jardin u "
		+ "where u=:userd and re.reun_title like %:search% and DATEDIFF(re.reun_date,CURDATE())>=0 ORDER BY reun_date ASC ")
public List<Reunion> findReunionByTitle(@Param("search") String searchString,@Param("userd") KinderGarten jardin);

@Query("Select "
		+ "re from Reunion re  "
		+ "join re.jardin u "
		+ "where u=:userd and u.KinderGartenName like %:search% and DATEDIFF(re.reun_date,CURDATE())>=0 ORDER BY reun_date ASC ")
public List<Reunion> findReunionByKinderGarten(@Param("search") String searchString,@Param("userd") KinderGarten jardin);

@Query("Select "
		+ "rd from Parent p Join Child c on c.parents.id=p.id Join KinderGarten k on c.kindergarten=:userp Join Reunion_dispo rd on k.id=rd.jardin.id")
public List<Reunion_dispo> getDisponibiltyforJ(@Param("userp") KinderGarten jardin);

@Query("Select "
		+ "rd from Parent p Join Child c on c.parents=:userp Join KinderGarten k on c.kindergarten.id=k.id Join Reunion_dispo rd on k.id=rd.jardin.id")
public List<Reunion_dispo> getDisponibiltyforrP(@Param("userp") Parent parent);

@Query("Select "
+ "p from Parent p where p.firstName like %:search% OR p.lastName like %:search%")
public List<Parent> getParentByName(@Param("search") String searchString);


@Query("Select "
		+ "re from Reunion re  "
		+ "join re.parents u "
		+ "where u=:userd and   re.reun_date < CURDATE() ORDER BY reun_date ASC ")
public List<Reunion> getOldReunionByParent(@Param("userd") Parent user);

@Query("Select "
		+ "re from Reunion re  "
		+ "join re.parents u "
		+ "where u=:userd and DATEDIFF(re.reun_date,CURDATE() ) <= 7 and DATEDIFF(re.reun_date,CURDATE())>=0 ORDER BY reun_date ASC ")
public List<Reunion> getWeekReunionByParent(@Param("userd") Parent parent);

@Query("Select "
		+ "re from Reunion re  "
		+ "join re.parents u "
		+ "where u=:userd and DATEDIFF(re.reun_date,CURDATE() ) <= 31 and DATEDIFF(re.reun_date,CURDATE())>=0 ORDER BY reun_date ASC ")
public List<Reunion> getMonthReunionByParent(@Param("userd") Parent parent);
}