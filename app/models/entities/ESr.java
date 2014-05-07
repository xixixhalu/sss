package models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity 
@Table(name = "simple_requirement")
public class ESr extends Model{
	
	@Id
	@Column(name="sr_id_pk")
	private Integer id;
	
	@Column(name="sr_title")
	private String title;

	@Column(name="sr_required_num")
	private Integer required_num;
	
	@Column(name="sr_cg_ids")
	private Integer cg_id;

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

	public Integer getRequired_num() {
		return required_num;
	}

	public void setRequired_num(Integer required_num) {
		this.required_num = required_num;
	}

	public Integer getCg_id() {
		return cg_id;
	}

	public void setCg_id(Integer cg_id) {
		this.cg_id = cg_id;
	}
}

