package com.javatechie;

import com.javatechie.model.FileHolder;
import com.javatechie.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Console;
import java.io.IOException;

@SpringBootApplication
@RestController
@RequestMapping("/image")
public class StorageServiceApplication {

	@Autowired
	private StorageService service;

	@PostMapping
	public ResponseEntity<?> uploadImage(@RequestParam("input")MultipartFile file) throws IOException {
		String uploadImage = service.uploadImage(file);
		return ResponseEntity.status(HttpStatus.OK)
				.body(uploadImage);
	}

	@GetMapping("/{fileName}")
	public ResponseEntity<?> downloadImage(@PathVariable String fileName){
		FileHolder imageData = service.downloadImage(fileName);
		
		System.out.println(imageData.getFileType());
		
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.valueOf(imageData.getFileType()))
				.body(imageData.getFileData());

	}
	@GetMapping("/find-all")
	public ResponseEntity<?> getFileNames(){
		
		return ResponseEntity.ok(service.getAllFileDetails());
	}

	public static void main(String[] args) {
		SpringApplication.run(StorageServiceApplication.class, args);
	}

}
