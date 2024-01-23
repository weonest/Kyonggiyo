package kyonggiyo.application.service.restaurant;

import kyonggiyo.adapter.in.web.restaurant.dto.RestaurantCreateRequest;
import kyonggiyo.application.port.in.restaurant.CreateRestaurantUseCase;
import kyonggiyo.application.port.out.restaurant.SaveRestaurantPort;
import kyonggiyo.domain.restaurant.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantCommandService implements CreateRestaurantUseCase {

    private final SaveRestaurantPort saveRestaurantPort;

    @Override
    public void create(RestaurantCreateRequest request) {
        Restaurant restaurant = request.toEntity();
        saveRestaurantPort.save(restaurant);
    }

}
