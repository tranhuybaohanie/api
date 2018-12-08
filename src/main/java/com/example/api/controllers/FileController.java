package com.example.api.controllers;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.multipart.MultipartFile;

import com.example.api.dao.UploadFileResponseRepositoryCustomImpl;
import com.example.api.exception.MyFileNotFoundException;
import com.example.api.model.UploadFileResponse;
import com.example.api.services.FileStorageService;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	@Autowired
	private FileStorageService fileStorageService;
	@Autowired
	private UploadFileResponseRepositoryCustomImpl uploadFileResponseDao;

	private Path rootLocation;

	private final SimpMessagingTemplate template;

	@Autowired
	FileController(SimpMessagingTemplate template) {
		this.template = template;
	}

	// @MessageMapping("/send/message")
	// public void onReceivedMesage(String message){
	// this.template.convertAndSend("/chat",
	// new SimpleDateFormat("HH:mm:ss").format(new Date())+"- "+message);
	// }

	@MessageMapping("/update/image")
	public void onReceivedMesage(String message) {
		System.out.println("send: "+message);
		this.template.convertAndSend("/image/snapshot", 
				//new SimpleDateFormat("HH:mm:ss").format(new Date()) + "- " + message
				uploadFileResponseDao.getAll()
				);
	}

	@RequestMapping(value = "/image", method = RequestMethod.POST)
	public ResponseEntity<String> upload1(@RequestParam("filedata") MultipartFile file,
			@RequestParam("name") String name, HttpServletRequest httpServletRequest) throws IOException {
		// TODO process the file here
		System.out.println("image hear");
		UploadFileResponse image = new UploadFileResponse(name,
				httpServletRequest.getHeader("host").toString() + "/image/" + file.getOriginalFilename(),
				file.getOriginalFilename(), file.getSize(), uploadFileResponseDao.getMaxCode() + 1);
		if (uploadFileResponseDao.add(image)) {
			File convertFile = new File("./upload/" + file.getOriginalFilename());
			System.out.println("uploadRootPath=" + convertFile);

			// Tạo thư mục gốc upload nếu nó không tồn tại.
			// if (!convertFile.exists()) {
			// convertFile.mkdirs();
			// }
			convertFile.createNewFile();

			FileOutputStream fout = new FileOutputStream(convertFile);
			fout.write(file.getBytes());
			fout.close();
			this.template.convertAndSend("/image/snapshot", 
					uploadFileResponseDao.getAll()
					);
			return ResponseEntity.ok().body("ok");
		} else {
			return ResponseEntity.badRequest().body("fail");
		}

	}

	@RequestMapping(value = "/image/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> Delete(@PathVariable("id") String id) throws IOException {
		// TODO process the file here
		UploadFileResponse image = uploadFileResponseDao.findById(id);

		if (uploadFileResponseDao.delete(id)) {
			File convertFile = new File("./upload/" + image.getFileType());
			System.out.println("uploadRootPath=" + convertFile);
			convertFile.delete();
			return ResponseEntity.ok().body("ok");
		} else {
			return ResponseEntity.badRequest().body("fail");
		}

	}

	@RequestMapping(value = "/image/{filename}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)

	public ResponseEntity<Resource> serveFile(@PathVariable("filename") String filename) throws IOException {
		System.out.println("asdf" + filename);
		File convertFile = new File("./upload/" + filename);

		System.out.println("uploadRootPath=" + convertFile);
		Resource resource = new UrlResource(convertFile.toURI());

		return ResponseEntity.ok().body(resource);
	}

	@RequestMapping(value = "/image", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<UploadFileResponse> serveFiles() throws IOException {
		try {
			return uploadFileResponseDao.getAll();
		} catch (Exception e) {
			return null;
		}
	}

	// @GetMapping("/downloadFile/{fileName:.+}")
	// public ResponseEntity<Resource> downloadFile(@PathVariable String fileName,
	// HttpServletRequest request) {
	// // Load file as Resource
	// Resource resource = fileStorageService.loadFileAsResource(fileName);
	//
	// // Try to determine file's content type
	// String contentType = null;
	// try {
	// contentType =
	// request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
	// } catch (IOException ex) {
	// logger.info("Could not determine file type.");
	// }
	//
	// // Fallback to the default content type if type could not be determined
	// if(contentType == null) {
	// contentType = "application/octet-stream";
	// }
	//
	// return ResponseEntity.ok()
	// .contentType(MediaType.parseMediaType(contentType))
	// .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
	// resource.getFilename() + "\"")
	// .body(resource);
	// }

}