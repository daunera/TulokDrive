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

import hu.tobias.entities.enums.PromiseType;
import hu.tobias.services.utils.Utils;

@Entity
@Table(name = "promise")
public class Promise implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "scout_id")
	private Scout scout;

	private Date date;
	private PromiseType type;
	private String place;

	public Promise() {
	}

	public Promise(Scout s) {
		this.scout = s;
	}

	public Promise(Scout s, PromiseType t) {
		this.scout = s;
		this.type = t;
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

	public PromiseType getType() {
		return type;
	}

	public void setType(PromiseType type) {
		this.type = type;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getFullInfo() {
		String text = "";
		if (type != null)
				text = type.getLabel();
		else
			text = "Fogadalom";

		String subtext = "";
		if (date != null)
			subtext += Utils.simpleDate(date) + ", ";
		if (!Utils.isEmpty(place))
			subtext += place + ", ";

		if (!Utils.isEmpty(subtext))
			text += " (" + subtext.substring(0, subtext.length() - 2) + ")";

		return text;
	}

	public String getDetails() {
		String text = "";
		if (date != null)
			text += Utils.simpleDate(date) + ", ";
		if (!Utils.isEmpty(place))
			text += place + ", ";

		if (!Utils.isEmpty(text))
			return text.substring(0, text.length() - 2);
		return "Letette";

	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Promise) {
			Promise o = (Promise) obj;
			if (this.id == null)
				return false;
			return this.id.equals(o.id);
		}
		return false;
	}
}
