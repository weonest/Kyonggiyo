package kyonggiyo.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import kyonggiyo.adapter.in.web.auth.AuthController;
import kyonggiyo.adapter.in.web.candidate.CandidateController;
import kyonggiyo.adapter.in.web.restaurant.RestaurantController;
import kyonggiyo.adapter.in.web.restaurant.ReviewController;
import kyonggiyo.adapter.in.web.user.UserController;
import kyonggiyo.application.port.in.auth.OAuthLoginUseCase;
import kyonggiyo.application.port.in.auth.OAuthLogoutUseCase;
import kyonggiyo.application.port.in.auth.ProvideAuthCodeUrlUseCase;
import kyonggiyo.application.port.in.candidate.CreateCandidateUseCase;
import kyonggiyo.application.port.in.candidate.FindCandidateUseCase;
import kyonggiyo.application.port.in.restaurant.CreateRestaurantUseCase;
import kyonggiyo.application.port.in.restaurant.CreateReviewUseCase;
import kyonggiyo.application.port.in.restaurant.GetRestaurantUseCase;
import kyonggiyo.application.port.in.restaurant.UpdateReviewUseCase;
import kyonggiyo.application.port.in.user.CreateUserProfileUseCase;
import kyonggiyo.application.service.auth.TokenService;
import kyonggiyo.domain.auth.util.PlatformConverter;
import kyonggiyo.domain.candidate.util.StatusConverter;
import kyonggiyo.global.auth.AuthContext;
import kyonggiyo.global.auth.AuthorizedArgumentResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest({
        AuthContext.class,
        AuthController.class,
        CandidateController.class,
        RestaurantController.class,
        ReviewController.class,
        UserController.class,
})
@AutoConfigureWebMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public abstract class ControllerTest {

    protected static final String BEARER_TOKEN = "Bearer AccessToken";
    protected static final String REFRESH_TOKEN = "RefreshToken";

    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected GenericConversionService genericConversionService;
    @MockBean
    protected ProvideAuthCodeUrlUseCase provideAuthCodeUrlUseCase;
    @MockBean
    protected OAuthLoginUseCase oAuthLoginUseCase;
    @MockBean
    protected OAuthLogoutUseCase oAuthLogoutUseCase;
    @MockBean
    protected TokenService tokenService;
    @MockBean
    protected CreateCandidateUseCase createCandidateUseCase;
    @MockBean
    protected FindCandidateUseCase findCandidateUseCase;
    @MockBean
    protected CreateRestaurantUseCase createRestaurantUseCase;
    @MockBean
    protected GetRestaurantUseCase getRestaurantUseCase;
    @MockBean
    protected CreateReviewUseCase createReviewUseCase;
    @MockBean
    protected UpdateReviewUseCase updateReviewUseCase;
    @MockBean
    protected CreateUserProfileUseCase createUserProfileUseCase;

    @BeforeEach
    void setUp(WebApplicationContext context) {
        genericConversionService.addConverter(new StatusConverter());
        genericConversionService.addConverter(new PlatformConverter());
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

}
