package com.Spring.Ecom_project.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Spring.Ecom_project.Model.Product;
import com.Spring.Ecom_project.Service.ProductService;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

	@Autowired
	private ProductService service;

	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducst() {
		return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable int id) {
		Product product = service.getProduct(id);
		if (product != null)
			return new ResponseEntity<>(product, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/product")
	public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile) {
		try {
			Product prd = service.addProduct(product, imageFile);
			System.out.println(prd.getStockQuantity());
			System.out.println(prd.isProductAvailable());
			return new ResponseEntity<>(prd, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/product/{id}/image")
	public ResponseEntity<byte[]> getImageById(@PathVariable int id){
		Product product = service.getProduct(id);
		byte[] image = product.getImageDate();
		return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType())).body(image);
	}
	
	@PutMapping("/product/{id}")
	public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Product product, @RequestPart MultipartFile imageFile){
		Product prd = null;
		try {
			prd = service.updateProduct(id, product, imageFile);
		} catch (IOException e) {
			return new ResponseEntity<String>("Failed to Update", HttpStatus.BAD_REQUEST);
		}
		if(prd!=null) return new ResponseEntity<String>("Updated Successfully", HttpStatus.OK);
		else return new ResponseEntity<String>("Failed to Update", HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/product/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable int id){
		Product prd = service.getProduct(id);
		if(prd!=null) {
			service.deleteProduct(id);
			return new ResponseEntity<String>("Prduct Successfully deleted", HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Failed to Delete", HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("products/search")
	public ResponseEntity<List<Product>> serachProducts(@RequestParam String keyword){
		List<Product> prd = service.searchProducts(keyword);
		return new ResponseEntity<List<Product>>(prd, HttpStatus.OK);
		
	}

}















