package dto;

import java.sql.Timestamp;

public class JobParameterDTO {
	private int id;
	private String param_code;
	private String param_name;
	private String status;
	private int seq_no;
	private int create_by;
	private Timestamp create_time;
	private int update_by;
	private Timestamp update_time;
	private String param_value;
	private String param_name_en;
	private String discription;
	private String job_action;
	private String execution_prequency;
	private Timestamp execution_start_date;
	private Timestamp execution_end_date;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getParam_code() {
		return param_code;
	}
	public void setParam_code(String param_code) {
		this.param_code = param_code;
	}
	public String getParam_name() {
		return param_name;
	}
	public void setParam_name(String param_name) {
		this.param_name = param_name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getSeq_no() {
		return seq_no;
	}
	public void setSeq_no(int seq_no) {
		this.seq_no = seq_no;
	}
	public int getCreate_by() {
		return create_by;
	}
	public void setCreate_by(int create_by) {
		this.create_by = create_by;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	public int getUpdate_by() {
		return update_by;
	}
	public void setUpdate_by(int update_by) {
		this.update_by = update_by;
	}
	public Timestamp getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}
	public String getParam_value() {
		return param_value;
	}
	public void setParam_value(String param_value) {
		this.param_value = param_value;
	}
	public String getParam_name_en() {
		return param_name_en;
	}
	public void setParam_name_en(String param_name_en) {
		this.param_name_en = param_name_en;
	}
	public String getDiscription() {
		return discription;
	}
	public void setDiscription(String discription) {
		this.discription = discription;
	}
	public String getJob_action() {
		return job_action;
	}
	public void setJob_action(String job_action) {
		this.job_action = job_action;
	}
	public String getExecution_prequency() {
		return execution_prequency;
	}
	public void setExecution_prequency(String execution_prequency) {
		this.execution_prequency = execution_prequency;
	}
	public Timestamp getExecution_start_date() {
		return execution_start_date;
	}
	public void setExecution_start_date(Timestamp execution_start_date) {
		this.execution_start_date = execution_start_date;
	}
	public Timestamp getExecution_end_date() {
		return execution_end_date;
	}
	public void setExecution_end_date(Timestamp execution_end_date) {
		this.execution_end_date = execution_end_date;
	}
	
}
