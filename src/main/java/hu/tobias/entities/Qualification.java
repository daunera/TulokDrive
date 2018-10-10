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

import hu.tobias.entities.enums.QualificationType;
import hu.tobias.services.utils.Utils;

@Entity
@Table(name = "qualification")
public class Qualification implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "scout_id")
	private Scout scout;

	private Date date;
	private String place;
	private QualificationType type;
	private String course;

	public Qualification() {
	}

	public Qualification(Scout s) {
		this.scout = s;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Scout getScout() {
		return scout;
	}

	public void setScout(Scout scout) {
		this.scout = scout;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public QualificationType getType() {
		return type;
	}

	public void setType(QualificationType type) {
		this.type = type;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getFullInfo() {
		String text = type.getLabel();

		String subtext = "";
		if (!Utils.isEmpty(course))
			subtext += course + ", ";
		if (date != null)
			subtext += Utils.simpleDate(date) + ", ";
		if (!Utils.isEmpty(place))
			subtext += place + ", ";

		if (!Utils.isEmpty(subtext))
			text += " (" + subtext.substring(0, subtext.length() - 2) + ")";

		return text;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Qualification) {
			Qualification o = (Qualification) obj;
			return this.id.equals(o.id);
		}
		return false;
	}
}
