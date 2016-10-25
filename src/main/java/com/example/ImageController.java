package com.example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
public class ImageController {

	@Autowired
	ServletContext servletContext;

	@Value("${imagepath}")
	private String imagepath;

	@RequestMapping("/photo")
	@ResponseBody
	public ResponseEntity<byte[]> downloadUserAvatarImage() throws IOException {
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		File fiel1 = new File(imagepath + "IMG_9611.JPG");

		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(fiel1),
				headers, HttpStatus.CREATED);
	}

	public List<String> listFile(String dir) {
		List<String> a = new ArrayList<String>();
		Collection<File> files = FileUtils.listFiles(FileUtils.getFile(dir),
				TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		files.forEach(p -> a.add(p.getAbsolutePath()));
		return a;
	}
}