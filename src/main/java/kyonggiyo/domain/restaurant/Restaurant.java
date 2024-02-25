package kyonggiyo.domain.restaurant;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kyonggiyo.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Builder
    private Restaurant(String name,
                       RestaurantCategory category,
                       String contact,
                       String address,
                       double lat,
                       double lng) {
        this.name = name;
        this.contact = contact;
        this.address = new Address(address, lat, lng);
        this.category = category;
    }

}
