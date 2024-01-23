package kyonggiyo.application.port.in.restaurant;


import kyonggiyo.domain.restaurant.Restaurant;

public interface GetRestaurantQuery {

    Restaurant get(Long id);

}
