package kyonggiyo.application.service.candidate;

import kyonggiyo.adapter.in.web.candidate.dto.CandidateResponse;
import kyonggiyo.application.port.in.candidate.FindCandidateUseCase;
import kyonggiyo.application.port.out.candidate.FindCandidatePort;
import kyonggiyo.domain.candidate.Candidate;
import kyonggiyo.domain.candidate.Status;
import kyonggiyo.domain.user.Role;
import kyonggiyo.global.auth.UserInfo;
import kyonggiyo.global.response.SliceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CandidateQueryService implements FindCandidateUseCase {

    private FindCandidatePort findCandidatePort;

    @Override
    public SliceResponse<CandidateResponse> findAllByStatus(UserInfo userInfo, Status status, Pageable pageable) {
        Slice<Candidate> candidates = findCandidatePort.findAllByStatus(status, pageable);
        Slice<CandidateResponse> candidateResponses = candidates.map(CandidateResponse::from);

        // TODO: 2024-02-26 아래 로직은 View에 의존적인 방식인 것 같음 프론트와 재상의 필요
        if (userInfo.role() == Role.USER) {
            return SliceResponse.of(candidateResponses, true);
        }
        return SliceResponse.of(candidateResponses, false);
    }

}
