package hu.tobias.controllers;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import hu.tobias.entities.enums.Card;
import hu.tobias.entities.enums.ChallengeType;
import hu.tobias.entities.enums.FeeStatusType;
import hu.tobias.entities.enums.Gender;
import hu.tobias.entities.enums.PromiseType;
import hu.tobias.entities.enums.QualificationType;
import hu.tobias.entities.enums.Religion;
import hu.tobias.entities.enums.RentType;
import hu.tobias.entities.enums.Size;
import hu.tobias.entities.enums.Status;

@Named
@ApplicationScoped
public class Enums implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public Gender[] getGenders() {
		return Gender.values();
	}
	
	public Status[] getStatuses() {
		return Status.values();
	}
	
	public Religion[] getReligions() {
		return Religion.values();
	}
	
	public List<Religion> getReligionsList(){
		return (List<Religion>) Arrays.asList(Religion.values());
	}
	
	public Card[] getCards() {
		return Card.values();
	}
	
	public PromiseType[] getPromises() {
		return PromiseType.values();
	}
	
	public ChallengeType[] getChallenges() {
		return ChallengeType.values();
	}
	
	public QualificationType[] getQualifications() {
		return QualificationType.values();
	}
	
	public FeeStatusType[] getFeeStatuses() {
		return FeeStatusType.values();
	}
	
	public Size[] getSizes() {
		return Size.values();
	}
	
	public RentType[] getRentTypes() {
		return RentType.values();
	}

}
