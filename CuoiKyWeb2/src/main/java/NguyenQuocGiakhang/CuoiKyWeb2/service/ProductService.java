package NguyenQuocGiakhang.CuoiKyWeb2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import NguyenQuocGiakhang.CuoiKyWeb2.domain.Product;
import NguyenQuocGiakhang.CuoiKyWeb2.repository.ProductRepository;

@Service
public class ProductService {
	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public List<Product> fetchProducts() {
		return this.productRepository.findAll();
	}

	public Optional<Product> fetchProductById(long id) {
		return this.productRepository.findById(id);
	}
}
