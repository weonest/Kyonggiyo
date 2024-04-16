package kyonggiyo.application.service.restaurant;

import kyonggiyo.application.port.in.restaurant.CreateRestaurantUseCase;
import kyonggiyo.application.port.in.restaurant.dto.RestaurantCreateCommand;
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
    public void createRestaurant(RestaurantCreateCommand command) {
        Restaurant restaurant = command.toEntity();
        saveRestaurantPort.save(restaurant);
    }

}
