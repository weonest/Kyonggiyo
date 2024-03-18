package kyonggiyo.adapter.in.web.restaurant;

import kyonggiyo.adapter.in.web.restaurant.dto.review.ReviewCreateRequest;
import kyonggiyo.adapter.in.web.restaurant.dto.review.ReviewUpdateRequest;
import kyonggiyo.application.port.in.restaurant.review.CreateReviewUseCase;
import kyonggiyo.application.port.in.restaurant.review.DeleteReviewUseCase;
import kyonggiyo.application.port.in.restaurant.review.UpdateReviewUseCase;
import kyonggiyo.global.auth.Auth;
import kyonggiyo.global.auth.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
                                             ReviewCreateRequest request,
                                             @RequestPart(value = "image", required = false) List<MultipartFile> multipartFiles) {
        createReviewUseCase.createReview(userInfo, restaurantId, request, multipartFiles);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<Void> reviewUpdate(@Auth UserInfo userInfo,
                                             @PathVariable Long reviewId,
                                             @RequestPart ReviewUpdateRequest request,
                                             @RequestPart(value = "image", required = false) List<MultipartFile> multipartFiles) {
        updateReviewUseCase.updateReview(userInfo, reviewId, request, multipartFiles);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> reviewDelete(@Auth UserInfo userInfo, @PathVariable Long reviewId) {
        deleteReviewUseCase.deleteReview(userInfo, reviewId);
        return ResponseEntity.ok().build();
    }

}
