package kyonggiyo.domain.restaurant;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kyonggiyo.domain.BaseEntity;
import kyonggiyo.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "reviewes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;

    private String content;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User reviewer;

    @Builder
    private Review(int rating,
                   String content,
                   Restaurant restaurant,
                   User reviewer) {
        this.rating = rating;
        this.content = content;
        setRestaurant(restaurant);
        this.reviewer = reviewer;
    }

    private void setRestaurant(Restaurant restaurant) {
        if (this.restaurant != null) {
            this.restaurant.getReviews().remove(this);
        }
        this.restaurant = restaurant;
        restaurant.getReviews().add(this);
    }

}
