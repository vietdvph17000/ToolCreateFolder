package dto;

import java.io.Serializable;

public class SubFolderDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private String folderId;
	private String folderName;
	private Integer folderLevel;
	private String pFolderId;
	private Integer idICom;
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
	public Integer getIdICom() {
		return idICom;
	}
	public void setIdICom(Integer idICom) {
		this.idICom = idICom;
	}
	
	
	
	
	
}
