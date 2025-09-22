package com.backend.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.blog.config.AppConstants;
import com.backend.blog.exceptions.InvalidFileFormatException;
import com.backend.blog.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		
		// File name
		String name = file.getOriginalFilename();

		
		//checking image format
		if(!(name.substring(name.lastIndexOf(".") + AppConstants.ONE).equalsIgnoreCase("JPEG")
			|| name.substring(name.lastIndexOf(".") + AppConstants.ONE).equalsIgnoreCase("JPG")
			|| name.substring(name.lastIndexOf(".") + AppConstants.ONE).equalsIgnoreCase("PNG"))) {
			throw new InvalidFileFormatException(AppConstants.INVALID_FILE_FORMAT);
		}
		
		
		// Random name generate file
		String randomID = UUID.randomUUID().toString();
		String newFileName = randomID.concat(name.substring(name.lastIndexOf(".")));

		// Fullpath
		String filePath = path + File.separator + newFileName;

		// Create folder if not created
		File f = new File(path);
		if (!(f.exists())) {
			f.mkdir();
		}

		// file copy
		Files.copy(file.getInputStream(), Paths.get(filePath));

		return newFileName;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		
		String fullPath = path + File.separator + fileName;
		
		InputStream is = new FileInputStream(fullPath);
		
		return is;
	}

}
