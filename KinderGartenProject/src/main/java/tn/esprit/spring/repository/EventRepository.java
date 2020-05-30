package tn.esprit.spring.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Category_event;
import tn.esprit.spring.entities.Etat_event;
import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Type_Event;


@Repository
public interface EventRepository extends CrudRepository<Event,Long> {
	
	@Query("SELECT e.title , e.category, e.date_event FROM Event e where e.kindereventmaker =:kindergarten order by date_event")
    public List<String> findeventsbykindergarten(@Param("kindergarten")KinderGarten kindergarten);

	@Query("SELECT e FROM Event e where e.id=:idevent and e.kindereventmaker =:kindergarten ")
    public Event detaileventbykindergarten(@Param("kindergarten")KinderGarten kindergarten,@Param("idevent") Long idevent);
	
	@Query("SELECT e.title , e.category, e.date_event FROM Event e where e.category=:categorie and e.kindereventmaker =:kindergarten ")
    public List<String> listeventbycategory(@Param("categorie") Category_event categorie,@Param("kindergarten")KinderGarten kindergarten);
	
	@Query("SELECT e FROM Event e ")
    public List<Event> listeventsbykindergarten();
	
	@Query("SELECT e from Event e where  e.kindereventmaker =:kindergarten and e.date_event = CURRENT_DATE()")
	 public Event getEventPourToday(@Param("kindergarten")KinderGarten kindergarten);
	
	@Query("SELECT e.title , e.category, e.date_event FROM Event e ")
    public List<String> findallevents();

	///////////////////////jsf///////////////////////////////////
	
	@Query("SELECT e FROM Event e where e.kindereventmaker =:kindergarten and e.date_event = CURRENT_DATE()  ")
    public List<Event> eventstodayjsf(@Param("kindergarten")KinderGarten kindergarten);
	
	@Query("SELECT e FROM Event e where e.kindereventmaker =:kindergarten and e.date_event > CURRENT_DATE()  ")
    public List<Event> upcomingeventsjsf(@Param("kindergarten")KinderGarten kindergarten);
	
	@Query("SELECT e FROM Event e where e.kindereventmaker =:kindergarten and e.date_event =:date ")
    public Event findeventsbydate(@Param("kindergarten")KinderGarten kindergarten,@Param("date")Date date);
	
	@Query("SELECT e FROM Event e where e.kindereventmaker =:kindergarten and e.date_event = CURRENT_DATE()  ")
    public Event eventtodayjsf(@Param("kindergarten")KinderGarten kindergarten);
	
	@Query("SELECT e FROM Event e where e.kindereventmaker =:kindergarten and e.type_event =:type and e.etat_event=:etat and e.category=:category")
    public List<Event> filtredeventsjsf(@Param("kindergarten")KinderGarten kindergarten ,@Param("type")Type_Event type,@Param("etat")Etat_event etat
    		,@Param("category") Category_event categorie  );
	@Query("Select e from Event e where e.kindereventmaker =:kindergarten and e.category=:category and e.id<>:idevent ORDER BY e.date_event desc  ")
	public List<Event>  eventassociesjsf(@Param("kindergarten")KinderGarten kindergarten,@Param("category") Category_event categorie,@Param("idevent") Long id);
    



}
