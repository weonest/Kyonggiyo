package kyonggiyo.persistence.restaurant;

import kyonggiyo.restaurant.domain.entity.Restaurant;
import kyonggiyo.restaurant.domain.vo.RestaurantCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantJpaRepository extends JpaRepository<Restaurant, Long> {

    @Query(value = "SELECT r FROM Restaurant r " +
                   "LEFT JOIN Review rv " +
                   "ON r.id = rv.restaurant.id " +
                   "WHERE r.name LIKE %:keyword% OR rv.content LIKE %:keyword%")
    List<Restaurant> findByNameOrReviewContent(@Param(value = "keyword") String keyword);

    @Query(value = "SELECT r FROM Restaurant r " +
                   "WHERE r.category in :categories")
    List<Restaurant> filterByCategory(@Param(value = "categories") List<RestaurantCategory> categories);

}
