package NguyenQuocGiakhang.CuoiKyWeb2.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import NguyenQuocGiakhang.CuoiKyWeb2.domain.Cart;
import NguyenQuocGiakhang.CuoiKyWeb2.domain.CartDetail;
import NguyenQuocGiakhang.CuoiKyWeb2.domain.Product;
import NguyenQuocGiakhang.CuoiKyWeb2.domain.User;
import NguyenQuocGiakhang.CuoiKyWeb2.repository.CartDetailRepository;
import NguyenQuocGiakhang.CuoiKyWeb2.repository.CartRepository;
import NguyenQuocGiakhang.CuoiKyWeb2.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;

@Service
public class CartService {
	private final CartRepository cartRepository;
	private final CartDetailRepository cartDetailRepository;
	private final ProductRepository productRepository;
	private final UserService userService;

	public CartService(
			CartRepository cartRepository,
			CartDetailRepository cartDetailRepository,
			ProductRepository productRepository,
			UserService userService) {
		this.cartRepository = cartRepository;
		this.cartDetailRepository = cartDetailRepository;
		this.productRepository = productRepository;
		this.userService = userService;
	}

	public Cart fetchByUser(User user) {
		return this.cartRepository.findByUser(user);
	}

	public void handleAddProductToCart(String email, long productId, HttpSession session, long quantity) {
		if (email == null || email.isBlank()) {
			return;
		}

		User user = this.userService.getUserByEmail(email);
		if (user == null) {
			return;
		}

		Cart cart = this.cartRepository.findByUser(user);
		if (cart == null) {
			Cart newCart = new Cart();
			newCart.setUser(user);
			newCart.setSum(0);
			cart = this.cartRepository.save(newCart);
		}

		Optional<Product> productOptional = this.productRepository.findById(productId);
		if (productOptional.isEmpty()) {
			return;
		}

		Product realProduct = productOptional.get();
		CartDetail oldDetail = this.cartDetailRepository.findByCartAndProduct(cart, realProduct);
		if (oldDetail == null) {
			CartDetail cd = new CartDetail();
			cd.setCart(cart);
			cd.setProduct(realProduct);
			cd.setPrice(realProduct.getPrice());
			cd.setQuantity(quantity);
			this.cartDetailRepository.save(cd);

			int s = cart.getSum() + 1;
			cart.setSum(s);
			this.cartRepository.save(cart);
			if (session != null) {
				session.setAttribute("sum", s);
			}
		} else {
			oldDetail.setQuantity(oldDetail.getQuantity() + quantity);
			this.cartDetailRepository.save(oldDetail);
		}
	}

	public void handleRemoveCartDetail(long cartDetailId, HttpSession session) {
		Optional<CartDetail> cartDetailOptional = this.cartDetailRepository.findById(cartDetailId);
		if (cartDetailOptional.isEmpty()) {
			return;
		}

		CartDetail cartDetail = cartDetailOptional.get();
		Cart currentCart = cartDetail.getCart();

		this.cartDetailRepository.deleteById(cartDetailId);

		if (currentCart.getSum() > 1) {
			int s = currentCart.getSum() - 1;
			currentCart.setSum(s);
			this.cartRepository.save(currentCart);
			if (session != null) {
				session.setAttribute("sum", s);
			}
		} else {
			this.cartRepository.deleteById(currentCart.getId());
			if (session != null) {
				session.setAttribute("sum", 0);
			}
		}
	}
}

