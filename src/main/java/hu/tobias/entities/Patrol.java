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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import hu.tobias.entities.enums.Gender;
import hu.tobias.services.utils.Utils;

@Entity
@Table(name = "patrol")
public class Patrol implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Integer id;

	private String name;
	private Date birthdate = new Date();
	private Gender gender = Gender.NOTDEFINED;;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "patrol_scout", joinColumns = @JoinColumn(name = "patrol_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "scout_id", referencedColumnName = "id"))
	private Set<Scout> scouts;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "patrol_leader", joinColumns = @JoinColumn(name = "patrol_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "leader_id", referencedColumnName = "id"))
	private Set<Leader> leaders;
	
	@ManyToMany(mappedBy = "patrols", fetch = FetchType.EAGER)
	private Set<Troop> troops;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Set<Scout> getScouts() {
		return scouts;
	}

	public void setScouts(Set<Scout> scouts) {
		this.scouts = scouts;
	}

	public Set<Leader> getLeaders() {
		return leaders;
	}

	public void setLeaders(Set<Leader> leaders) {
		this.leaders = leaders;
	}

	public Set<Troop> getTroops() {
		return troops;
	}

	public void setTroops(Set<Troop> troops) {
		this.troops = troops;
	}

	public String getPatrolUrl() {
		String rootUrl = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
		return rootUrl + "/patrol/" + id;
	}

	public String getSimpleBirthdate() {
		return Utils.simpleDate(birthdate);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Patrol) {
			Patrol o = (Patrol) obj;
			return this.id.equals(o.id);
		}
		return false;
	}

}
