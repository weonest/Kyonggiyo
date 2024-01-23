package kyonggiyo.domain.restaurant;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Address {

    private String address;

    private double x;

    private double y;

}
