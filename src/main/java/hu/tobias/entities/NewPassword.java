package hu.tobias.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.mindrot.jbcrypt.BCrypt;

@Entity
@Table(name = "newpassword")
public class NewPassword implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Integer id;
	
	private String code;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private Leader user;
	
	private Date created;

	public NewPassword() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = BCrypt.hashpw(code, BCrypt.gensalt());
	}

	public Leader getUser() {
		return user;
	}

	public void setUser(Leader user) {
		this.user = user;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
   
}
