package kyonggiyo.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import kyonggiyo.adapter.in.web.auth.AuthController;
import kyonggiyo.application.port.in.auth.OAuthLoginUseCase;
import kyonggiyo.application.port.in.auth.OAuthLogoutUseCase;
import kyonggiyo.application.port.in.auth.ProvideAuthCodeUrlUseCase;
import kyonggiyo.application.service.auth.TokenService;
import kyonggiyo.global.auth.AuthContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest({
        AuthContext.class,
        AuthController.class,
})
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public abstract class ControllerTest {

    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected MockMvc mockMvc;
    @MockBean
    protected ProvideAuthCodeUrlUseCase provideAuthCodeUrlUseCase;
    @MockBean
    protected OAuthLoginUseCase oAuthLoginUseCase;
    @MockBean
    protected OAuthLogoutUseCase oAuthLogoutUseCase;
    @MockBean
    protected TokenService tokenService;

    @BeforeEach
    void setUp(WebApplicationContext context) {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

}
