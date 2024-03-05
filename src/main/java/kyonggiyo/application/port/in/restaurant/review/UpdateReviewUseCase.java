package kyonggiyo.application.port.in.restaurant.review;

import kyonggiyo.adapter.in.web.restaurant.dto.review.ReviewUpdateRequest;
import kyonggiyo.global.auth.UserInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UpdateReviewUseCase {

    void updateReview(UserInfo userInfo,
                      Long id,
                      ReviewUpdateRequest request,
                      List<MultipartFile> multipartFiles);

}
