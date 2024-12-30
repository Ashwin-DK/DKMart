package com.ashwin.dkmart.Service.Image;

import java.io.IOException;
import java.lang.module.ResolutionException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ashwin.dkmart.DTO.ImageDTO;
import com.ashwin.dkmart.Entity.Image;
import com.ashwin.dkmart.Entity.Product;
import com.ashwin.dkmart.Exception.ResourseNotFound;
import com.ashwin.dkmart.Repository.ImageRepository;
import com.ashwin.dkmart.Repository.ProductRepository;
import com.ashwin.dkmart.Service.Product.ProductService;

@Service 
public class ImageService implements IImageService{
	
	@Autowired
	ImageRepository imageRepository;
	
	@Autowired 
	ProductService productService;

	@Override
	public Image getImageById(Long Id) {
		return imageRepository.findById(Id)
				.orElseThrow(() -> new ResourseNotFound(Id + " image Id invalid"));
	}

	@Override
	public void updateImage(MultipartFile file, Long imageId) {
		Image image = getImageById(imageId);
		
		try {
			image.setFileName(file.getOriginalFilename());
			image.setFileTpye(file.getContentType());
			imageRepository.save(image);
		}catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException();
		}
		 
	}

	@Override
	public List<ImageDTO> addImage(List<MultipartFile> files, Long productId) {
		// TODO Auto-generated method stub
		Product product = productService.productById(productId);
		List<ImageDTO> imageList = new ArrayList<>();
		for(MultipartFile file: files) {
			try {
				Image image = new Image();
				image.setFileName(file.getOriginalFilename());
				image.setFileTpye(file.getContentType());
				image.setImage(new SerialBlob(file.getBytes()));
				
				String bulidurl = "/api/v1/images/image/download/";
				image.setDownloadUrl(bulidurl+image.getId());
				image.setProduct(product);
				
				Image savedImage = imageRepository.save(image);
				savedImage.setDownloadUrl(bulidurl+savedImage.getId());
				
				
				ImageDTO dto = new ImageDTO();
				dto.setImageId(savedImage.getId());
				dto.setFileName(savedImage.getFileName());
				dto.setDownloadUrl(savedImage.getDownloadUrl());
				imageList.add(dto);
			}catch (IOException | SQLException e ) {
				throw new RuntimeException(e.getMessage());	
			}
			

		}
		return imageList;
	}

	@Override
	public void deleteImage(Long imageId) {
		imageRepository.findById(imageId)
				.ifPresentOrElse(imageRepository::delete, 
						() -> new ResourseNotFound(imageId + " image Id invalid"));
		
		
		
	}

}
