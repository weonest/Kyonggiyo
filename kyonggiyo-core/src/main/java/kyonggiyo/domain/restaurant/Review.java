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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Entity
@Table(name = "reviews")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private Long reviewerId;

    private String reviewerNickname;

    @Builder
    private Review(int rating,
                   String content,
                   Restaurant restaurant,
                   Long reviewerId,
                   String reviewerNickname) {
        this.rating = rating;
        this.content = content;
        setRestaurant(restaurant);
        this.reviewerId = reviewerId;
        this.reviewerNickname = reviewerNickname;
    }

    public void update(int rating, String content) {
        this.rating = rating;
        this.content = content;
    }

    public void deleteReview() {
        this.restaurant.getReviews().remove(this);
        this.restaurant.updateAverageRating();
        this.restaurant = null;
    }

    private void setRestaurant(Restaurant restaurant) {
        if (this.restaurant != null) {
            this.restaurant.getReviews().remove(this);
        }
        this.restaurant = restaurant;
        this.restaurant.getReviews().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review review)) return false;
        return Objects.equals(getId(), review.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
