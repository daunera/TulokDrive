package hu.tobias.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import hu.tobias.entities.enums.Gender;
import hu.tobias.entities.enums.Status;
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
	private Integer startclass = 2;
	private Gender gender = Gender.NOTDEFINED;;

	@OneToMany(mappedBy = "patrol", fetch = FetchType.EAGER)
	private Set<Scout> scouts = new HashSet<Scout>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "patrol_leader", joinColumns = @JoinColumn(name = "patrol_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "leader_id", referencedColumnName = "id"))
	private Set<Leader> leaders = new HashSet<Leader>();

	@ManyToOne
	@JoinColumn(name = "troop_id")
	private Troop troop;

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

	public Integer getStartclass() {
		return startclass;
	}

	public void setStartclass(Integer startclass) {
		this.startclass = startclass;
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

	public Troop getTroop() {
		return troop;
	}

	public void setTroop(Troop troop) {
		this.troop = troop;
	}

	public String getPatrolUrl() {
		String rootUrl = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
		return rootUrl + "/patrol/" + id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Patrol) {
			Patrol o = (Patrol) obj;
			if (this.id == null)
				return false;
			return this.id.equals(o.id);
		}
		return false;
	}

	public int getLeaderNum() {
		return leaders.size();
	}

	public int getActiveNum() {
		int count = 0;
		for (Scout s : scouts) {
			if (s.getStatus().equals(Status.ACTIVE))
				count++;
		}
		return count;
	}

	public int getActualClass() {
		if (birthdate == null)
			return 0;
		else {
			Calendar c = Calendar.getInstance();
			c.setTime(birthdate);
			if(c.get(Calendar.MONTH) < Calendar.SEPTEMBER) {
				c.add(Calendar.YEAR, -1);
			}
			c.set(Calendar.MONTH, Calendar.SEPTEMBER);
			c.set(Calendar.DATE, 1);
			
			return Utils.ageInYear(c.getTime()) + startclass;
		}
	}

}
