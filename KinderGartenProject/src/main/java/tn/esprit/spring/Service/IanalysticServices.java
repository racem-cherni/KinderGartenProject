package tn.esprit.spring.Service;

import java.util.Map;

import tn.esprit.spring.entities.KinderGarten;

public interface IanalysticServices {
public int calculerNbredeClasseParJardin(KinderGarten k);
public int calculerNbredeKidsParJardin(KinderGarten k);
public int calculerNbredeTeacherParJardin(KinderGarten k);
public Map<Long,Object> calculerNbreabonneParJardin(KinderGarten k);
public Map<Long,Object> estimerNbreDeInscription(KinderGarten k);
public Map<String,Object> estimerMonqueDeClasseParNbreparCapacite(KinderGarten k);
public Map<String,Object> estimerMonqueDeTeacher(KinderGarten k);
public Map<Long,Object> estimerkidshealth(KinderGarten k);
public Map<Long,Object> estimerScoreParJardin(KinderGarten k);

}
