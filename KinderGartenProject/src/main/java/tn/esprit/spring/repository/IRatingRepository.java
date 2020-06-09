package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import tn.esprit.spring.entities.Rate;
import tn.esprit.spring.entities.Rating;




public interface IRatingRepository extends CrudRepository<Rating, Long> {
	@Query(value = "SELECT rate FROM rating WHERE id_user=:userId AND id_post=:postId", nativeQuery= true)
	public String takeRate(@Param("userId")Long userId , @Param("postId")Long postId);
}
