package models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.ebean.Model;


@SuppressWarnings("serial")
@Entity 
@Table(name = "course")
public class ECourse extends Model{
	
	@Id
	@Column(name="course_id_pk")
	private Integer id;
	
	@Column(name="course_prefix")
	private String prefix;
	
	@Column(name="course_number")
	private Integer number;
	
	@Column(name="course_title")
	private String title;
	
	@Column(name="course_credit")
	private Integer credit;
	
	@Column(name="course_prerequisite_ids")
	private String prerequisite_ids;
	
	@Column(name="course_corequisite_ids")
	private String corequisite_ids;
	
	@Column(name="course_oncampus")
	private String oncampus;
	
	@Column(name="course_online")
	private String online;

	/** Getter and Setter*/
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getCredit() {
		return credit;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	public String getPrerequisite_ids() {
		return prerequisite_ids;
	}

	public void setPrerequisite_ids(String prerequisite_ids) {
		this.prerequisite_ids = prerequisite_ids;
	}

	public String getCorequisite_ids() {
		return corequisite_ids;
	}

	public void setCorequisite_ids(String corequisite_ids) {
		this.corequisite_ids = corequisite_ids;
	}

	public String getOncampus() {
		return oncampus;
	}

	public void setOncampus(String oncampus) {
		this.oncampus = oncampus;
	}

	public String getOnline() {
		return online;
	}

	public void setOnline(String online) {
		this.online = online;
	}
	
}
