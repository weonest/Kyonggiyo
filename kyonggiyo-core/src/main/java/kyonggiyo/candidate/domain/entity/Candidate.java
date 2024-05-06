package kyonggiyo.candidate.domain.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kyonggiyo.common.entity.BaseEntity;
import kyonggiyo.candidate.domain.vo.Status;
import kyonggiyo.restaurant.domain.vo.Address;
import kyonggiyo.restaurant.domain.entity.Restaurant;
import kyonggiyo.restaurant.domain.vo.RestaurantCategory;
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

    public void updateName(String name) {
        this.name = name;
    }

    public void updateCategory(RestaurantCategory category) {
        this.category = category;
    }

    public void updateContact(String contact) {
        this.contact = contact;
    }

    public void updateAddress(String address, double lat, double lng) {
        this.address = new Address(address, lat, lng);
    }

    public void updateReason(String reason) {
        this.reason = reason;
    }

    public Restaurant toRestaurant() {
        return Restaurant.builder()
                .name(name)
                .category(category)
                .contact(contact)
                .address(address.getAddress())
                .lat(address.getLat())
                .lng(address.getLng())
                .reason(reason)
                .build();
    }

}
