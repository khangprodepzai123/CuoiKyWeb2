package NguyenQuocGiakhang.CuoiKyWeb2.service;

import java.util.List;

import org.springframework.stereotype.Service;

import NguyenQuocGiakhang.CuoiKyWeb2.domain.Order;
import NguyenQuocGiakhang.CuoiKyWeb2.domain.User;
import NguyenQuocGiakhang.CuoiKyWeb2.repository.OrderRepository;

@Service
public class OrderService {
	private final OrderRepository orderRepository;

	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	public List<Order> fetchOrderByUser(User user) {
		return this.orderRepository.findByUser(user);
	}
}
