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
import tn.esprit.spring.entities.Rdv_dispo;


public interface RdvRepository extends CrudRepository<Rdv, Integer> {
	
	

	

	@Query("Select "
			+ "rdv from Rdv rdv  "
			+ "join rdv.jardin u "
			+ "where u=:userj and rdv.parent=:userp and rdv.rdv_date=:dateR  ")
	public Rdv existance(@Param("dateR") Date date,@Param("userj") KinderGarten jardin,@Param("userp") Parent parent);
	
	@Query("Select "
			+ "rdv from Rdv rdv  "
			+ "join rdv.jardin u "
			+ "where u=:userj and rdv.rdv_date=:dateR and rdv.rdv_heure=:heure ")
	public Rdv existanceplus(@Param("heure") String heure,@Param("dateR") Date date,@Param("userj") KinderGarten jardin);
	
	@Query("Select "
			+ "rd.heuredm,rd.heurefm from Rdv_dispo rd  "
			+ "join rd.jardin u "
			+ "where u=:userj and rd.jour=:lejour  ")
	public List<String> getHeureDF(@Param("userj") KinderGarten jardin,@Param("lejour") Lesjours dimanche);
	
	@Query("Select "
			+ "count(rdv) from Rdv rdv  "
			+ "join rdv.jardin u "
			+ "where u=:userj and rdv.parent=:userp and rdv.rdv_date=:dateR  ")
	public int existancee(@Param("dateR") Date date,@Param("userj") KinderGarten jardin,@Param("userp") Parent parent);
	
	
	@Query("Select "
			+ "rdv from Rdv rdv  "
			+ "join rdv.jardin u "
			+ "where u=:userj and  rdv.rdv_date>=:dateD and rdv.rdv_date<:dateF order by rdv.rdv_date ASC ")
	public List<Rdv> getUpcomingRdvFromDateUntildate(@Param("userj") KinderGarten jardin,@Param("dateD") Date dateRetour,@Param("dateF") Date date22);

	@Query("Select "
			+ "rdv from Rdv rdv  "
			+ "join rdv.jardin u "
			+ "where u=:userd and  now() <= rdv.rdv_date order by rdv.rdv_date ASC   ")
    public List<Rdv> getAllRdvByJardinn(@Param("userd") KinderGarten user);
	
	@Query("Select "
			+ "rdv from Rdv rdv  "
			+ "join rdv.parent u "
			+ "where rdv.jardin=:userd and  now() <= rdv.rdv_date and u.lastName=:nom order by rdv.rdv_date ASC  ")
    public List<Rdv> getAllRdvByNomparentJJ(@Param("userd") KinderGarten user,@Param("nom") String nom);
	
	@Query("Select "
			+ "rdv from Rdv rdv  "
			+ "join rdv.jardin u "
			+ "where u=:userd and  now() <= rdv.rdv_date and rdv_title=:titlee order by rdv.rdv_date ASC  ")
    public List<Rdv> getAllRdvBytitleJJ(@Param("userd") KinderGarten user,@Param("titlee") String titlee);
	
	@Query("Select "
			+ "rdv from Rdv rdv  "
			+ "join rdv.parent u "
			+ "where u=:userd and now() <= rdv.rdv_date order by rdv.rdv_date ASC ")
    public List<Rdv> getAllRdvByParentt(@Param("userd") Parent user);
	
	

	
	@Query("Select "
			+ "rdv from Rdv rdv  "
			+ "join rdv.jardin u "
			+ "where u=:userd and  now() <= rdv.rdv_date  "
	        + "ORDER BY rdv.rdv_date ASC " )

	public List<Rdv> getAllRdvTrierDateByJardin(@Param("userd") KinderGarten user);
	
	
	@Query("Select "
			+ "rdv from Rdv rdv  "
			+ "join rdv.jardin u "
			+ "where u=:userd and  rdv.rdv_date < CURDATE() order by rdv.rdv_date DESC")
    public List<Rdv> getoldRdvByJardin(@Param("userd") KinderGarten user);
	
