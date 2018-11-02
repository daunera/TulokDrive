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

import hu.tobias.entities.enums.FeeStatusType;
import hu.tobias.services.utils.Utils;

@Entity
@Table(name = "fee")
public class Fee implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "scout_id")
	private Scout scout;

	private Integer year;
	private Date completeDate = new Date();
	private Integer amount;
	private Integer support = 0;
	private FeeStatusType status = FeeStatusType.NO;
	private String contributor;
	private String other;

	public Fee() {

	}

	public Fee(Scout s, Integer y) {
		this.scout = s;
		this.year = y;
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

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Date getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getSupport() {
		return support;
	}

	public void setSupport(Integer support) {
		this.support = support;
	}

	public FeeStatusType getStatus() {
		return status;
	}

	public void setStatus(FeeStatusType status) {
		this.status = status;
	}

	public String getContributor() {
		return contributor;
	}

	public void setContributor(String contributor) {
		this.contributor = contributor;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getFullInfo() {
		return year.toString() + " - " + status.getLabel() + " (" + Utils.simpleDate(completeDate) + ")";
	}

	public String getOtherInfo() {
		String text = "";

		if (!Utils.isEmpty(contributor))
			text += "befizető: " + contributor + ", ";
		if (!Utils.isEmpty(other))
			text += other + ", ";

		if (Utils.isEmpty(text))
			return null;
		return text.substring(0, text.length() - 2);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Fee) {
			Fee o = (Fee) obj;
			return this.id.equals(o.id);
		}
		return false;
	}

}
