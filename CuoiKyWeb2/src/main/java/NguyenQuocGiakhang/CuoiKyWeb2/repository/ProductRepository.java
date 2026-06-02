package NguyenQuocGiakhang.CuoiKyWeb2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import NguyenQuocGiakhang.CuoiKyWeb2.domain.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
