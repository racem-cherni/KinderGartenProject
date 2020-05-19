package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tn.esprit.spring.entities.Advertissement;
import tn.esprit.spring.entities.Relation;
import tn.esprit.spring.entities.UserApp;

public interface AdvertissementRepository extends JpaRepository<Advertissement, Long> {
@Query("select a from Advertissement a where a.sourceUser= :user or a.targetUser= :user ")
public List<Advertissement> findUserRelation(@Param("user") UserApp user );



@Query("select a from Advertissement a where a.targetUser= :target and a.sourceUser= :user   ")
public Advertissement findtargetAd(@Param("target") UserApp target ,@Param("user") UserApp user);


@Query("select a.sourceUser from Advertissement a where a.targetUser= :user and a.active=false and a.relation= :relation ")
public List<UserApp> findInvit(@Param("user") UserApp user ,@Param("relation")Relation relation);




@Query("select a from Advertissement a where a.targetUser= :target and a.sourceUser= :user   ")
public Advertissement findAdByTargetetSource(@Param("target") UserApp target ,@Param("user") UserApp user);





@Query("select a from Advertissement a where a.targetUser= :target or a.sourceUser= :user    ")
public List<Advertissement> findfriend(@Param("target") UserApp target ,@Param("user") UserApp user);



@Query("select a from Advertissement a  where (a.targetUser= :user or a.sourceUser= :user) and a.active=true and a.relation= :relation ")
public List<Advertissement> findRelation(@Param("user") UserApp user ,@Param("relation")Relation relation);


}
