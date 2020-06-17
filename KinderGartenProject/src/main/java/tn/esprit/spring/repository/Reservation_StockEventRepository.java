package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.Reservation_stock_event;

@Repository
public interface Reservation_StockEventRepository extends CrudRepository<Reservation_stock_event,Long> {
	@Query("Select r from Reservation_stock_event r where r.event=:event ")
	public List<Reservation_stock_event> listreservationsevent (@Param("event")Event event);
	@Query("Select count (*) from  Reservation_stock_event r where r.event=:event  ")
	public int nbrreservationevent (@Param("event")Event event);
	
	@Modifying
    @Transactional
    @Query("DELETE FROM  Reservation_stock_event r where r.event.id=:idevent and r.stockevent.id=:idstock") 

    public void deleteReservation(@Param("idevent")Long idevent ,@Param("idstock")Long idstock );
	
	@Query ("Select r from Reservation_stock_event r where r.event.id=:idevent and r.stockevent.id=:idstock ")
	public Reservation_stock_event getreservation(@Param("idevent")Long idevent ,@Param("idstock")Long idstock);
	
	@Query("Select SUM (r.price) from Reservation_stock_event r where r.event.id=:idevent ")
	public Double prixtotalereservation (@Param("idevent")Long idevent);

	

}
