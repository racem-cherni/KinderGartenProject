package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Child;
import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.MedicalRec;
import tn.esprit.spring.entities.foodsandtheircallories;
@Repository
public interface MedicalRecRepository extends JpaRepository<MedicalRec,Long>{

	@Query("select e.MedicalRec from Child e "			
			+ "where e.id=:idmedrec")
	public MedicalRec foodbyidkinder(@Param("idmedrec")long idmedrec);
	
	@Query("select count(e) from MedicalRec e "	
			+"join e.child h "
			+"join h.kindergarten t "
			+ "where t.id=:idkinder AND e.Allergy=:glut")
	public int calculnbrallegrybychild(@Param("idkinder")long idkinder,@Param("glut")String glut);
	
	@Query("select count(e) from MedicalRec e "	
			+"join e.child h "
			+"join h.kindergarten t "
			+ "where t.id=:idkinder AND e.Medicalprob=:skninf")
	public int calculnbrinfection(@Param("idkinder")long idkinder,@Param("skninf")String skninf);
	
	@Query("select count(e) from MedicalRec e "	
			+"join e.child h "
			+"join h.kindergarten t "
			+ "where t.id=:idkinder AND e.blood_groups=:bld")
	public int calculnbralblood(@Param("idkinder")long idkinder,@Param("bld")String bld);
	
}
