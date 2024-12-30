package com.ashwin.dkmart.Controller;

import java.awt.Image;
import java.net.http.HttpHeaders;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties.Tomcat.Resource;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ashwin.dkmart.DTO.ImageDTO;
import com.ashwin.dkmart.Response.ApiResponse;
import com.ashwin.dkmart.Service.Image.IImageService;

@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {

	
	@Autowired
	private IImageService iImageService;
	
	@PostMapping("/Upload")
	public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long productId) {
		try {
			List<ImageDTO> imageDTOs = iImageService.addImage(files, productId);
			return ResponseEntity.ok(new ApiResponse("Upload Image Successfull!", imageDTOs));
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload Failed!",e.getMessage()));
		}
		
	}
	
	@GetMapping("/image/download/{imageId}")
	public ResponseEntity<ByteArrayResource> downloadImage(@PathVariable Long imageId) throws SQLException{
		com.ashwin.dkmart.Entity.Image image = iImageService.getImageById(imageId);

	        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));

	        return  ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileTpye()))
	                .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +image.getFileName() + "\"")
	                .body(resource);
	}
	
	
	
	@PutMapping("/image/{imageId}/update")
	public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file){
		com.ashwin.dkmart.Entity.Image image = iImageService.getImageById(imageId);

		try {

			if(image != null) {
				iImageService.updateImage(file, imageId);
				return ResponseEntity.ok(new ApiResponse("Upload Success!", null));
			}

		}catch (ConfigDataResourceNotFoundException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));

		}

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload Failed!", null));
		
	}
	
	@DeleteMapping("/image/{imageId}/delete")
	public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId){
		com.ashwin.dkmart.Entity.Image image = iImageService.getImageById(imageId);
		try {

			if(image != null) {
				iImageService.deleteImage(imageId);
				return ResponseEntity.ok(new ApiResponse("Delete Success!", null));
			}

		}catch (ConfigDataResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete Failed!", null));
		
	}
	
	
	
}
