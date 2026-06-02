package NguyenQuocGiakhang.CuoiKyWeb2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import NguyenQuocGiakhang.CuoiKyWeb2.domain.Cart;
import NguyenQuocGiakhang.CuoiKyWeb2.domain.CartDetail;
import NguyenQuocGiakhang.CuoiKyWeb2.domain.Order;
import NguyenQuocGiakhang.CuoiKyWeb2.domain.OrderDetail;
import NguyenQuocGiakhang.CuoiKyWeb2.domain.Product;
import NguyenQuocGiakhang.CuoiKyWeb2.domain.User;
import NguyenQuocGiakhang.CuoiKyWeb2.repository.CartDetailRepository;
import NguyenQuocGiakhang.CuoiKyWeb2.repository.CartRepository;
import NguyenQuocGiakhang.CuoiKyWeb2.repository.OrderDetailRepository;
import NguyenQuocGiakhang.CuoiKyWeb2.repository.OrderRepository;
import NguyenQuocGiakhang.CuoiKyWeb2.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;

@Service
public class OrderService {
	private final OrderRepository orderRepository;
	private final OrderDetailRepository orderDetailRepository;
	private final CartRepository cartRepository;
	private final CartDetailRepository cartDetailRepository;
	private final ProductRepository productRepository;
	private final UserService userService;

	public OrderService(
			OrderRepository orderRepository,
			OrderDetailRepository orderDetailRepository,
			CartRepository cartRepository,
			CartDetailRepository cartDetailRepository,
			ProductRepository productRepository,
			UserService userService) {
		this.orderRepository = orderRepository;
		this.orderDetailRepository = orderDetailRepository;
		this.cartRepository = cartRepository;
		this.cartDetailRepository = cartDetailRepository;
		this.productRepository = productRepository;
		this.userService = userService;
	}

	public List<Order> fetchOrderByUser(User user) {
		if (user == null) {
			return List.of();
		}
		return this.orderRepository.findByUserIdWithDetails(user.getId());
	}

	public List<Order> fetchAllOrders() {
		return this.orderRepository.findAllWithUser();
	}

	public Optional<Order> fetchOrderById(long id) {
		return this.orderRepository.findByIdWithDetails(id);
	}

	public void deleteOrderById(long id) {
		Optional<Order> orderOptional = this.fetchOrderById(id);
		if (orderOptional.isPresent()) {
			Order order = orderOptional.get();
			List<OrderDetail> orderDetails = order.getOrderDetails();
			if (orderDetails != null) {
				for (OrderDetail orderDetail : orderDetails) {
					this.orderDetailRepository.deleteById(orderDetail.getId());
				}
			}
		}
		this.orderRepository.deleteById(id);
	}

	public void updateOrder(Order order) {
		Optional<Order> orderOptional = this.orderRepository.findById(order.getId());
		if (orderOptional.isPresent()) {
			Order currentOrder = orderOptional.get();
			currentOrder.setStatus(order.getStatus());
			this.orderRepository.save(currentOrder);
		}
	}

	@Transactional
	public void handlePlaceOrder(
			long userId,
			HttpSession session,
			String receiverName,
			String receiverAddress,
			String receiverPhone) {

		User user = this.userService.getUserById(userId);
		if (user == null) {
			return;
		}

		Cart cart = this.cartRepository.findByUser(user);
		if (cart == null) {
			return;
		}

		List<CartDetail> cartDetails = cart.getCartDetails();
		if (cartDetails == null || cartDetails.isEmpty()) {
			return;
		}

		Order order = new Order();
		order.setUser(user);
		order.setReceiverName(receiverName);
		order.setReceiverAddress(receiverAddress);
		order.setReceiverPhone(receiverPhone);
		order.setStatus("PENDING");

		double totalPrice = 0;
		for (CartDetail cd : cartDetails) {
			totalPrice += cd.getPrice() * cd.getQuantity();
		}
		order.setTotalPrice(totalPrice);
		order = this.orderRepository.save(order);

		for (CartDetail cd : cartDetails) {
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrder(order);
			orderDetail.setProduct(cd.getProduct());
			orderDetail.setPrice(cd.getPrice());
			orderDetail.setQuantity(cd.getQuantity());
			this.orderDetailRepository.save(orderDetail);

			Product product = cd.getProduct();
			if (product != null) {
				long newQty = product.getQuantity() - cd.getQuantity();
				product.setQuantity(Math.max(0, newQty));
				product.setSold(product.getSold() + cd.getQuantity());
				this.productRepository.save(product);
			}
		}

		for (CartDetail cd : cartDetails) {
			this.cartDetailRepository.deleteById(cd.getId());
		}

		// Gỡ liên kết User.cart trước khi xóa giỏ (tránh TransientPropertyValueException)
		user.setCart(null);
		this.cartRepository.deleteById(cart.getId());

		if (session != null) {
			session.setAttribute("sum", 0);
		}
	}
}
