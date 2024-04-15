package kyonggiyo.domain.restaurant;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    private String address;

    private double lat;

    private double lng;

    public Address(String address, double lat, double lng) {
        validateCoordinate(lat, lng);
        this.address = address;
        this.lat = lat;
        this.lng = lng;
    }

    private void validateCoordinate(double lat, double lng) {
        // TODO: 2024-02-25 국내 좌표인지
    }

}
