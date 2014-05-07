package models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.ebean.Model;

@SuppressWarnings("serial")
@Entity 
@Table(name = "course_group")
public class ECg extends Model{
	
	@Id
	@Column(name="cg_id_pk")
	private Integer id;
	
	@Column(name="cg_prefix")
	private String prefix;
	
	@Column(name="cg_title")
	private String title;

	@Column(name="cg_course_ids")
	private String course_ids;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCourse_ids() {
		return course_ids;
	}

	public void setCourse_ids(String course_ids) {
		this.course_ids = course_ids;
	}
}