	@Query("Select "
			+ "rdv from Rdv rdv  "
			+ "join rdv.jardin u "
			+ "where u=:userd and  now() <= rdv.rdv_date and rdv.rdv_etat=2 order by rdv.rdv_date ASC ")
    public List<Rdv> getRejectedRdvByJardin(@Param("userd") KinderGarten user);
	
	
	@Query("Select "
			+ "rdv from Rdv rdv  "
			+ "join rdv.jardin u "
			+ "where u=:userd and  now() <= rdv.rdv_date and rdv.rdv_etat=1 order by rdv.rdv_date ASC ")
    public List<Rdv> getConfirmedRdvByJardin(@Param("userd") KinderGarten user);

	
	@Query("Select "
			+ "rdv from Rdv rdv  "
			+ "join rdv.jardin u "
			+ "where u=:userd and  CURDATE()=rdv.rdv_date order by rdv.rdv_date ASC")
	public List<Rdv> getTodayRdvByJardin(@Param("userd") KinderGarten user);

	
	@Query("Select "
			+ "rdv from Rdv rdv  "
			+ "join rdv.jardin u "
			+ "where u=:userd and  DATEDIFF(rdv.rdv_date,CURDATE() ) <= 7 and DATEDIFF(rdv.rdv_date,CURDATE())>=0 order by rdv.rdv_date ASC   ")
	public List<Rdv> getweekRdvByJardin(@Param("userd") KinderGarten user);

	
	
	@Query("Select "
			+ "rdv from Rdv rdv  "
			+ "join rdv.jardin u "
			+ "where u=:userd and  CURDATE() < rdv.rdv_date order by rdv.rdv_date ASC ")
	public List<Rdv> getUpcomingRdvByJardin(@Param("userd") KinderGarten user);
	/*@Query("Select "
			+ "rdv from Rdv rdv  "
			+ "join rdv.jardin u "
			+ "where u=:userd and  now() <= rdv.rdv_date order by rdv.rdv_date ASC   ")
    public List<Rdv> getAllRdvByJardinn(@Param("userd") KinderGarten user);
	
	@Query("Select "
			+ "rdv from Rdv rdv  "
			+ "join rdv.jardin u "
			+ "where u=:userd and  now() <= rdv.rdv_date and rdv_title=:titlee order by rdv.rdv_date ASC  ")
    public List<Rdv> getAllRdvBytitleJJ(@Param("userd") KinderGarten user,@Param("titlee") String titlee);
	
	@Query("Select "
			+ "rdv from Rdv rdv  "
			+ "join rdv.parent u "
			+ "where u=:userd and now() <= rdv.rdv_date order by rdv.rdv_date ASC ")
    public List<Rdv> getAllRdvByParentt(@Param("userd") Parent user);
	
	@Query("Select "
			+ "rdv from Rdv rdv  "
			+ "join rdv.parent u "
			+ "where rdv.jardin=:userd and  now() <= rdv.rdv_date and u.Name=:nom order by rdv.rdv_date ASC  ")
    public List<Rdv> getAllRdvByNomparentJJ(@Param("userd") Parent user,@Param("nom") String nom);

	
	@Query("Select "
			+ "rdv from Rdv rdv  "
			+ "join rdv.jardin u "
			+ "where u=:userd and  now() <= rdv.rdv_date  "
	        + "ORDER BY rdv.rdv_date ASC " )

	public List<Rdv> getAllRdvTrierDateByJardin(@Param("userd") KinderGarten user);
	
	
	@Query("Select "
			+ "rdv from Rdv rdv  "
			+ "join rdv.jardin u "
			+ "where u=:userd and  rdv.rdv_date < CURDATE() order by rdv.rdv_date DESC")
    public List<Rdv> getoldRdvByJardin(@Param("userd") KinderGarten user);
	
	@Query("Select "
			+ "rdv from Rdv rdv  "
			+ "join rdv.jardin u "
			+ "where u=:userd and  now() <= rdv.rdv_date and rdv.rdv_etat=2 order by rdv.rdv_date ASC ")
    public List<Rdv> getRejectedRdvByJardin(@Param("userd") KinderGarten user);
	
	
	@Query("Select "
			+ "rdv from Rdv rdv  "
			+ "join rdv.jardin u "
			+ "where u=:userd and  now() <= rdv.rdv_date and rdv.rdv_etat=1 order by rdv.rdv_date ASC ")
    public List<Rdv> getConfirmedRdvByJardin(@Param("userd") KinderGarten user);

	
	@Query("Select "
			+ "rdv from Rdv rdv  "
			+ "join rdv.jardin u "
			+ "where u=:userd and  CURDATE()=rdv.rdv_date order by rdv.rdv_date ASC")
	public List<Rdv> getTodayRdvByJardin(@Param("userd") KinderGarten user);

	
	@Query("Select "
			+ "rdv from Rdv rdv  "
			+ "join rdv.jardin u "
			+ "where u=:userd and  DATEDIFF(rdv.rdv_date,CURDATE() ) <= 7 and DATEDIFF(rdv.rdv_date,CURDATE())>=0 order by rdv.rdv_date ASC   ")
	public List<Rdv> getweekRdvByJardin(@Param("userd") KinderGarten user);

	
	
	@Query("Select "
			+ "rdv from Rdv rdv  "
			+ "join rdv.jardin u "
			+ "where u=:userd and  CURDATE() < rdv.rdv_date order by rdv.rdv_date ASC ")
	public List<Rdv> getUpcomingRdvByJardin(@Param("userd") KinderGarten user);
*/

	
	@Query("Select "
			+ "rdv from Rdv rdv  "
			+ "join rdv.jardin u "
			+ "where u=:userj and  CURDATE()<rdv.rdv_date and rdv.rdv_date<=:dateR order by rdv.rdv_date ASC ")
	public List<Rdv> getUpcomingRdvByJardinUntildate(@Param("userj") KinderGarten jardin,@Param("dateR") Date dateR);

