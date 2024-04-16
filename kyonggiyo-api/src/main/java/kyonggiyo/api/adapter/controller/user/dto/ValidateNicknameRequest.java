package kyonggiyo.api.adapter.controller.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ValidateNicknameRequest(
        @NotNull
        @Size(min = 1, max=10, message = "닉네임이 너무 길거나 짧습니다.")
        @Pattern(regexp = "^[a-zA-Z0-9가-힣]+$", message = "닉네임은 숫자, 한글, 영어만 가능합니다.")
        String nickname
) {
}
