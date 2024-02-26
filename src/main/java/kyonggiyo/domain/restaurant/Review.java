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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@EqualsAndHashCode
@Table(name = "reviews")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity implements Comparable<Review>{

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

    @Override
    public int compareTo(Review o) {
        if (getCreatedAt().isAfter(o.getCreatedAt())) return -1;
        if (getCreatedAt() == o.getCreatedAt()) return 0;
        return -1;
    }

    public void update(int rating, String content) {
        this.restaurant.getReviews().remove(this);
        this.rating = rating;
        this.content = content;
        this.restaurant.getReviews().add(this);
    }

    private void setRestaurant(Restaurant restaurant) {
        if (this.restaurant != null) {
            this.restaurant.getReviews().remove(this);
        }
        this.restaurant = restaurant;
        this.restaurant.getReviews().add(this);
    }

}
