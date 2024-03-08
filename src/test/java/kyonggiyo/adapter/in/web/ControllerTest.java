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
import kyonggiyo.application.port.in.candidate.AcceptCandidateUseCase;
import kyonggiyo.application.port.in.candidate.CreateCandidateUseCase;
import kyonggiyo.application.port.in.candidate.DeleteCandidateUseCase;
import kyonggiyo.application.port.in.candidate.FindCandidateUseCase;
import kyonggiyo.application.port.in.candidate.UpdateCandidateUseCase;
import kyonggiyo.application.port.in.restaurant.CreateRestaurantUseCase;
import kyonggiyo.application.port.in.restaurant.review.CreateReviewUseCase;
import kyonggiyo.application.port.in.restaurant.GetRestaurantUseCase;
import kyonggiyo.application.port.in.restaurant.review.DeleteReviewUseCase;
import kyonggiyo.application.port.in.restaurant.review.UpdateReviewUseCase;
import kyonggiyo.application.port.in.user.CreateUserUseCase;
import kyonggiyo.application.service.auth.TokenService;
import kyonggiyo.domain.auth.util.PlatformConverter;
import kyonggiyo.domain.candidate.util.StatusConverter;
import kyonggiyo.global.auth.AuthContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
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
@ExtendWith(RestDocumentationExtension.class)
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
    protected AcceptCandidateUseCase acceptCandidateUseCase;
    @MockBean
    protected UpdateCandidateUseCase updateCandidateUseCase;
    @MockBean
    protected FindCandidateUseCase findCandidateUseCase;
    @MockBean
    protected DeleteCandidateUseCase deleteCandidateUseCase;
    @MockBean
    protected CreateRestaurantUseCase createRestaurantUseCase;
    @MockBean
    protected GetRestaurantUseCase getRestaurantUseCase;
    @MockBean
    protected CreateReviewUseCase createReviewUseCase;
    @MockBean
    protected UpdateReviewUseCase updateReviewUseCase;
    @MockBean
    protected DeleteReviewUseCase deleteReviewUseCase;
    @MockBean
    protected CreateUserUseCase createUserUseCase;

    @BeforeEach
    void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentationContextProvider) {
        genericConversionService.addConverter(new StatusConverter());
        genericConversionService.addConverter(new PlatformConverter());
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentationContextProvider))
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

}
