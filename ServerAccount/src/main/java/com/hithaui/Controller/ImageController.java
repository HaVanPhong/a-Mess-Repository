package com.hithaui.Controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.hithaui.Exception.NotFoundException;
import com.hithaui.Model.Image;
import com.hithaui.Repositories.ImageRepository;

@RestController
@RequestMapping("/api/img")
public class ImageController {

	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private Cloudinary cloudinary;
	
	@GetMapping
	public ResponseEntity<?> getImgLink (){
		List<Image> list= imageRepository.findAll();
		if (list==null) {
			throw new NotFoundException("NO");
		}
		return ResponseEntity.status(200).body(list);
	}
	
	@PostMapping
	public ResponseEntity<?> createImgLink (@PathParam("img") MultipartFile img) throws IOException{
		Map<?, ?> map= cloudinary.uploader().upload(img.getBytes(), ObjectUtils.emptyMap());
		String linkString = (String)map.get("secure_url");
		Image image = new Image(linkString);
		Image image2 = imageRepository.save(image);
		return ResponseEntity.status(201).body(image2);
	}
	
}
