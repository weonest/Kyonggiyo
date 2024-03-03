package kyonggiyo.application.service.candidate;

import kyonggiyo.adapter.in.web.candidate.dto.CandidateResponse;
import kyonggiyo.application.port.in.candidate.FindCandidateUseCase;
import kyonggiyo.application.port.out.candidate.FindCandidatePort;
import kyonggiyo.application.service.ServiceTest;
import kyonggiyo.domain.candidate.Candidate;
import kyonggiyo.domain.candidate.Status;
import kyonggiyo.domain.user.Role;
import kyonggiyo.fixture.CandidateFixtures;
import kyonggiyo.global.auth.UserInfo;
import kyonggiyo.global.response.SliceResponse;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;


@ContextConfiguration(classes = CandidateQueryService.class)
class CandidateQueryServiceTest extends ServiceTest {

    @Autowired
    private FindCandidateUseCase findCandidateUseCase;

    @MockBean
    private FindCandidatePort findCandidatePort;

    @Test
    void 맛집_후보군의_상태에_따라_조회할_수_있다() {
        // given
        UserInfo userInfo = new UserInfo(1L, Role.USER);
        Status status = Instancio.create(Status.class);
        Pageable pageable = Instancio.create(Pageable.class);

        List<Candidate> candidates = getCandidateListFixture(status);
        SliceImpl<Candidate> slice = new SliceImpl<>(candidates);

        given(findCandidatePort.findAllByStatus(status, pageable)).willReturn(slice);

        // when
        SliceResponse<CandidateResponse> result = findCandidateUseCase.findAllByStatus(userInfo, status, pageable);

        // then
        assertThat(result.data()).hasSameSizeAs(candidates);
    }

    private List<Candidate> getCandidateListFixture(Status status) {
        if (status == Status.WAITING) return CandidateFixtures.generateWaitingCandidateList();
        if (status == Status.ACCEPTED) return CandidateFixtures.generateAcceptedCandidateList();
        return CandidateFixtures.generateRejectedCandidateList();
    }

}
