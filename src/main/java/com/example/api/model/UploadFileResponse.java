package com.example.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.web.multipart.MultipartFile;
 
@Document(collection = "image")
public class UploadFileResponse {
	 public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	@Id
	    private String id;
//	    @Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)
//	    @Id
	 @Indexed(unique = true)
	 private int code;
	 
	    @Field(value = "name")
    private String fileName;
	    @Field(value = "img_url")
    private String fileDownloadUri;
	    @Field(value = "type")
    private String fileType;
	    @Field(value = "size")
    private long size;

    public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

	public UploadFileResponse(String fileName, String fileDownloadUri, String fileType, long size,int code) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
        this.code=code;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    public void setFileDownloadUri(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
