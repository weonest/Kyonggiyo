package kyonggiyo.adapter.in.web.user;

import kyonggiyo.adapter.in.web.ControllerTest;
import kyonggiyo.adapter.in.web.auth.dto.UserCreateRequst;
import kyonggiyo.domain.auth.Platform;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends ControllerTest {

    @Test
    void 요청정보를_통해_유저를_생성한다() throws Exception{
        // given
        UserCreateRequst request = Instancio.create(UserCreateRequst.class);
        Platform platform = Instancio.create(Platform.class);
        URI expectUri = UriComponentsBuilder
                .fromUriString("www.example.com/oauth/authorize")
                .queryParam("client_id", "{clientId}")
                .queryParam("redirect_uri", "{redirectUri}")
                .queryParam("response_type", "code")
                .build().toUri();

        given(createUserProfileUseCase.createUser(request)).willReturn(platform);
        given(provideAuthCodeUrlUseCase.provideUri(platform)).willReturn(expectUri);

        // when
        ResultActions resultActions = mockMvc.perform(
                post("/api/v1/users/profile")
                        .header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isCreated())
                .andExpect(redirectedUrl(expectUri.toString()));
    }

}
