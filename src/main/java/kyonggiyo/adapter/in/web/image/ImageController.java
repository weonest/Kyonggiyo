package kyonggiyo.adapter.in.web.image;

import kyonggiyo.adapter.in.web.image.dto.ImageDeleteRequest;
import kyonggiyo.adapter.in.web.image.dto.PresignedUrlRequest;
import kyonggiyo.adapter.in.web.image.dto.PresignedUrlResponse;
import kyonggiyo.adapter.out.client.image.ImageManager;
import kyonggiyo.application.port.in.image.DeleteImageUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
public class ImageController {

    private final ImageManager imageManager;
    private final DeleteImageUseCase deleteImageUseCase;

    @PostMapping("/presignedUrl")
    private ResponseEntity<PresignedUrlResponse> presignedUrlGenerate(@RequestBody PresignedUrlRequest request) {
        String presignedUrl = imageManager.generatePresignedUrl(request.filename());
        return ResponseEntity.ok(new PresignedUrlResponse(presignedUrl));
    }

    @DeleteMapping("/{imageId}")
    private ResponseEntity<Void> imageDelete(@RequestBody ImageDeleteRequest request) {
        deleteImageUseCase.deleteById(request);
        return ResponseEntity.noContent().build();
    }

}
