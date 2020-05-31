package tn.esprit.spring.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
@Table(name="EVALUATION_EVENT")
public class Evaluation_Event implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private Evaluation_EventPk evaluationpk ;
	
	@Column(name="Evaluation_value")
	private int evaluation_value;
	
	//@Temporal (TemporalType.DATE)
	@Column(name="Evaluation_Date")
	private Date Evaluation_date;

	@ManyToOne
	@JoinColumn(name = "idEvent", referencedColumnName = "Event_id", insertable=false, updatable=false)
	private Event event_evaluation ;
	
	@ManyToOne
	@JoinColumn(name = "idparentt", referencedColumnName = "id", insertable=false, updatable=false)
    private Parent parent_evaluation ;

	public Evaluation_EventPk getEvaluationpk() {
		return evaluationpk;
	}

	public void setEvaluationpk(Evaluation_EventPk evaluationpk) {
		this.evaluationpk = evaluationpk;
	}

	public int getEvaluation_value() {
		return evaluation_value;
	}

	public void setEvaluation_value(int evaluation_value) {
		this.evaluation_value = evaluation_value;
	}

	public Event getEvent_evaluation() {
		return event_evaluation;
	}

	public void setEvent_evaluation(Event event_evaluation) {
		this.event_evaluation = event_evaluation;
	}

	public Date getEvaluation_date() {
		return Evaluation_date;
	}

	public void setEvaluation_date(Date evaluation_date) {
		Evaluation_date = evaluation_date;
	}

	public Parent getParent_evaluation() {
		return parent_evaluation;
	}

	public void setParent_evaluation(Parent parent_evaluation) {
		this.parent_evaluation = parent_evaluation;
	}

	
	

	
}
