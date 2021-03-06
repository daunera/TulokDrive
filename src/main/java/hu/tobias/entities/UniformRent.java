package hu.tobias.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import hu.tobias.entities.enums.RentType;
import hu.tobias.entities.enums.Size;
import hu.tobias.entities.enums.Status;
import hu.tobias.services.utils.Utils;

@Entity
@Table(name = "uniform")
public class UniformRent implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NotNull
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "scout_id")
	private Scout scout;

	private Date begindate;
	private Date returndate;
	private Integer price;
	private Size uniformsize;
	private RentType renttype;
	private Boolean returned;
	private String other;

	public UniformRent() {
		this.begindate = new Date();
		this.renttype = RentType.NOW;
		this.uniformsize = Size.NOTDEFINED;
	}

	public UniformRent(Scout s) {
		this.scout = s;
		this.begindate = new Date();
		this.renttype = RentType.NOW;
		this.uniformsize = Size.NOTDEFINED;
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

	public Date getBegindate() {
		return begindate;
	}

	public void setBegindate(Date begindate) {
		this.begindate = begindate;
	}

	public Date getReturndate() {
		return returndate;
	}

	public void setReturndate(Date returndate) {
		this.returndate = returndate;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Size getUniformsize() {
		return uniformsize;
	}

	public void setUniformsize(Size uniformsize) {
		this.uniformsize = uniformsize;
	}

	public RentType getRenttype() {
		return renttype;
	}

	public void setRenttype(RentType renttype) {
		this.renttype = renttype;
	}

	public Boolean getReturned() {
		return returned;
	}

	public void setReturned(Boolean returned) {
		this.returned = returned;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public Integer getActualReturnPrice() {
		if (renttype.equals(RentType.VERYOLD))
			return 0;
		if (begindate == null)
			return -1; // szomi ha nincs bérlésdátum :(
		else {
			int y = Utils.ageInYear(begindate);
			if (renttype.equals(RentType.OLD)) {
				int money = 6600 - (y + 1) * 1500;
				if (money < 0)
					money = 0;
				return money;
			} else if (renttype.equals(RentType.NOW)) {
				int money = 4000 - (y) * 1000;
				if (money < 0)
					money = 0;
				return money;
			} else
				return 0;
		}
	}

	public String getPlannedReturnDate() {
		Date result = null;

		if (renttype.equals(RentType.VERYOLD))
			result = Utils.now();

		else if (renttype.equals(RentType.OLD)) {
			if (scout.getPromiseByType("CS") != null)
				result = Utils.now();
			else if (scout.getStatus().equals(Status.QUITTED))
				result = Utils.now();
			else {
				if (scout.getPatrol().getActualClass() > 5)
					result = Utils.now();
				else if (scout.getPatrol().getActualClass() != 0) {
					Calendar c = Calendar.getInstance();
					c.setTime(scout.getPatrol().getBirthdate());
					c.set(Calendar.MONTH, Calendar.SEPTEMBER);
					c.set(Calendar.DATE, 1);
					c.add(Calendar.YEAR, 6 - scout.getPatrol().getStartclass());
					result = c.getTime();
				}
				if (begindate != null) {
					Calendar c = Calendar.getInstance();
					c.setTime(begindate);
					c.add(Calendar.YEAR, 3);
					if (result != null && result.after(c.getTime())) {
						c.add(Calendar.DATE, 1);
						result = c.getTime();
					}
				}
			}

		} else if (renttype.equals(RentType.NOW)) {
			if (scout.getStatus().equals(Status.QUITTED))
				result = Utils.now();
			else {
				if (scout.getPatrol().getActualClass() > 5) {
					result = Utils.now();
				} else if (scout.getPatrol().getActualClass() != 0) {
					Calendar c = Calendar.getInstance();
					c.setTime(scout.getPatrol().getBirthdate());
					c.set(Calendar.MONTH, Calendar.SEPTEMBER);
					c.set(Calendar.DATE, 1);
					c.add(Calendar.YEAR, 6 - scout.getPatrol().getStartclass());
					result = c.getTime();
				}
			}

		} else
			result = Utils.now();

		Date dt = Utils.now();
		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DATE, 1);
		dt = c.getTime();

		if (result == null)
			return "PASSZ";
		else if (result.before(dt))
			return "MOST";
		else
			return Utils.simpleDate(result);
	}

}
