package kyonggiyo.review.port.inbound;


import kyonggiyo.auth.domain.vo.UserInfo;

public interface DeleteReviewUseCase {

    void deleteReview(UserInfo userInfo, Long id);

}
