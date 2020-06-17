package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Parent;

public interface KinderGartenRepository extends JpaRepository<KinderGarten, Long>{
	
	
	
	
//@Query("select DISTINCT k from KinderGarten k join k.kid c join  c.parents p where p.id= :idP  ")	
//public List<KinderGarten> findKindergartenByparent(@Param("idP") long idP);


@Query("select DISTINCT v.sourceUser from Vote v where v.targetUser= :parent and v.sourceUser= :kinder ")	
public KinderGarten findKindergartenNotVoted(@Param("parent") Parent parent,@Param("kinder") KinderGarten kinder);


@Query("select  k from KinderGarten k join k.userapp u where  u.actived=true  ")	
public List<KinderGarten> recherchKinder();

@Query("select  k from KinderGarten k join k.userapp u where  u.actived=false  ")	
public List<KinderGarten> getkinderforAdmin();

@Query("select  k from KinderGarten k where k.userapp.id =:id")
public KinderGarten getKinder(@Param("id") Long id);






}
