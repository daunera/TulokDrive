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
		return getPatrolLeader().size();
	}

	public int getAllLeaderNum() {
		return getAllLeader().size();
	}

	public int getActiveNum() {
		int count = 0;
		for (Patrol p : patrols) {
			count += p.getActiveNum();
		}
		return count;
	}

	public Set<Scout> getScouts() {
		Set<Scout> result = new HashSet<Scout>();
		for (Patrol p : patrols) {
			result.addAll(p.getScouts());
		}
		return result;
	}

	public Set<Leader> getPatrolLeader() {
		Set<Leader> result = new HashSet<Leader>();
		for (Patrol p : patrols) {
			for (Leader l : p.getLeaders()) {
				boolean addFlag = true;
				for (Leader r : result) {
					if (r.equals(l))
						addFlag = false;
				}
				if (addFlag)
					result.add(l);
			}
		}
		return result;
	}

	public Set<Leader> getAllLeader() {
		Set<Leader> result = getPatrolLeader();
		for (Leader l : leaders) {
			boolean addFlag = true;
			for (Leader r : result) {
				if (r.equals(l))
					addFlag = false;
			}
			if (addFlag)
				result.add(l);
		}
		return result;
	}

}