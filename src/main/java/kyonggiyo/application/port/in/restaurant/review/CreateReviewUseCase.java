package kyonggiyo.application.port.in.restaurant.review;

import kyonggiyo.adapter.in.web.restaurant.dto.review.ReviewCreateRequest;
import kyonggiyo.global.auth.UserInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CreateReviewUseCase {

    void createReview(UserInfo userInfo,
                      Long restaurantId,
                      ReviewCreateRequest request,
                      List<MultipartFile> multipartFiles);

}
