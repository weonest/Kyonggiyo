package kyonggiyo.application.service.candidate;

import kyonggiyo.adapter.in.web.candidate.dto.CandidateResponse;
import kyonggiyo.application.port.in.candidate.FindCandidateUseCase;
import kyonggiyo.application.port.out.candidate.FindCandidatePort;
import kyonggiyo.domain.candidate.Candidate;
import kyonggiyo.domain.candidate.Status;
import kyonggiyo.global.auth.UserInfo;
import kyonggiyo.global.response.SliceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CandidateQueryService implements FindCandidateUseCase {

    private final FindCandidatePort findCandidatePort;

    @Override
    public SliceResponse<CandidateResponse> findAllByStatus(UserInfo userInfo, Status status, Pageable pageable) {
        Slice<Candidate> candidates = findCandidatePort.findAllByStatus(status, pageable);
        return SliceResponse.of(candidates.map(CandidateResponse::from));
    }

}
