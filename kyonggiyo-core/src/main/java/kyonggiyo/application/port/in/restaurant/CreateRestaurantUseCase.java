package kyonggiyo.application.port.in.restaurant;


import kyonggiyo.application.port.in.restaurant.dto.RestaurantCreateCommand;

public interface CreateRestaurantUseCase {

    void createRestaurant(RestaurantCreateCommand command);

}
