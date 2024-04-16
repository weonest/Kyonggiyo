package kyonggiyo.application.port.in.review;


import kyonggiyo.application.port.in.auth.dto.UserInfo;

public interface DeleteReviewUseCase {

    void deleteReview(UserInfo userInfo, Long id);

}
