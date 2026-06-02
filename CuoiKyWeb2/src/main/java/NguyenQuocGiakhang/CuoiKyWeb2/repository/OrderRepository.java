package NguyenQuocGiakhang.CuoiKyWeb2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import NguyenQuocGiakhang.CuoiKyWeb2.domain.Order;
import NguyenQuocGiakhang.CuoiKyWeb2.domain.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);

    @Query("SELECT DISTINCT o FROM Order o "
            + "LEFT JOIN FETCH o.orderDetails od "
            + "LEFT JOIN FETCH od.product "
            + "WHERE o.user.id = :userId ORDER BY o.id DESC")
    List<Order> findByUserIdWithDetails(@Param("userId") long userId);
}