	@Query("Select "
			+ "COUNT(rdv) from Rdv rdv  "
			+ "join rdv.jardin u "
			+ "where u=:userj and rdv.rdv_date=:dateR  ")
	public int getNbrRdvAtDate(@Param("userj") KinderGarten jardin,@Param("dateR") Date dateRetour);


	@Query("Select "
			+ "rdv from Rdv rdv  "
			+ "join rdv.jardin u "
			+ "where u=:userj and rdv.rdv_date=:dateD  ")
	public List<Rdv> getRdvBydate(@Param("userj") KinderGarten jardin,@Param("dateD") Date date1);

	@Query("Select "
			+ "r.parent from Rdv r  "
			+ "where  r.rdv_id=:id ")
	public Parent getParentByRdvID(@Param("id") int idrdv);

	
	@Query("Select "
			+ "rdv from Rdv rdv  "
			+ "join rdv.jardin u "
			+ "where u=:userj and rdv.rdv_date=:dateR and rdv.rdv_heure=:heure ")
	public Rdv existanceplus(@Param("heure") Time heure,@Param("dateR") Date date,@Param("userj") KinderGarten jardin);

	
	
	@Query("Select "
			+ "rd.heuredm from Rdv_dispo rd  "
			+ "join rd.jardin u "
			+ "where u=:userj and rd.jour=:lejour  ")
	public Time getHeureD(@Param("userj") KinderGarten jardin, @Param("lejour") Lesjours dimanche);

	@Query("Select "
			+ "rd.heurefm from Rdv_dispo rd  "
			+ "join rd.jardin u "
			+ "where u=:userj and rd.jour=:lejour  ")
	public Time getHeureF(@Param("userj") KinderGarten jardin, @Param("lejour") Lesjours dimanche);

	
	@Query("Select "
			+ "p from Parent p  ")
	public List<Parent> getAllParent();

	//////////////jsf 
	@Query("Select "
			+ "rdv from Rdv rdv  "
			+ "join rdv.jardin u "
			+ "where u=:userj and rdv.rdv_date=:dateR and rdv.rdv_heure=:heure and rdv.rdv_id!=:id ")
	public Rdv existanceplusforupdate(@Param("heure") Time heure,@Param("dateR") Date date,@Param("userj") KinderGarten jardin,@Param("id") int rdv_id);
	
