package kyonggiyo.adapter.in.web.restaurant;

import kyonggiyo.adapter.in.web.restaurant.dto.ReviewCreateRequest;
import kyonggiyo.adapter.in.web.restaurant.dto.ReviewUpdateRequest;
import kyonggiyo.application.port.in.restaurant.CreateReviewUseCase;
import kyonggiyo.application.port.in.restaurant.UpdateReviewUseCase;
import kyonggiyo.global.auth.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/restaurants/{restaurantId}/reviews")
public class ReviewController {

    private final CreateReviewUseCase createReviewUseCase;
    private final UpdateReviewUseCase updateReviewUseCase;

    @PostMapping
    public ResponseEntity<Void> createReview(UserInfo userInfo,
                                       @PathVariable Long restaurantId,
                                       ReviewCreateRequest request) {
        createReviewUseCase.create(userInfo, restaurantId, request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<Void> updateReview(@PathVariable Long reviewId,
                                             ReviewUpdateRequest request) {
        updateReviewUseCase.update(reviewId, request);
        return ResponseEntity.ok().build();
    }

}
