package tn.esprit.spring.Service;

import java.text.ParseException;
import java.util.List;

import tn.esprit.spring.entities.Rdv;
import tn.esprit.spring.entities.Rdv_reponse;

public interface IRdv_reponseService {
	

	public int repondreAuRdv(Rdv_reponse rdvR,int idrdv) throws ParseException, Exception;
	public void updatereponseAuRdv(Rdv_reponse rdvR,int idrdvR) throws Exception ;
	public List<Rdv_reponse> getReponseByRdv(Rdv rdv);


}