	@Query("Select "
			+ "count(rdv) from Rdv rdv  "
			+ "join rdv.jardin u "
			+ "where u=:userj and rdv.parent=:userp and rdv.rdv_date=:dateR and rdv.rdv_id!=:id  ")
	public int existanceeforupdate(@Param("dateR") Date date,@Param("userj") KinderGarten jardin,@Param("userp") Parent parent,@Param("id") int rdv_id);

	
	
	@Query("Select "
			+ "r.jardin from Rdv r  "
			+ "where  r.rdv_id=:id ")
	public KinderGarten getJardinByRdvID(@Param("id") int idrdv);

	@Query("Select "
			+ "j from KinderGarten j  ")
	public List<KinderGarten> getAllJardin();
	
	@Query("Select "
			+ "rd from Parent p Join Child c on c.parents=:userp Join KinderGarten k on c.kindergarten.id=k.id Join Rdv_dispo rd on k.id=rd.jardin.id")
	public List<Rdv_dispo> getDisponibiltyforP(@Param("userp") Parent parent);








	@Query("Select "
			+ "rdv from Rdv rdv  "
			+ "join rdv.parent u "
			+ "where u=:userd and  DATEDIFF(rdv.rdv_date,CURDATE() ) <= 7 and DATEDIFF(rdv.rdv_date,CURDATE())>=0 order by rdv.rdv_date ASC   ")
	public List<Rdv> getweekRdvByParent(@Param("userd") Parent user);

	@Query("Select "
			+ "rdv from Rdv rdv  "
			+ "join rdv.parent u "
			+ "where u=:userd and  rdv.rdv_date < CURDATE() order by rdv.rdv_date DESC")
    public List<Rdv> getoldRdvByParent(@Param("userd") Parent user);

	
	@Query("Select "
			+ "rdv from Rdv rdv  "
			+ "join rdv.parent u "
			+ "where u=:userd and rdv.rdv_etat=0 and now() <= rdv.rdv_date order by rdv.rdv_date ASC ")
	public List<Rdv> getRdvByParentToConfirm(@Param("userd") Parent user);

	
	@Query("Select "
			+ "rdv from Rdv rdv  "
			+ "join rdv.parent u "
			+ "where u=:userd and now() <= rdv.rdv_date and (rdv.rdv_etat=1 OR rdv.rdv_etat=2) order by rdv.rdv_date ASC ")
    public List<Rdv> getUpcommingRdvByParent(@Param("userd") Parent user);

	@Query("Select "
			+ "rdv from Rdv rdv  "
			+ "join rdv.parent u "
			+ "where u=:userd and now() <= rdv.rdv_date and rdv.rdv_etat=3 order by rdv.rdv_date ASC ")
	public List<Rdv> getTakenRdvByParent(@Param("userd") Parent user);

	@Query("Select "
			+ "p from Parent p Join Child c on c.parents.id=p.id where c.kindergarten=:userd ORDER BY p.firstName ASC ")
	public List<Parent> getParentOfkindergarten(@Param("userd") KinderGarten jardin);

	@Query("Select "
			+ "k from KinderGarten k Join Child c on c.kindergarten.id=k.id where c.parents=:userd ORDER BY k.KinderGartenName ASC ")
	public List<KinderGarten> getKinderGartenofParent(@Param("userd") Parent parent);

	@Query("Select "
			+ "rd from Parent p Join Child c on c.parents.id=p.id Join KinderGarten k on c.kindergarten=:userd Join Rdv_dispo rd on k.id=rd.jardin.id")
	public List<Rdv_dispo> getDisponibiltyforJ(@Param("userd") KinderGarten jardin);

	@Query("Select "
			+ "rdv from Rdv rdv  "
			+ "join rdv.jardin u "
			+ "where u=:userd and rdv.rdv_title like %:search% order by rdv.rdv_date ASC ")
	public List<Rdv> findRdvByTitle(@Param("search") String searchString, @Param("userd") KinderGarten jardin);

	@Query("Select "
			+ "rdv from Rdv rdv  "
			+ "join rdv.jardin u "
			+ "where u=:userd and ( rdv.parent.firstName like %:search% or rdv.parent.lastName like %:search% ) order by rdv.rdv_date ASC ")
	public List<Rdv> findRdvByParentName(@Param("search") String searchString, @Param("userd") KinderGarten jardin);

	
	
}
