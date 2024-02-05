package kyonggiyo.adapter.in.web.user;

import kyonggiyo.adapter.in.web.auth.dto.CreateUserRequest;
import kyonggiyo.application.port.in.auth.ProvideAuthCodeUrlUseCase;
import kyonggiyo.application.port.in.user.CreateUserUseCase;
import kyonggiyo.domain.auth.Platform;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/")
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final ProvideAuthCodeUrlUseCase provideAuthCodeUrlUseCase;

    @PostMapping("/signup")
    public ResponseEntity<Void> createUser(@RequestBody CreateUserRequest request) {
        Platform platform = createUserUseCase.createUser(request);
        URI uri = provideAuthCodeUrlUseCase.provideUri(platform);
        return ResponseEntity.created(uri).build();
    }

}
