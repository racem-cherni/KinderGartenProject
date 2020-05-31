package tn.esprit.spring.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class RateUsersPK implements Serializable {

	private static final long serialVersionUID = 5377539445871317492L;

	private int offerid;

	private Long userid;

	public Long getKindergartenid() {
		return userid;
	}

	public void setKindergartenid(Long kindergartenid) {
		this.userid = kindergartenid;
	}

	public int getOfferid() {
		return offerid;
	}

	public void setOfferid(int offerid) {
		this.offerid = offerid;
	}

	public RateUsersPK(int offerid, Long kindergartenid) {
		super();
		this.offerid = offerid;
		this.userid = kindergartenid;
	}

	public RateUsersPK() {
		super();
	}

	
	
}
