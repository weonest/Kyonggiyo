package kyonggiyo.restaurant.domain.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import kyonggiyo.common.entity.BaseEntity;
import kyonggiyo.restaurant.domain.vo.Address;
import kyonggiyo.restaurant.domain.vo.RestaurantCategory;
import kyonggiyo.review.domain.entity.Review;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Entity
@Table(name = "restaurants")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private RestaurantCategory category;

    private String contact;

    @Embedded
    private Address address;

    private String reason;

    @OrderBy("createdAt desc")
    @OneToMany(mappedBy = "restaurant", orphanRemoval = true)
    private Set<Review> reviews = new HashSet<>();

    private float averageRating;

    @Builder
    private Restaurant(String name,
                       RestaurantCategory category,
                       String contact,
                       String address,
                       double lat,
                       double lng,
                       String reason) {
        this.name = name;
        this.contact = contact;
        this.address = new Address(address, lat, lng);
        this.category = category;
        this.reason = reason;
    }

    public void updateAverageRating() {
        float total = 0;
        for (Review review : reviews) {
            total += review.getRating();
        }
        averageRating = Math.round(total / reviews.size() * 10) / 10.0f;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Restaurant that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
