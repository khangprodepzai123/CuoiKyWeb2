package NguyenQuocGiakhang.CuoiKyWeb2.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import NguyenQuocGiakhang.CuoiKyWeb2.domain.Product;
import NguyenQuocGiakhang.CuoiKyWeb2.service.ProductService;
import NguyenQuocGiakhang.CuoiKyWeb2.service.UploadService;
import jakarta.validation.Valid;

@Controller
public class ProductController {

	private final UploadService uploadService;
	private final ProductService productService;

	public ProductController(UploadService uploadService, ProductService productService) {
		this.uploadService = uploadService;
		this.productService = productService;
	}

	@GetMapping("/admin/product")
	public String getProduct(Model model) {
		List<Product> products = this.productService.fetchProducts();
		model.addAttribute("products", products);
		return "admin/product/show";
	}

	@GetMapping("/admin/product/create")
	public String getCreateProductPage(Model model) {
		model.addAttribute("newProduct", new Product());
		return "admin/product/create";
	}

	@PostMapping("/admin/product/create")
	public String handleCreateProduct(
			@ModelAttribute("newProduct") @Valid Product product,
			BindingResult bindingResult,
			@RequestParam("hoidanitFile") MultipartFile file) {
		if (bindingResult.hasErrors()) {
			return "admin/product/create";
		}

		String image = this.uploadService.handleSaveUploadFile(file, "product");
		product.setImage(image);
		this.productService.createProduct(product);
		return "redirect:/admin/product";
	}

	@GetMapping("/admin/product/update/{id}")
	public String getUpdateProductPage(Model model, @PathVariable long id) {
		Optional<Product> currentProduct = this.productService.fetchProductById(id);
		if (currentProduct.isEmpty()) {
			return "redirect:/admin/product";
		}
		model.addAttribute("newProduct", currentProduct.get());
		return "admin/product/update";
	}

	@PostMapping("/admin/product/update")
	public String handleUpdateProduct(
			@ModelAttribute("newProduct") @Valid Product product,
			BindingResult bindingResult,
			@RequestParam("hoidanitFile") MultipartFile file) {
		if (bindingResult.hasErrors()) {
			return "admin/product/update";
		}

		Optional<Product> optional = this.productService.fetchProductById(product.getId());
		if (optional.isEmpty()) {
			return "redirect:/admin/product";
		}

		Product currentProduct = optional.get();
		if (!file.isEmpty()) {
			String img = this.uploadService.handleSaveUploadFile(file, "product");
			currentProduct.setImage(img);
		}

		currentProduct.setName(product.getName());
		currentProduct.setPrice(product.getPrice());
		currentProduct.setQuantity(product.getQuantity());
		currentProduct.setDetailDesc(product.getDetailDesc());
		currentProduct.setShortDesc(product.getShortDesc());
		currentProduct.setFactory(product.getFactory());
		currentProduct.setTarget(product.getTarget());

		this.productService.createProduct(currentProduct);
		return "redirect:/admin/product";
	}

	@GetMapping("/admin/product/delete/{id}")
	public String getDeleteProductPage(Model model, @PathVariable long id) {
		model.addAttribute("id", id);
		model.addAttribute("newProduct", new Product());
		return "admin/product/delete";
	}

	@PostMapping("/admin/product/delete")
	public String postDeleteProduct(@ModelAttribute("newProduct") Product product) {
		this.productService.deleteProduct(product.getId());
		return "redirect:/admin/product";
	}

	@GetMapping("/admin/product/{id}")
	public String getProductDetailPage(Model model, @PathVariable long id) {
		Optional<Product> optional = this.productService.fetchProductById(id);
		if (optional.isEmpty()) {
			return "redirect:/admin/product";
		}
		model.addAttribute("product", optional.get());
		model.addAttribute("id", id);
		return "admin/product/detail";
	}
}
