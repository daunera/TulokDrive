package hu.tobias.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.mindrot.jbcrypt.BCrypt;

import hu.tobias.services.utils.Utils;

@Entity
@Table(name = "leader")
public class Leader implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Integer id;

	@Size(min = 1)
	private String password;
	@Size(min = 1, max = 50)
	private String username;

	@OneToOne
	@JoinColumn(name = "scout_id")
	private Scout scout;

	@OneToOne(mappedBy = "leader")
	private Userroles role;

	private String leadername;
	private String food;
	private Date becomeleader = new Date();
	private Date leaderpromise;
	private String keynum;
	private Date keydate;

	private Date lastlogin = new Date();

	@ManyToMany(mappedBy = "leaders", fetch = FetchType.EAGER)
	private Set<Patrol> patrols;
	
	@ManyToMany(mappedBy = "leaders", fetch = FetchType.EAGER)
	private Set<Troop> troops;

	private Boolean god = false;
	private Boolean uniformer = false;

	private String salt;

	public Leader() {
		this.salt = BCrypt.gensalt();
	}

	public Leader(Integer id) {
		this.id = id;
		this.salt = BCrypt.gensalt();
	}

	public Leader(Integer id, String username, String password) {
		this.id = id;
		this.username = username;
		this.salt = BCrypt.gensalt();
		this.password = BCrypt.hashpw(password, salt);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = BCrypt.hashpw(password, getSalt());
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Scout getScout() {
		return scout;
	}

	public void setScout(Scout scout) {
		this.scout = scout;
	}

	public Userroles getRole() {
		return role;
	}

	public void setRole(Userroles role) {
		this.role = role;
	}

	public String getLeadername() {
		return leadername;
	}

	public void setLeadername(String leadername) {
		this.leadername = leadername;
	}

	public String getFood() {
		return food;
	}

	public void setFood(String food) {
		this.food = food;
	}

	public Date getBecomeleader() {
		return becomeleader;
	}

	public void setBecomeleader(Date becomeleader) {
		this.becomeleader = becomeleader;
	}

	public Date getLeaderpromise() {
		return leaderpromise;
	}

	public void setLeaderpromise(Date leaderpromise) {
		this.leaderpromise = leaderpromise;
	}

	public String getKeynum() {
		return keynum;
	}

	public void setKeynum(String keynum) {
		this.keynum = keynum;
	}

	public Date getKeydate() {
		return keydate;
	}

	public void setKeydate(Date keydate) {
		this.keydate = keydate;
	}

	public Date getLastlogin() {
		return lastlogin;
	}

	public void setLastlogin(Date lastlogin) {
		this.lastlogin = lastlogin;
	}

	public Set<Patrol> getPatrols() {
		return patrols;
	}

	public void setPatrols(Set<Patrol> patrols) {
		this.patrols = patrols;
	}

	public Set<Troop> getTroops() {
		return troops;
	}

	public void setTroops(Set<Troop> troops) {
		this.troops = troops;
	}

	public Boolean getGod() {
		return god;
	}

	public void setGod(Boolean god) {
		this.god = god;
	}

	public Boolean getUniformer() {
		return uniformer;
	}

	public void setUniformer(Boolean uniformer) {
		this.uniformer = uniformer;
	}

	public String getSimpleBecomedate() {
		return Utils.simpleDate(becomeleader);
	}

	public String getSimplePromisedate() {
		return Utils.simpleDate(leaderpromise);
	}

	public String getSimpleKeydate() {
		return Utils.simpleDate(keydate);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Leader) {
			Leader o = (Leader) obj;
			return this.id.equals(o.id);
		}
		return false;
	}

	public String getExtraInfo() {
		String permissions = "";
		if (getGod())
			permissions += "adminisztrátor, ";
		if (getUniformer())
			permissions += "cserkészingfelelős, ";
		if (Utils.isEmpty(permissions))
			return null;
		else
			return permissions.substring(0, permissions.length() - 2);
	}

	public String getEmail() {
		return this.scout.getPerson().getEmail();
	}

	public String getFullName() {
		return this.scout.getPerson().getFullName();
	}

	public String getPersonalName() {
		return this.scout.getPerson().getPersonalName();
	}

	public Boolean isAGod() {
		return god;
	}

	public Boolean isAUniformer() {
		if (isAGod())
			return true;
		else
			return uniformer;
	}

	public String getSalt() {
		if (Utils.isEmpty(this.salt)) {
			this.salt = BCrypt.gensalt();
		}
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

}
