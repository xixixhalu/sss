package models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity 
@Table(name = "degree")
public class EDegree extends Model{
	
	@Id
	@Column(name="degree_id_pk")
	private Integer id;
	
	@Column(name="degree_title")
	private String title;

	@Column(name="degree_req_ids")
	private String req_ids;

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

	public String getReq_ids() {
		return req_ids;
	}

	public void setReq_ids(String req_ids) {
		this.req_ids = req_ids;
	}
}

