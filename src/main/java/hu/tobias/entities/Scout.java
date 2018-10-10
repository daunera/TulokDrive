package hu.tobias.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import hu.tobias.entities.enums.Card;
import hu.tobias.entities.enums.ChallengeType;
import hu.tobias.entities.enums.PromiseType;
import hu.tobias.entities.enums.Status;
import hu.tobias.services.utils.Utils;

@Entity
@Table(name = "scout")
public class Scout implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Integer id;

	private String ecsetcode;
	private Status status = Status.ACTIVE;
	private Date joindate;
	private Date leavedate;
	private Card scoutcard = Card.NOTDEFINED;

	@OneToOne
	@JoinColumn(name = "person_id")
	private Person person;

	@OneToOne(mappedBy = "scout")
	private Leader leader;

	@OneToMany(mappedBy = "scout", fetch = FetchType.EAGER)
	private Set<Promise> promises;

	@OneToMany(mappedBy = "scout", fetch = FetchType.EAGER)
	private Set<Challenge> challenges;

	@OneToMany(mappedBy = "scout", fetch = FetchType.EAGER)
	private Set<Qualification> qualifications;

	@OneToMany(mappedBy = "scout", fetch = FetchType.EAGER)
	private Set<Fee> fees;

	@ManyToMany(mappedBy = "scouts", fetch = FetchType.EAGER)
	private Set<Patrol> patrols;
	
	@OneToMany(mappedBy = "scout", fetch = FetchType.EAGER)
	private Set<UniformRent> uniforms;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEcsetcode() {
		return ecsetcode;
	}

	public void setEcsetcode(String ecsetcode) {
		this.ecsetcode = ecsetcode;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getJoindate() {
		return joindate;
	}

	public void setJoindate(Date joindate) {
		this.joindate = joindate;
	}

	public Date getLeavedate() {
		return leavedate;
	}

	public void setLeavedate(Date leavedate) {
		this.leavedate = leavedate;
	}

	public Card getScoutcard() {
		return scoutcard;
	}

	public void setScoutcard(Card scoutcard) {
		this.scoutcard = scoutcard;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Leader getLeader() {
		return leader;
	}

	public void setLeader(Leader leader) {
		this.leader = leader;
	}

	public Set<Promise> getPromises() {
		return promises;
	}

	public void setPromises(Set<Promise> promises) {
		this.promises = promises;
	}

	public Set<Challenge> getChallenges() {
		return challenges;
	}

	public void setChallenges(Set<Challenge> challenges) {
		this.challenges = challenges;
	}

	public Set<Qualification> getQualifications() {
		return qualifications;
	}

	public void setQualifications(Set<Qualification> qualifications) {
		this.qualifications = qualifications;
	}

	public Set<Fee> getFees() {
		return fees;
	}

	public void setFees(Set<Fee> fees) {
		this.fees = fees;
	}

	public Set<Patrol> getPatrols() {
		return patrols;
	}

	public void setPatrols(Set<Patrol> patrols) {
		this.patrols = patrols;
	}

	public Set<UniformRent> getUniforms() {
		return uniforms;
	}

	public void setUniforms(Set<UniformRent> uniforms) {
		this.uniforms = uniforms;
	}

	public boolean isALeader() {
		if (leader == null)
			return false;
		return true;
	}

	public boolean hasPromises() {
		if (promises.size() > 0)
			return true;
		return false;
	}

	public boolean hasChallenges() {
		if (challenges.size() > 0)
			return true;
		return false;
	}

	public boolean hasQualifications() {
		if (qualifications.size() > 0)
			return true;
		return false;
	}

	public boolean hasFees() {
		if (fees.size() > 0)
			return true;
		return false;
	}

	public String getScoutUrl() {
		String rootUrl = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
		return rootUrl + "/scout/" + id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Scout) {
			Scout o = (Scout) obj;
			return this.id.equals(o.id);
		}
		return false;
	}

	public String getSimpleJoindate() {
		return Utils.simpleDate(joindate);
	}

	public String getSimpleLeavedate() {
		return Utils.simpleDate(leavedate);
	}

	public Promise getPromiseByType(String type) {
		PromiseType promiseType = PromiseType.valueOf(type);
		for (Promise p : promises) {
			if (p.getType().equals(promiseType))
				return p;
		}
		return null;
	}

	public Challenge getChallengeByType(String type) {
		ChallengeType challengeType = ChallengeType.valueOf(type);
		for (Challenge c : challenges) {
			if (c.getType().equals(challengeType))
				return c;
		}
		return null;
	}

	public Fee getFeeByYear(Integer year) {
		for (Fee f : fees) {
			if (f.getYear().equals(year))
				return f;
		}
		return null;
	}

}
