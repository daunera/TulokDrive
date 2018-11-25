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

import hu.tobias.entities.enums.ChallengeType;
import hu.tobias.services.utils.Utils;

@Entity
@Table(name = "challenge")
public class Challenge implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "scout_id")
	private Scout scout;

	private Date date;
	private ChallengeType type;
	private String place;

	public Challenge() {
	}

	public Challenge(Scout s) {
		this.scout = s;
	}

	public Challenge(Scout s, ChallengeType c) {
		this.scout = s;
		this.type = c;
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

	public ChallengeType getType() {
		return type;
	}

	public void setType(ChallengeType type) {
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
		if( type != null) {
			text = type.getLabel();
		} else
			text = "Próba";

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
		return "Megszerezte";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Challenge) {
			Challenge o = (Challenge) obj;
			if (this.id == null)
				return false;
			return this.id.equals(o.id);
		}
		return false;
	}

}
