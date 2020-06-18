package tn.esprit.spring.Service;

import javax.servlet.http.HttpServletRequest;

import tn.esprit.spring.entities.Rating;
import tn.esprit.spring.entities.Rdv_dispo;

public interface IRdv_dispoService {
	public int ajouterRdv_dispo(Rdv_dispo rat,HttpServletRequest request);
}
