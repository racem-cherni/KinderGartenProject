package tn.esprit.spring.Service;

import java.util.Map;

import tn.esprit.spring.entities.KinderGarten;

public interface IanalysticServices {
public Map<String,Object> calculerNbredeClasseParJardin(KinderGarten k);
public Map<String,Object> calculerNbredeKidsParJardin(KinderGarten k);
public Map<String,Object> calculerNbredeTeacherParJardin(KinderGarten k);
public Map<Long,Object> calculerNbreabonneParJardin(KinderGarten k);
public Map<Long,Object> estimerNbreDeInscription(KinderGarten k);
public Map<Long,Object> estimerMonqueDeClasseParNbreparCapacite(KinderGarten k);
public Map<Long,Object> estimerMonqueDeTeacher(KinderGarten k);
public Map<Long,Object> estimerkidshealth(KinderGarten k);
public Map<Long,Object> estimerScoreParJardin(KinderGarten k);

}
