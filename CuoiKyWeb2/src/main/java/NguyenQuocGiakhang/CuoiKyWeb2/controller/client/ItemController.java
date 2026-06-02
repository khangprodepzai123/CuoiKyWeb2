package NguyenQuocGiakhang.CuoiKyWeb2.controller.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import NguyenQuocGiakhang.CuoiKyWeb2.domain.Cart;
import NguyenQuocGiakhang.CuoiKyWeb2.domain.CartDetail;
import NguyenQuocGiakhang.CuoiKyWeb2.domain.User;
import NguyenQuocGiakhang.CuoiKyWeb2.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ItemController {
	private final CartService cartService;

	public ItemController(CartService cartService) {
		this.cartService = cartService;
	}

	@PostMapping("/add-product-to-cart/{id}")
	public String addProductToCart(@PathVariable long id, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return "redirect:/login";
		}

		String email = (String) session.getAttribute("email");
		this.cartService.handleAddProductToCart(email, id, session, 1);
		return "redirect:/cart";
	}

	@GetMapping("/cart")
	public String getCartPage(Model model, HttpServletRequest request) {
		User currentUser = new User();
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("id") == null) {
			return "redirect:/login";
		}

		long id = (long) session.getAttribute("id");
		currentUser.setId(id);

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
}

