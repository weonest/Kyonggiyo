package kyonggiyo.adapter.image.dto;

import jakarta.validation.constraints.Pattern;

public record PresignedUrlRequest(
        @Pattern(regexp = "([0-9a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣\\._-]+.(png|PNG|jp[e]?g|JP[E]?G|svg|SVG))",
        message = "특수문자를 제외한 파일명과 png, jpg, jpeg, svg 확장자를 사용해 주세요.")
        String filename
) {
}
