package kyonggiyo.api.adapter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kyonggiyo.api.adapter.controller.auth.AuthController;
import kyonggiyo.api.adapter.controller.candidate.CandidateController;
import kyonggiyo.api.adapter.controller.image.ImageController;
import kyonggiyo.api.adapter.controller.restaurant.RestaurantController;
import kyonggiyo.api.adapter.controller.review.ReviewController;
import kyonggiyo.api.adapter.controller.user.UserController;
import kyonggiyo.auth.port.inbound.OAuthLoginUseCase;
import kyonggiyo.auth.port.inbound.OAuthLogoutUseCase;
import kyonggiyo.auth.port.inbound.ProvideAuthCodeUrlUseCase;
import kyonggiyo.candidate.port.inbound.AcceptCandidateUseCase;
import kyonggiyo.candidate.port.inbound.CreateCandidateUseCase;
import kyonggiyo.candidate.port.inbound.DeleteCandidateUseCase;
import kyonggiyo.candidate.port.inbound.LoadCandidateUseCase;
import kyonggiyo.candidate.port.inbound.UpdateCandidateUseCase;
import kyonggiyo.image.port.inbound.DeleteImageUseCase;
import kyonggiyo.application.port.in.restaurant.CreateRestaurantUseCase;
import kyonggiyo.review.port.inbound.CreateReviewUseCase;
import kyonggiyo.application.port.in.restaurant.LoadRestaurantUseCase;
import kyonggiyo.review.port.inbound.DeleteReviewUseCase;
import kyonggiyo.review.port.inbound.UpdateReviewUseCase;
import kyonggiyo.user.port.inbound.CreateUserUseCase;
import kyonggiyo.user.port.inbound.ValidateNicknameUseCase;
import kyonggiyo.image.port.outbound.ImageManager;
import kyonggiyo.auth.service.TokenService;
import kyonggiyo.auth.domain.util.PlatformConverter;
import kyonggiyo.candidate.domain.util.StatusConverter;
import kyonggiyo.auth.AuthContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
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
        ImageController.class
})
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
    protected LoadCandidateUseCase loadCandidateUseCase;
    @MockBean
    protected DeleteCandidateUseCase deleteCandidateUseCase;
    @MockBean
    protected CreateRestaurantUseCase createRestaurantUseCase;
    @MockBean
    protected LoadRestaurantUseCase loadRestaurantUseCase;
    @MockBean
    protected ImageManager imageManager;
    @MockBean
    protected CreateReviewUseCase createReviewUseCase;
    @MockBean
    protected UpdateReviewUseCase updateReviewUseCase;
    @MockBean
    protected DeleteReviewUseCase deleteReviewUseCase;
    @MockBean
    protected CreateUserUseCase createUserUseCase;
    @MockBean
    protected ValidateNicknameUseCase validateNicknameUseCase;
    @MockBean
    protected DeleteImageUseCase deleteImageUseCase;

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
