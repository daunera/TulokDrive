package hu.tobias.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "troop")
public class Troop implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Integer id;

	private String name;

	@OneToMany(mappedBy = "troop", fetch = FetchType.EAGER)
	private Set<Patrol> patrols = new HashSet<Patrol>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "troop_leader", joinColumns = @JoinColumn(name = "troop_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "leader_id", referencedColumnName = "id"))
	private Set<Leader> leaders = new HashSet<Leader>();

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

	public Set<Patrol> getPatrols() {
		return patrols;
	}

	public void setPatrols(Set<Patrol> patrols) {
		this.patrols = patrols;
	}

	public Set<Leader> getLeaders() {
		return leaders;
	}

	public void setLeaders(Set<Leader> leaders) {
		this.leaders = leaders;
	}

	public String getTroopUrl() {
		String rootUrl = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
		return rootUrl + "/troop/" + id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Troop) {
			Troop o = (Troop) obj;
			if (this.id == null)
				return false;
			return this.id.equals(o.id);
		}
		return false;
	}

	public int getLeaderNum() {
		return leaders.size();
	}

	public int getPatrolLeaderNum() {
		int count = 0;
		for (Patrol p : patrols) {
			count += p.getLeaderNum();
		}
		return count;
	}

	public int getActiveNum() {
		int count = 0;
		for (Patrol p : patrols) {
			count += p.getActiveNum();
		}
		return count;
	}

}
