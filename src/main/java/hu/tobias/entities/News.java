package hu.tobias.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import hu.tobias.services.utils.Utils;

@Entity
@Table(name = "news")
public class News implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Integer id;

	private Date created = new Date();
	private Date expire = new Date();

	private String text;

	@ManyToOne
	@JoinColumn(name = "submitter_id")
	private Leader submitter;

	public News() {
		long weekInMilisec = 604800000;
		this.expire.setTime(expire.getTime() + weekInMilisec);
	}

	public News(Leader l) {
		long weekInMilisec = 604800000;
		this.expire.setTime(expire.getTime() + weekInMilisec);
		this.submitter = l;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getExpire() {
		return expire;
	}

	public void setExpire(Date expire) {
		this.expire = expire;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Leader getSubmitter() {
		return submitter;
	}

	public void setSubmitter(Leader submitter) {
		this.submitter = submitter;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof News) {
			News o = (News) obj;
			return this.id.equals(o.id);
		}
		return false;
	}

	public String getSimpleCreated() {
		return Utils.simpleDate(created);
	}

	public String getSimpleExpire() {
		return Utils.simpleDate(expire);
	}

}
