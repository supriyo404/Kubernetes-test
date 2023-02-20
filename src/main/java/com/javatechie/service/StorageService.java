package com.javatechie.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.javatechie.entity.ImageData;
import com.javatechie.model.FileHolder;
import com.javatechie.respository.StorageRepository;
import com.javatechie.util.ImageUtils;

@Service
public class StorageService {

    @Autowired
    private StorageRepository repository;

    public String uploadImage(MultipartFile file) throws IOException {

        ImageData imageData = repository.save(ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .payload(ImageUtils.compressImage(file.getBytes())).build());
        if (imageData != null) {
            return "file uploaded successfully : " + file.getOriginalFilename();
        }
        return null;
    }

    @Transactional //required for large image save in postgresDB
    public FileHolder downloadImage(String fileName){
        Optional<ImageData> dbImageData = repository.findByName(fileName);
        byte[] images=ImageUtils.decompressImage(dbImageData.get().getPayload());
        String mediaType = dbImageData.get().getType();
        return new FileHolder(mediaType,images);
    }
    
    public List<String> getAllFileDetails(){
    	
    	return repository.findAll().stream()
    			.map(ImageData::getName)
    			.collect(Collectors.toList());
    }
}
