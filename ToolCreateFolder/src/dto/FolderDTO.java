package dto;

import java.sql.Timestamp;

public class FolderDTO {
	private String folderId;
	private String folderName;
	private String folderClass;
	private Integer folderLevel;
	private String pFolderId;
	private String pFolderName;
	private String rootFolderId;
	private String rootFolderName;
	private String status;
	private String updateName;
	private String path;
	private Integer updateBy;
	private Timestamp updateTime;
	
	private Integer createBy;
	private String createName;
	private Timestamp createTime;
	
	private Integer idICom;
	private Integer parentIdICom;
	
	
	public String getRootFolderName() {
		return rootFolderName;
	}
	public void setRootFolderName(String rootFolderName) {
		this.rootFolderName = rootFolderName;
	}
	public String getpFolderName() {
		return pFolderName;
	}
	public void setpFolderName(String pFolderName) {
		this.pFolderName = pFolderName;
	}
	public String getFolderId() {
		return folderId;
	}
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public String getFolderClass() {
		return folderClass;
	}
	public void setFolderClass(String folderClass) {
		this.folderClass = folderClass;
	}
	public Integer getFolderLevel() {
		return folderLevel;
	}
	public void setFolderLevel(Integer folderLevel) {
		this.folderLevel = folderLevel;
	}
	public String getpFolderId() {
		return pFolderId;
	}
	public void setpFolderId(String pFolderId) {
		this.pFolderId = pFolderId;
	}
	public String getRootFolderId() {
		return rootFolderId;
	}
	public void setRootFolderId(String rootFolderId) {
		this.rootFolderId = rootFolderId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUpdateName() {
		return updateName;
	}
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	public Integer getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(Integer updateBy) {
		this.updateBy = updateBy;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Integer getIdICom() {
		return idICom;
	}
	public void setIdICom(Integer idICom) {
		this.idICom = idICom;
	}
	public Integer getParentIdICom() {
		return parentIdICom;
	}
	public void setParentIdICom(Integer parentIdICom) {
		this.parentIdICom = parentIdICom;
	}
	
}
