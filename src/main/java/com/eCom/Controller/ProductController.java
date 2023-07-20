package com.eCom.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eCom.Model.Product;
import com.eCom.Payload.AppConstants;
import com.eCom.Payload.ProductResponse;
import com.eCom.Services.FileUploadService;
import com.eCom.Services.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private FileUploadService fileUploadService;

    @Value("${product.path.images}")
    private String imagePath;

    @PostMapping("/create/{cId}")
    public ResponseEntity<Product> createProduct(@RequestBody Product product, @PathVariable int cId) {
        Product newProduct = this.productService.createProduct(product, cId);

        new Product();

        return new ResponseEntity<Product>(newProduct, HttpStatus.CREATED);
    }

    @GetMapping("/view")
    public ProductResponse viewAllProduct(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.pageNumberString, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.pageSizeString, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.sortByString, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.sortDirString, required = false) String sortDir) {
        ProductResponse response = this.productService.viewAll(pageNumber, pageSize, sortBy, sortDir);
        return response;
    }

    @GetMapping("/viewById/{productId}")
    public ResponseEntity<Product> viewProductById(@PathVariable int productId) {
        return new ResponseEntity<Product>(this.productService.viewByProductId(productId), HttpStatus.OK);
    }

    @GetMapping("/delete/{pId}")
    public ResponseEntity<String> deleteProduct(@PathVariable int pId) {
        this.productService.deleteProduct(pId);
        return new ResponseEntity<String>("Product Deleted", HttpStatus.OK);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable int productId, @RequestBody Product product) {
        return new ResponseEntity<Product>(this.productService.updateProduct(productId, product), HttpStatus.ACCEPTED);
    }

    @GetMapping("/viewByCategory/{cId}")
    public ResponseEntity<List<Product>> viewProductByCategory(@PathVariable int cId){
        return new ResponseEntity<List<Product>>(this.productService.viewProductByCategory(cId),HttpStatus.FOUND);
    }

    @PostMapping("/images/{productId}")
    public ResponseEntity<?> uploadImageOfProduct(@PathVariable int productId, @RequestParam("product_Image") MultipartFile file){
        
        Product product = this.productService.viewByProductId(productId);

        String imageName = null;

        try{

            imageName = this.fileUploadService.uploadImage(imagePath, file);
            product.setProductImageName(imageName);

            Product updatedProduct = this.productService.updateProduct(productId, product);

            return new ResponseEntity<>(updatedProduct,HttpStatus.ACCEPTED);

        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(Map.of("Message","File not uploaded in server "),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
