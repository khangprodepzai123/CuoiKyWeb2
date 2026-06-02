package NguyenQuocGiakhang.CuoiKyWeb2.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import NguyenQuocGiakhang.CuoiKyWeb2.domain.Order;
import NguyenQuocGiakhang.CuoiKyWeb2.service.OrderService;

@Controller
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping("/admin/order")
	public String getOrderPage(Model model) {
		List<Order> orders = this.orderService.fetchAllOrders();
		model.addAttribute("orders", orders);
		return "admin/order/show";
	}

	@GetMapping("/admin/order/{id}")
	public String getOrderDetailPage(Model model, @PathVariable long id) {
		Optional<Order> orderOptional = this.orderService.fetchOrderById(id);
		if (orderOptional.isEmpty()) {
			return "redirect:/admin/order";
		}
		Order order = orderOptional.get();
		model.addAttribute("order", order);
		model.addAttribute("id", id);
		model.addAttribute("orderDetails", order.getOrderDetails());
		return "admin/order/detail";
	}

	@GetMapping("/admin/order/delete/{id}")
	public String getDeleteOrderPage(Model model, @PathVariable long id) {
		model.addAttribute("id", id);
		model.addAttribute("newOrder", new Order());
		return "admin/order/delete";
	}

	@PostMapping("/admin/order/delete")
	public String postDeleteOrder(@ModelAttribute("newOrder") Order order) {
		this.orderService.deleteOrderById(order.getId());
		return "redirect:/admin/order";
	}

	@GetMapping("/admin/order/update/{id}")
	public String getUpdateOrderPage(Model model, @PathVariable long id) {
		Optional<Order> currentOrder = this.orderService.fetchOrderById(id);
		if (currentOrder.isEmpty()) {
			return "redirect:/admin/order";
		}
		model.addAttribute("newOrder", currentOrder.get());
		return "admin/order/update";
	}

	@PostMapping("/admin/order/update")
	public String handleUpdateOrder(@ModelAttribute("newOrder") Order order) {
		this.orderService.updateOrder(order);
		return "redirect:/admin/order";
	}
}
