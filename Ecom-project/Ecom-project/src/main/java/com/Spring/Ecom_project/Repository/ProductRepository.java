package com.Spring.Ecom_project.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Spring.Ecom_project.Model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

	@Query("select p from Product p where "+ 
	"lower(p.name) like lower(concat('%', :keyword, '%')) or " + 
	"lower(p.description) like lower(concat('%', :keyword, '%')) or "+ 
	"lower(p.brand) like lower(concat('%', :keyword, '%')) or "+ 
	"lower(p.category) like lower(concat('%', :keyword, '%'))")
	List<Product> searchProducts(String keyword);

}
