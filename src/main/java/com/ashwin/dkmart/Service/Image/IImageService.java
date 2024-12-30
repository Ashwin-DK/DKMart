package com.ashwin.dkmart.Service.Image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ashwin.dkmart.DTO.ImageDTO;
import com.ashwin.dkmart.Entity.Image;

public interface IImageService {
	
	Image getImageById(Long Id);
	void updateImage(MultipartFile file, Long imageId);
	List<ImageDTO> addImage(List<MultipartFile> files, Long productId);
	void deleteImage(Long imageId);
	

}
