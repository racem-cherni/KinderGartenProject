package tn.esprit.spring.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Category;
import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Vote;
import tn.esprit.spring.repository.EventRepository;
import tn.esprit.spring.repository.KinderGartenRepository;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.repository.VoteRepository;
@Service
public class VoteServecies {
@Autowired
private VoteRepository voteRepository;
@Autowired
private KinderGartenRepository KinderGartenRepository;
@Autowired
private EventRepository eventRepository;
@Autowired
private UserRepository userRepository;


public Vote saveVote(Vote v){
	return voteRepository.save(v);
}

public void deleteVote(Vote v){
	voteRepository.delete(v);
}
public Vote updateVote(Vote v){
	return voteRepository.save(v);
}

public void calculScore(KinderGarten kinder,int n){
	
	
	int note=0;
		if(	voteRepository.getSumNote(kinder)!=null)
			note=voteRepository.getSumNote(kinder);
	int nbre=0;
	if(voteRepository.getNum(kinder)!=null)
		nbre=voteRepository.getNum(kinder);
	kinder.getUserapp().setScore((note+n)/(nbre+1));
	KinderGartenRepository.save(kinder);
	
	
	
}
//&& ((today.getTime()-e.getEnd_date().getTime())/total)<=30 && ((today.getTime()-e.getEnd_date().getTime())/total)>0

public void updateScoreEvent(KinderGarten k){
	
	List<Event> l=eventRepository.findAll();
	Date today= new Date();
	int score=0;
	int i=1;
	Long total=(long) (1000*60*60*24);
	int fscore=0;
	l.stream().forEach(e->{
		
		System.out.println("test 1");
		System.out.println(e.getKinderGarten().getKinderGartenName());
	});
	l=(List<Event>) l.stream().filter(e->e.getKinderGarten().getId()==k.getId()&&((today.getTime()-e.getEnd_date().getTime())/total)<=30 && ((today.getTime()-e.getEnd_date().getTime())/total)>0 )
			.collect(Collectors.toList());
	
	l.stream().forEach(e->{
		System.out.println("test 2");
		System.out.println(e.getKinderGarten().getKinderGartenName());
	});
	
	for (Event e : l) {
		i++;
		if(e.getCategory().equals(Category.Birth))
			score++;
		if(e.getCategory().equals(Category.Party))
			score++;
		if(e.getCategory().equals(Category.End_of_year))
			score++;
		if(e.getCategory().equals(Category.Film))
			score=score+2;
		if(e.getCategory().equals(Category.cammping))
			score=score+3;
		if(e.getCategory().equals(Category.donation))
			score=score+5;
		if(e.getCategory().equals(Category.Activite_Sociale))
			score=score+6;
		
		
	}
	
	
	fscore=score/i;
	userRepository.updateUserScore(fscore/2,k);
	
	
}



}
