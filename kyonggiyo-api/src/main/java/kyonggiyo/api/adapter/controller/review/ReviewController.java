package kyonggiyo.api.adapter.controller.review;

import kyonggiyo.api.adapter.controller.review.dto.ReviewCreateRequest;
import kyonggiyo.api.adapter.controller.review.dto.ReviewUpdateRequest;
import kyonggiyo.review.port.inbound.CreateReviewUseCase;
import kyonggiyo.review.port.inbound.DeleteReviewUseCase;
import kyonggiyo.review.port.inbound.UpdateReviewUseCase;
import kyonggiyo.auth.Auth;
import kyonggiyo.auth.domain.vo.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/restaurants/{restaurantId}/reviews")
public class ReviewController {

    private final CreateReviewUseCase createReviewUseCase;
    private final UpdateReviewUseCase updateReviewUseCase;
    private final DeleteReviewUseCase deleteReviewUseCase;

    @PostMapping
    public ResponseEntity<Void> reviewCreate(@Auth UserInfo userInfo,
                                             @PathVariable Long restaurantId,
                                             @RequestBody ReviewCreateRequest request) {
        createReviewUseCase.createReview(userInfo, restaurantId, request.toCommand());
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<Void> reviewUpdate(@Auth UserInfo userInfo,
                                             @PathVariable Long reviewId,
                                             @RequestBody ReviewUpdateRequest request) {
        updateReviewUseCase.updateReview(userInfo, reviewId, request.toCommand());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> reviewDelete(@Auth UserInfo userInfo, @PathVariable Long reviewId) {
        deleteReviewUseCase.deleteReview(userInfo, reviewId);
        return ResponseEntity.ok().build();
    }

}
