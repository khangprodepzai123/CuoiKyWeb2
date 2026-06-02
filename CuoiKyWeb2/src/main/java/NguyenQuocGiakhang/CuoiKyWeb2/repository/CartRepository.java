package NguyenQuocGiakhang.CuoiKyWeb2.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import NguyenQuocGiakhang.CuoiKyWeb2.domain.Cart;
import NguyenQuocGiakhang.CuoiKyWeb2.domain.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(User user);
}