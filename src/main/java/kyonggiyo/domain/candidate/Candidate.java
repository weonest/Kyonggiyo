package kyonggiyo.domain.candidate;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kyonggiyo.domain.BaseEntity;
import kyonggiyo.domain.restaurant.Address;
import kyonggiyo.domain.restaurant.RestaurantCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "candidates")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Candidate extends BaseEntity {

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

    @Enumerated(EnumType.STRING)
    private Status status = Status.WAITING;

    private Long requesterId;

    @Builder
    private Candidate(String name,
                      RestaurantCategory category,
                      String contact,
                      String address,
                      double lat,
                      double lng,
                      String reason,
                      Long requesterId) {
        this.name = name;
        this.category = category;
        this.contact = contact;
        this.address = new Address(address, lat, lng);
        this.reason = reason;
        this.requesterId = requesterId;
    }

    public void accept() {
        this.status = Status.ACCEPTED;
    }

}
