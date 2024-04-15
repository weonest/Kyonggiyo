package kyonggiyo.adapter.in.web.user;

import kyonggiyo.adapter.in.web.auth.dto.UserCreateRequst;
import kyonggiyo.adapter.in.web.user.dto.ValidateNicknameRequest;
import kyonggiyo.adapter.in.web.user.dto.ValidateNicknameResponse;
import kyonggiyo.application.port.in.auth.ProvideAuthCodeUrlUseCase;
import kyonggiyo.application.port.in.user.CreateUserUseCase;
import kyonggiyo.application.port.in.user.ValidateNicknameUseCase;
import kyonggiyo.domain.auth.Platform;
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
    public ResponseEntity<Void> userCreate(@RequestBody UserCreateRequst request) {
        Platform platform = createUserUseCase.createUser(request);
        URI uri = provideAuthCodeUrlUseCase.provideUri(platform);
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/profile/nickname")
    public ResponseEntity<ValidateNicknameResponse> nicknameValidate(ValidateNicknameRequest request) {
        boolean existed = validateNicknameUseCase.existByNickname(request.nickname());
        return ResponseEntity.ok(new ValidateNicknameResponse(!existed));
    }

}
