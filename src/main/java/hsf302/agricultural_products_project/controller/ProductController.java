package hsf302.agricultural_products_project.controller;

import hsf302.agricultural_products_project.model.AgriculturalProduct;
import hsf302.agricultural_products_project.model.Category;
import hsf302.agricultural_products_project.model.Role;
import hsf302.agricultural_products_project.model.User;
import hsf302.agricultural_products_project.service.CategoryService;
import hsf302.agricultural_products_project.service.ProductService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static hsf302.agricultural_products_project.utils.VietnameseAccentRemover.normalize;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/admin/product")
    public String showManageProduct(HttpSession session,
                                    Model model,
                                    @RequestParam(value = "pageNo", defaultValue = "1") Integer page,
                                    @RequestParam(value = "keyword", required = false) String keyword) {
        User account = (User) session.getAttribute("account");
        if (account != null && Role.ROLE_ADMIN.equals(account.getRole())) {

            int pageSize = 5;
            List<AgriculturalProduct> all = productService.getAllProducts(); // Trả về List, không phân trang DB

            // Nếu có từ khoá tìm kiếm
            if (keyword != null && !keyword.isEmpty()) {
                String normalizedKeyword = normalize(keyword.toLowerCase());
                List<AgriculturalProduct> filtered = new ArrayList<>();
                for (AgriculturalProduct p : all) {
                    String name = normalize(p.getName().toLowerCase());
                    if (name.contains(normalizedKeyword)) {
                        filtered.add(p);
                    }
                }
                all = filtered;
                model.addAttribute("keyword", keyword);
            }

            // Phân trang thủ công
            int totalPage = (int) Math.ceil((double) all.size() / pageSize);
            int fromIndex = (page - 1) * pageSize;
            int toIndex = Math.min(fromIndex + pageSize, all.size());
            List<AgriculturalProduct> pageList = all.subList(fromIndex, toIndex);

            model.addAttribute("products", pageList);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPage", totalPage);
            model.addAttribute("account", account);
            return "admin/product/manageProduct";
        }
        return "redirect:/403";
    }



    @GetMapping("/admin/product/add")
    public String showCreateForm(HttpSession session,
                                 Model model) {
        User account = (User) session.getAttribute("account");
        if (account != null && Role.ROLE_ADMIN.equals(account.getRole())) {
            List<Category> categories = categoryService.getAllCategories();
            model.addAttribute("product", new AgriculturalProduct());
            model.addAttribute("categories", categories);
            return "admin/product/create";
        }
        return "redirect:/403";
    }

    @PostMapping("/admin/product/save")
    public String saveProduct(@Valid @ModelAttribute("product") AgriculturalProduct product,
                              BindingResult result,
                              Model model,
                              HttpSession session) {
        User account = (User) session.getAttribute("account");
        if (account != null && Role.ROLE_ADMIN.equals(account.getRole())) {
            if (result.hasErrors()) {
                model.addAttribute("categories", categoryService.getAllCategories());
                return "admin/product/create";
            }
            Long categoryId = product.getCategory().getCategoryId();
            Category fullCategory = categoryService.getCategoryByLong(categoryId);
            product.setCategory(fullCategory);
            productService.saveProduct(product);
            return "redirect:/admin/product";
        }
        return "redirect:/403";
    }

    @GetMapping("/admin/product/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id,
                               Model model,
                               HttpSession session) {
        User account = (User) session.getAttribute("account");
        if (account != null && Role.ROLE_ADMIN.equals(account.getRole())) {
            AgriculturalProduct product = productService.getProductById(id);
            List<Category> categories = categoryService.getAllCategories();

            model.addAttribute("product", product);
            model.addAttribute("categories", categories);

            return "admin/product/edit";
        }
        return "redirect:/403";
    }

    @PostMapping("/admin/product/edit")
    public String updateProduct(@Valid @ModelAttribute("product") AgriculturalProduct product,
                                BindingResult result,
                                HttpSession session,
                                Model model) {
        User account = (User) session.getAttribute("account");
        if (account != null && Role.ROLE_ADMIN.equals(account.getRole())) {
            if (result.hasErrors()) {
                model.addAttribute("categories", categoryService.getAllCategories());
                return "admin/product/edit";
            }
            productService.updateProduct(product);
            return "redirect:/admin/product";
        }
        return "redirect:/403";
    }


    @GetMapping("/admin/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id,
                                HttpSession session) {
        User account = (User) session.getAttribute("account");
        if (account != null && Role.ROLE_ADMIN.equals(account.getRole())) {
            productService.deleteProduct(id);
            return "redirect:/admin/product";
        }
        return "redirect:/403";
    }

    @GetMapping("/product/category/{type}")
    public String showProductType(@PathVariable("type") String type,
                                  Model model,
                                  HttpSession session) {
        User account = (User) session.getAttribute("account");
        List<AgriculturalProduct> products = null;
        int cateId;
        switch (type) {
            case "rau-la":
                cateId = 1;
                break;
            case "rau-cu":
                cateId = 2;
                break;
            case "nong-san-kho":
                cateId = 3;
                break;
            default:
                return "redirect:/product/all-product";
        }
        products = productService.getProductsByCategory(cateId);
        model.addAttribute("product", products.get(0).getCategory().getName());
        model.addAttribute("products", products);
        model.addAttribute("account", account);
        return "/product/list";
    }

    @GetMapping("/products/{id}")
    public String viewProductDetail(@PathVariable("id") Long id,
                                    Model model,
                                    HttpSession session) {
        User account = (User) session.getAttribute("account");
        AgriculturalProduct product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("account", account);
        return "product/detail";
    }

    @GetMapping("/product/all-product")
    public String showAllProducts(HttpSession session,
                                  Model model) {
        User account = (User) session.getAttribute("account");
        List<AgriculturalProduct> products = productService.getAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("account", account);


        return "product/all-product";
    }

    @PostMapping("/product/search")
    public String searchProducts(@RequestParam("keyword") String keyword,
                                 Model model,
                                 HttpSession session) {
        User account = (User) session.getAttribute("account");
        List<AgriculturalProduct> products = productService.getProductsByName(keyword);
        model.addAttribute("products", products);
        model.addAttribute("account", account);
        model.addAttribute("keyword", keyword);
        return "product/list";
    }

}