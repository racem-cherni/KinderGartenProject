package tn.esprit.spring.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Child;
import tn.esprit.spring.entities.Classe;
import tn.esprit.spring.entities.Competence;
import tn.esprit.spring.entities.KinderGarten;
import tn.esprit.spring.entities.Teacher;
import tn.esprit.spring.repository.ChildRepository;
import tn.esprit.spring.repository.ClasseRepository;
import tn.esprit.spring.repository.CompetenceRepository;
import tn.esprit.spring.repository.TeacherRepository;
@Service
public class TeacherServices {
@Autowired
private TeacherRepository teacherRepository;
@Autowired
private ClasseRepository classeRepository;
@Autowired
private  CompetenceRepository competenceRepository;


@Autowired
private ChildRepository childRepository;


public List<Teacher> showteaches(KinderGarten k){
	return teacherRepository.findByKinder(k);
}




public Teacher saveTeacher(Teacher t)
{
	return teacherRepository.save(t);
}
public void delateTeacher(Teacher t)
{Teacher  te=teacherRepository.getOne(t.getId());

	 teacherRepository.delete(te);
}

public Teacher updateTeacher(Teacher t)
{
	
	Teacher  te=teacherRepository.getOne(t.getId());
	
	te.setAge(t.getAge());
	te.setNom(t.getNom());
	te.setNumtel(t.getNumtel());
	te.setEmail(t.getEmail());
	te.setImage(t.getImage());
	
	return teacherRepository.save(te);
}



public Teacher affecttacherToClasse(Teacher t, Classe cl){
	Teacher  te=teacherRepository.getOne(t.getId());
if(te.getClasse()!=null)
	throw new RuntimeException("this teacher has classe");

	te.setClasse(cl);
	System.out.println("cl :"+cl.getNom());

	System.out.println("teachername :"+te.getClasse().getNom());
	return teacherRepository.save(te);
	}
	


public List<Child> listkidClasse(Teacher t){
	Classe cl=classeRepository.findByTeacher(t);
	
	List<Child> lc=(List<Child>) cl.getKid();
	
	return lc;	
	
}

public List<Teacher> getTeacherTheMostAdecttied(KinderGarten k,Classe cl){
	int classeScore=(ClasseScore(cl));
	List<Teacher> lt=teacherRepository.findByKinder(k);
	
	lt=lt.stream().filter(t->ScoreForTeacher(t, cl)>=classeScore ).sorted((t1,t2)->ScoreForTeacher(t2, cl)-ScoreForTeacher(t1, cl)).collect(Collectors.toList());
	lt.forEach(e->System.out.println("teachername :"+e.getNom()));

	return lt;
	
	
}

public int ClasseScore(Classe cl){
int score=0;	
List<Child> chl=childRepository.findchildByClasse(cl);

List<String> childHealth=chl.stream().map(c->c.getHealth()).collect(Collectors.toList());


 if(childHealth.contains("GOOD"))
		score=score+1;
	if(childHealth.contains("MEDICALE_PROBLEM"))
		score=score+10;
	if(childHealth.contains("AUTISME"))
		score=score+30;
	
if(cl.getAge()<=2)	
	score=score+50;
	
System.out.println("scoreclasse :"+score);
return score;
}


public int ScoreForTeacher(Teacher t,Classe cl){
	int score=0;
	Set<Competence> setCom=new HashSet<>();
	for(Competence c :t.getCompetences())
	{
		setCom.add(c);
		}
	
	
	for(Competence c : setCom){
		score=(int) (score+c.getNote());
	}
	System.out.println("teacher nom :"+t.getNom()+" scoreteacher :"+score);
	return score;
}

public Teacher saveTeacherCompetence(Competence c,Long idt){
	Teacher t=teacherRepository.getOne(idt);
	System.out.println("scoreteacher :"+t.getNom());
	boolean test=competenceRepository.existsById(c.getId());
	if(!test){
	c.setNote((float)1);
	competenceRepository.save(c);
	Competence cf=competenceRepository.findByNom(c.getCompetenceName());
	if(t.getCompetences().contains(cf))
		throw new RuntimeException("this competence is all ready exist");
	
	
	t.getCompetences().add(cf);
	return teacherRepository.save(t);
	}
	
	Competence cm =competenceRepository.getOne(c.getId());
	if(t.getCompetences().contains(cm))
		throw new RuntimeException("this competence is all ready exist");
	t.getCompetences().add(cm);
	return teacherRepository.save(t);
	
}

public List<Competence> showCompetencesMy() {
	
	return competenceRepository.findAll();
}


public List<Competence> showCompetences() {
	
	return competenceRepository.findAll();
}








}
