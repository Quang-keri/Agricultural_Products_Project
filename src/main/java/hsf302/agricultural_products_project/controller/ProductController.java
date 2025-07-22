package hsf302.agricultural_products_project.controller;

import hsf302.agricultural_products_project.model.AgriculturalProduct;
import hsf302.agricultural_products_project.model.Category;
import hsf302.agricultural_products_project.model.User;
import hsf302.agricultural_products_project.service.CategoryService;
import hsf302.agricultural_products_project.service.ProductService;
import jakarta.servlet.http.HttpSession;
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
    public String showManageProduct(HttpSession session, Model model) {
        User account = (User) session.getAttribute("account");
        if (account != null) {
            model.addAttribute("products", productService.getAllProducts());
            model.addAttribute("account", account);
            System.err.println("Session Account: " + account);
            return "admin/product/manageProduct";
        }
        return "redirect:/403";
    }

    @GetMapping("/admin/product/add")
    public String showCreateForm(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("product", new AgriculturalProduct());
        model.addAttribute("categories", categories);
        return "admin/product/create";
    }

    @PostMapping("/admin/product/save")
    public String saveProduct(@ModelAttribute("product") AgriculturalProduct product) {
        Long categoryId = product.getCategory().getCategoryId();
        Category fullCategory = categoryService.getCategoryByLong(categoryId);
        product.setCategory(fullCategory);
        productService.saveProduct(product);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        AgriculturalProduct product = productService.getProductById(id);
        List<Category> categories = categoryService.getAllCategories();

        model.addAttribute("product", product);
        model.addAttribute("categories", categories);

        return "admin/product/edit";
    }

    @PostMapping("/admin/product/edit")
    public String updateProduct(@ModelAttribute("product") AgriculturalProduct product) {
        productService.updateProduct(product);

        return "redirect:/admin/product";
    }


    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/product";
    }

    @GetMapping("/product/rau-la")
    public String showProductRauLa(Model model, HttpSession session) {
        User account = (User) session.getAttribute("account");
        List<AgriculturalProduct> products = productService.getProductsByCategory(6);
        model.addAttribute("products", products);
        model.addAttribute("account", account);
        return "/product/list";
    }

    @GetMapping("/product/rau-cu")
    public String showProductraucu(Model model, HttpSession session) {
        User account = (User) session.getAttribute("account");
        List<AgriculturalProduct> products = productService.getProductsByCategory(1);
        model.addAttribute("products", products);
        model.addAttribute("account", account);
        return "/product/list";
    }

    @GetMapping("/product/trai-cay")
    public String showProductnongsankho(Model model, HttpSession session) {
        User account = (User) session.getAttribute("account");
        List<AgriculturalProduct> products = productService.getProductsByCategory(7);
        model.addAttribute("products", products);
        model.addAttribute("account", account);
        return "/product/list";
    }

    @GetMapping("/products/{id}")
    public String viewProductDetail(@PathVariable("id") Long id, Model model, HttpSession session) {
        User account = (User) session.getAttribute("account");
        AgriculturalProduct product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("account", account);
        return "product/detail";
    }

    @GetMapping("/product/all-product")
    public String showAllProducts(HttpSession session, Model model) {
        User account = (User) session.getAttribute("account");
        List<AgriculturalProduct> products = productService.getAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("account", account);
        return "product/all-product";
    }

}