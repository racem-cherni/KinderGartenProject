package tn.esprit.spring.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class AdvertissementPK implements Serializable {

	
	private static final long serialVersionUID = 5377539445871317492L;
	
	private Long idsourceUser;
		
	
	
	private Long idtargetUser;
	
	
	
}
