package com.eCom.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.eCom.Exception.ResourceNotFoundException;
import com.eCom.Model.Category;
import com.eCom.Model.Product;
import com.eCom.Payload.ProductResponse;
import com.eCom.Repositories.CategoryRepo;
import com.eCom.Repositories.ProductRepo;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    public Product createProduct(Product product, int cId) {
        Category category = this.categoryRepo.findById(cId)
                .orElseThrow(() -> new ResourceNotFoundException("Sorry the requested resource is not there !!"));
        product.setCategory(category);
        return this.productRepo.save(product);
    }

    public ProductResponse viewAll(int pageNumber,int pageSize,String sortBy,String sortDir){

        Sort sort = null;

        if(sortDir.trim().toLowerCase().equals("asc")){
            sort = Sort.by(sortBy).ascending();
            System.out.println(sort);
        }else{
            sort = Sort.by(sortBy).descending();
            System.out.println(sort);
        }

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = this.productRepo.findAll(pageable);
        List<Product> pageProduct = page.getContent();
        pageProduct.stream().filter(p->p.isLive()).collect(Collectors.toList());

        ProductResponse response = new ProductResponse();
        response.setContent(pageProduct);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());

        return response;
    }

    public Product viewByProductId(int productId) {
        return this.productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Sorry the requested resource is not there !!"));
    }

    public void deleteProduct(int pId) {
        Product product = this.productRepo.findById(pId)
                .orElseThrow(() -> new ResourceNotFoundException("The provided resource is not there sorry !!"));
        this.productRepo.delete(product);
    }

    public Product updateProduct(int pId, Product newProduct) {
        Product oldProduct = this.productRepo.findById(pId)
                .orElseThrow(() -> new ResourceNotFoundException("Sorry the requested resource is not there !!"));

        oldProduct.setLive(newProduct.isLive());
        oldProduct.setProductDesc(newProduct.getProductDesc());
        oldProduct.setProductImageName(newProduct.getProductImageName());
        oldProduct.setProductName(newProduct.getProductName());
        oldProduct.setProductPrice(newProduct.getProductPrice());
        oldProduct.setProductQuantity(newProduct.getProductQuantity());
        oldProduct.setStock(newProduct.isStock());

        return this.productRepo.save(oldProduct);
    }

    public List<Product> viewProductByCategory(int cId){
        Category category = this.categoryRepo.findById(cId) .orElseThrow(() -> new ResourceNotFoundException("Sorry the requested resource is not there !!"));
;
        return this.productRepo.findByCategory(category);
    }
}
