package com.example.filepoller;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FilesInformation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "filesInformationId")
	private Integer filesInformationId;
	
	private String folderName;
	
	private String fileName;

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getFilesInformationId() {
		return filesInformationId;
	}

	public void setFilesInformationId(Integer filesInformationId) {
		this.filesInformationId = filesInformationId;
	}
	
}
