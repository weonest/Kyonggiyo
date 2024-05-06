package kyonggiyo.application.port.in.review;


import kyonggiyo.auth.domain.vo.UserInfo;

public interface DeleteReviewUseCase {

    void deleteReview(UserInfo userInfo, Long id);

}
