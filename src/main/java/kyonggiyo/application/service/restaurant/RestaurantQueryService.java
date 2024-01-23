package kyonggiyo.application.service.restaurant;

import kyonggiyo.application.port.in.restaurant.GetRestaurantQuery;
import kyonggiyo.application.port.out.restaurant.GetRestaurantPort;
import kyonggiyo.domain.restaurant.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantQueryService implements GetRestaurantQuery {

    private final GetRestaurantPort getRestaurantPort;

    @Override
    public Restaurant get(Long id) {
        return getRestaurantPort.get(id);
    }

}
