package kyonggiyo.application.service.candidate;

import kyonggiyo.adapter.in.web.candidate.dto.CandidateResponse;
import kyonggiyo.application.port.in.candidate.LoadCandidateUseCase;
import kyonggiyo.application.port.out.candidate.LoadCandidatePort;
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
public class CandidateQueryService implements LoadCandidateUseCase {

    private final LoadCandidatePort loadCandidatePort;

    @Override
    public SliceResponse<CandidateResponse> findAllByStatus(UserInfo userInfo, Status status, Pageable pageable) {
        Slice<Candidate> candidates = loadCandidatePort.findAllByStatus(status, pageable);
        return SliceResponse.of(candidates.map(CandidateResponse::from));
    }

}
