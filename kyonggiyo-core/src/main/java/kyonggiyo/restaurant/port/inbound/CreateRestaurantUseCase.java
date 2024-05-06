package kyonggiyo.restaurant.port.inbound;


import kyonggiyo.restaurant.dto.RestaurantCreateCommand;

public interface CreateRestaurantUseCase {

    void createRestaurant(RestaurantCreateCommand command);

}
