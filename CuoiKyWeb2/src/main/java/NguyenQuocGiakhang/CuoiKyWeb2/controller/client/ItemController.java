package NguyenQuocGiakhang.CuoiKyWeb2.controller.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import NguyenQuocGiakhang.CuoiKyWeb2.domain.Cart;
import NguyenQuocGiakhang.CuoiKyWeb2.domain.CartDetail;
import NguyenQuocGiakhang.CuoiKyWeb2.domain.Product;
import NguyenQuocGiakhang.CuoiKyWeb2.domain.User;
import NguyenQuocGiakhang.CuoiKyWeb2.service.CartService;
import NguyenQuocGiakhang.CuoiKyWeb2.service.OrderService;
import NguyenQuocGiakhang.CuoiKyWeb2.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ItemController {
	private final CartService cartService;
	private final ProductService productService;
	private final OrderService orderService;

	public ItemController(CartService cartService, ProductService productService, OrderService orderService) {
		this.cartService = cartService;
		this.productService = productService;
		this.orderService = orderService;
	}

	@GetMapping("/product/{id}")
	public String getProductPage(Model model, @PathVariable long id) {
		Optional<Product> productOptional = this.productService.fetchProductById(id);
		if (productOptional.isEmpty()) {
			return "redirect:/";
		}
		model.addAttribute("product", productOptional.get());
		return "client/product/detail";
	}

	@PostMapping("/add-product-to-cart/{id}")
	public String addProductToCart(@PathVariable long id, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("email") == null) {
			return "redirect:/login";
		}

		String email = (String) session.getAttribute("email");
		this.cartService.handleAddProductToCart(email, id, session, 1);
		return "redirect:/cart";
	}

	@PostMapping("/add-product-from-view-detail")
	public String handleAddProductFromViewDetail(
			@RequestParam("id") long id,
			@RequestParam("quantity") long quantity,
			HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("email") == null) {
			return "redirect:/login";
		}

		String email = (String) session.getAttribute("email");
		long qty = quantity < 1 ? 1 : quantity;
		this.cartService.handleAddProductToCart(email, id, session, qty);
		return "redirect:/product/" + id;
	}

	@GetMapping("/cart")
	public String getCartPage(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("id") == null) {
			return "redirect:/login";
		}

		User currentUser = new User();
		currentUser.setId((long) session.getAttribute("id"));

		Cart cart = this.cartService.fetchByUser(currentUser);
		List<CartDetail> cartDetails = cart == null ? new ArrayList<>() : cart.getCartDetails();

		double totalPrice = 0;
		for (CartDetail cd : cartDetails) {
			totalPrice += cd.getPrice() * cd.getQuantity();
		}

		model.addAttribute("cartDetails", cartDetails);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("cart", cart);
		return "client/cart/show";
	}

	@PostMapping("/delete-cart-product/{id}")
	public String deleteCartDetail(@PathVariable long id, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		this.cartService.handleRemoveCartDetail(id, session);
		return "redirect:/cart";
	}

	@GetMapping("/checkout")
	public String getCheckOutPage(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("id") == null) {
			return "redirect:/login";
		}

		User currentUser = new User();
		currentUser.setId((long) session.getAttribute("id"));

		Cart cart = this.cartService.fetchByUser(currentUser);
		List<CartDetail> cartDetails = cart == null ? new ArrayList<>() : cart.getCartDetails();

		double totalPrice = 0;
		for (CartDetail cd : cartDetails) {
			totalPrice += cd.getPrice() * cd.getQuantity();
		}

		model.addAttribute("cartDetails", cartDetails);
		model.addAttribute("totalPrice", totalPrice);
		return "client/cart/checkout";
	}

	@PostMapping("/confirm-checkout")
	public String confirmCheckout(@ModelAttribute("cart") Cart cart) {
		List<CartDetail> cartDetails = cart == null ? new ArrayList<>() : cart.getCartDetails();
		this.cartService.handleUpdateCartBeforeCheckout(cartDetails);
		return "redirect:/checkout";
	}

	@PostMapping("/place-order")
	public String handlePlaceOrder(
			HttpServletRequest request,
			@RequestParam("receiverName") String receiverName,
			@RequestParam("receiverAddress") String receiverAddress,
			@RequestParam("receiverPhone") String receiverPhone) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("id") == null) {
			return "redirect:/login";
		}

		long userId = (long) session.getAttribute("id");
		this.orderService.handlePlaceOrder(userId, session, receiverName, receiverAddress, receiverPhone);
		return "redirect:/thanks";
	}

	@GetMapping("/thanks")
	public String getThankYouPage() {
		return "client/cart/thanks";
	}
}
