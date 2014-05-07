package models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity 
@Table(name = "requirement")
public class ERequirement extends Model{
	
	@Id
	@Column(name="req_id_pk")
	private Integer id;
	
	@Column(name="req_title")
	private String title;

	@Column(name="req_sr_ids")
	private String sr_ids;

	/** Getter and Setter*/
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSr_ids() {
		return sr_ids;
	}

	public void setSr_ids(String sr_ids) {
		this.sr_ids = sr_ids;
	}
}

