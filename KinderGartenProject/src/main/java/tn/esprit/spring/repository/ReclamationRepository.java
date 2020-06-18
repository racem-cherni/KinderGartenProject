package tn.esprit.spring.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Parent;
import tn.esprit.spring.entities.Rdv;
import tn.esprit.spring.entities.Rdv_dispo;
import tn.esprit.spring.entities.Reclamation;
import tn.esprit.spring.entities.SujetReclam;

public interface ReclamationRepository extends  CrudRepository<Reclamation, Integer> {
	@Query("Select "
			+ "rec from Reclamation rec  "
			+ "join rec.jardin u "
			+ "where u=:userd ORDER BY reclamation_date DESC")
    public List<Reclamation> getAllReclamationByJardinn(@Param("userd") KinderGarten user);
	
	@Query("Select "
			+ "rec from Reclamation rec  "
			+ "join rec.parent u "
			+ "where u=:userd")
    public List<Reclamation> getAllReclamationByParentt(@Param("userd") Parent user);

	
	@Query("Select "
			+ "COUNT(rec.id) from Reclamation rec  "
			+ "where rec.reclamation_date>=:dated and rec.reclamation_date<=:datef and rec.sujet_reclam=:type ")
	public int getNombreParTypeEtPeriode(@Param("dated") Date dated, @Param("datef") Date datef, @Param("type") SujetReclam type);

	@Query("Select "
			+ "COUNT(rec.id) from Reclamation rec  "
			+ "where rec.reclamation_date>=:dated and rec.reclamation_date<=:datef ")
	public int getNombreParPeriode(@Param("dated") Date dated, @Param("datef") Date datef);

	@Query("Select "
			+ "rec.sujet_reclam,(COUNT(rec.sujet_reclam)/COUNT(rec.id))*100 from Reclamation rec  "
			+ "where rec.reclamation_date>=:dated and rec.reclamation_date<=:datef "
				+ "GROUP BY rec.sujet_reclam ")
	public List<String> getPourcentageParPeriode(@Param("dated") Date dated, @Param("datef") Date datef);

	@Query("Select "
			+ "rec from Reclamation rec  "
			+ "join rec.jardin u "
			+ "where u=:userd and rec.sujet_reclam=:type ORDER BY reclamation_date DESC ")
	public List<Reclamation> getReclamationParType(@Param("userd") KinderGarten jardin,@Param("type") SujetReclam type);

	@Query("Select "
			+ "rec from Reclamation rec  "
			+ "join rec.jardin u "
			+ "where u=:userd and rec.sujet_reclam=:type "
			+"and rec.reclamation_date>=:dated and rec.reclamation_date<=:datef ORDER BY reclamation_date DESC ")
	public List<Reclamation> getReclamationParTypeEtPeriode(@Param("userd") KinderGarten user,@Param("dated") Date dated, @Param("datef") Date datef,@Param("type") SujetReclam type);

	
	
	@Query("Select "
			+ "rec from Reclamation rec  "
			+ "join rec.jardin u "
			+ "where u=:userd and rec.reclamation_date>=:dated and rec.reclamation_date<=:datef ORDER BY reclamation_date DESC"
			+ "")
	public List<Reclamation> getReclamationByPeriode(@Param("userd") KinderGarten user,@Param("dated") Date dated, @Param("datef") Date datef);

	@Query("Select "
			+ "COUNT(rec.id) from Reclamation rec join rec.jardin u "
			+ "where u=:userd and rec.sujet_reclam=:type ")
	public double getNombreTotalParType(@Param("userd") KinderGarten user,@Param("type") SujetReclam type);

	@Query("Select "
			+ "COUNT(rec.id) from Reclamation rec join rec.jardin u where u=:userd ")
	public double getNombreTotalReclam(@Param("userd") KinderGarten user);
	
	
	@Query("Select "
			+ "k from KinderGarten k Join Child c on c.kindergarten.id=k.id where c.parents=:userp")
	public List<KinderGarten> getJardinByParent(@Param("userp") Parent parent);

	
	@Query("Select "
			+ "rec from Reclamation rec  "
			+ "join rec.parent u "
			+ "where u=:userd ORDER BY reclamation_date DESC ")
	public List<Reclamation> getReclamationparparent(@Param("userd") Parent parent);

	@Query("Select "
			+ "rec from Reclamation rec  "
			+ "join rec.jardin u "
			+ "where u=:userd ORDER BY reclamation_date DESC ")
	public List<Reclamation> getLastReclamAddedForJadin(@Param("userd") KinderGarten jardin);
	
	
}
