package dto;

public class StorageDTO {
	private String ObjectStoreId;
	private String ObjectStoreName;
	private String ObjectStoreDesc;
	private String repositoryId;
	private String path;
	private String type;
	
	public String getObjectStoreId() {
		return ObjectStoreId;
	}
	public void setObjectStoreId(String objectStoreId) {
		ObjectStoreId = objectStoreId;
	}
	public String getObjectStoreName() {
		return ObjectStoreName;
	}
	public void setObjectStoreName(String objectStoreName) {
		ObjectStoreName = objectStoreName;
	}
	public String getObjectStoreDesc() {
		return ObjectStoreDesc;
	}
	public void setObjectStoreDesc(String objectStoreDesc) {
		ObjectStoreDesc = objectStoreDesc;
	}
	public String getRepositoryId() {
		return repositoryId;
	}
	public void setRepositoryId(String repositoryId) {
		this.repositoryId = repositoryId;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
