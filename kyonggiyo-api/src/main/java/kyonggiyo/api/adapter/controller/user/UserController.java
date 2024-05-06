package kyonggiyo.api.adapter.controller.user;

import kyonggiyo.api.adapter.controller.user.dto.UserCreateRequest;
import kyonggiyo.api.adapter.controller.user.dto.ValidateNicknameRequest;
import kyonggiyo.api.adapter.controller.user.dto.ValidateNicknameResponse;
import kyonggiyo.auth.port.inbound.ProvideAuthCodeUrlUseCase;
import kyonggiyo.user.port.inbound.CreateUserUseCase;
import kyonggiyo.user.port.inbound.ValidateNicknameUseCase;
import kyonggiyo.auth.domain.vo.Platform;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final ProvideAuthCodeUrlUseCase provideAuthCodeUrlUseCase;
    private final ValidateNicknameUseCase validateNicknameUseCase;

    @PostMapping("/profile")
    public ResponseEntity<Void> userCreate(@RequestBody UserCreateRequest request) {
        Platform platform = createUserUseCase.createUser(request.toCommand());
        URI uri = provideAuthCodeUrlUseCase.provideUri(platform);
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/profile/nickname")
    public ResponseEntity<ValidateNicknameResponse> nicknameValidate(ValidateNicknameRequest request) {
        boolean existed = validateNicknameUseCase.existByNickname(request.nickname());
        return ResponseEntity.ok(new ValidateNicknameResponse(!existed));
    }

}
