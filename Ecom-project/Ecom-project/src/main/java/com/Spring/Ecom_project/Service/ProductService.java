package com.Spring.Ecom_project.Service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Spring.Ecom_project.Model.Product;
import com.Spring.Ecom_project.Repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repository;

	public List<Product> getAllProducts() {
		return repository.findAll();
	}

	public Product getProduct(int id) {
		return repository.findById(id).orElse(null);
	}

	public Product addProduct(Product product, MultipartFile image) throws IOException {
		product.setImageName(image.getOriginalFilename());
		product.setImageType(image.getContentType());
		product.setImageDate(image.getBytes());
		return repository.save(product);
	}

	public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
		product.setImageDate(imageFile.getBytes());
		product.setImageName(imageFile.getOriginalFilename());
		product.setImageType(imageFile.getContentType());
		return repository.save(product);
	}

	public void deleteProduct(int id) {
		repository.deleteById(id);
	}

	public List<Product> searchProducts(String keyword) {
		return repository.searchProducts(keyword);
	}

}
