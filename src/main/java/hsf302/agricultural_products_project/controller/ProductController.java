package hsf302.agricultural_products_project.controller;

import hsf302.agricultural_products_project.model.Category;
import hsf302.agricultural_products_project.model.Product;
import hsf302.agricultural_products_project.service.CategoryService;
import hsf302.agricultural_products_project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/admin/product")
    public String showManageProduct(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin/product/manageProduct";
    }

    @GetMapping("/admin/product/add")
    public String showCreateForm(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categories);
        return "admin/product/create";
    }

    @PostMapping("/admin/product/save")
    public String saveProduct(@ModelAttribute("product") Product product) {
        int categoryId = product.getCategory().getCategoryId();
        Category fullCategory = categoryService.getCategoryById(categoryId);
        product.setCategory(fullCategory);
        productService.saveProduct(product);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id);
        List<Category> categories = categoryService.getAllCategories();

        model.addAttribute("product", product);
        model.addAttribute("categories", categories);

        return "admin/product/edit";
    }

    @PostMapping("/admin/product/edit")
    public String updateProduct(@ModelAttribute("product") Product product) {
        productService.saveProduct(product);

        return "redirect:/admin/product";
    }


    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/product";
    }

    @GetMapping("/product/rau-la")
    public String showProductRauLa(Model model) {
        List<Product> products = productService.getProductsByCategory(1);
        model.addAttribute("products", products);
        return "/product/list";
    }

    @GetMapping("/product/rau-cu")
    public String showProductraucu(Model model) {
        List<Product> products = productService.getProductsByCategory(2);
        model.addAttribute("products", products);
        return "/product/list";
    }

    @GetMapping("/product/nong-san-kho")
    public String showProductnongsankho(Model model) {
        List<Product> products = productService.getProductsByCategory(3);
        model.addAttribute("products", products);
        return "/product/list";
    }

    @GetMapping("/products/{id}")
    public String viewProductDetail(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product/detail";
    }

    @GetMapping("/product/all-product")
    public String showAllProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "product/all-product"; // trỏ tới file all-product.html trong thư mục templates/product
    }

}