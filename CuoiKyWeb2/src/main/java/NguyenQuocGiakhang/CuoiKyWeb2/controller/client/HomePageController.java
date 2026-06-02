package NguyenQuocGiakhang.CuoiKyWeb2.controller.client;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import NguyenQuocGiakhang.CuoiKyWeb2.domain.Order;
import NguyenQuocGiakhang.CuoiKyWeb2.domain.Product;
import NguyenQuocGiakhang.CuoiKyWeb2.domain.User;
import NguyenQuocGiakhang.CuoiKyWeb2.domain.dto.RegisterDTO;
import NguyenQuocGiakhang.CuoiKyWeb2.service.OrderService;
import NguyenQuocGiakhang.CuoiKyWeb2.service.ProductService;
import NguyenQuocGiakhang.CuoiKyWeb2.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomePageController {

	private final ProductService productService;
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final OrderService orderService;

	public HomePageController(
			ProductService productService,
			UserService userService,
			PasswordEncoder passwordEncoder,
			OrderService orderService) {
		this.productService = productService;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
		this.orderService = orderService;
	}

	@GetMapping("/")
	public String getHomePage(Model model) {
		List<Product> products = this.productService.fetchProducts();
		model.addAttribute("products", products);
		return "client/homepage/show";
	}

	@GetMapping("/register")
	public String getRegisterPage(Model model) {
		model.addAttribute("registerUser", new RegisterDTO());
		return "client/auth/register";
	}

	@PostMapping("/register")
	public String handleRegister(
			@ModelAttribute("registerUser") @Valid RegisterDTO registerDTO,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "client/auth/register";
		}

		User user = this.userService.registerDTOtoUser(registerDTO);
		String hashPassword = this.passwordEncoder.encode(user.getPassword());
		user.setPassword(hashPassword);
		user.setRole(this.userService.getRoleByName("USER"));
		this.userService.handleSaveUser(user);
		return "redirect:/login";
	}

	@GetMapping("/login")
	public String getLoginPage() {
		return "client/auth/login";
	}

	@GetMapping("/access-deny")
	public String getDenyPage() {
		return "client/auth/deny";
	}

	@GetMapping("/order-history")
	public String getOrderHistoryPage(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("id") == null) {
			return "redirect:/login";
		}

		User currentUser = new User();
		currentUser.setId((long) session.getAttribute("id"));
		List<Order> orders = this.orderService.fetchOrderByUser(currentUser);
		model.addAttribute("orders", orders);
		return "client/cart/order-history";
	}
}
