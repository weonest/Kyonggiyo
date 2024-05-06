package kyonggiyo.application.service.candidate;

import kyonggiyo.auth.domain.vo.UserInfo;
import kyonggiyo.application.port.in.candidate.LoadCandidateUseCase;
import kyonggiyo.application.port.in.candidate.dto.CandidateResponse;
import kyonggiyo.application.port.out.candidate.LoadCandidatePort;
import kyonggiyo.domain.candidate.Candidate;
import kyonggiyo.domain.candidate.Status;
import kyonggiyo.common.response.SliceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CandidateQueryService implements LoadCandidateUseCase {

    private static final int DEFAULT_PAGE_SIZE = 10;

    private final LoadCandidatePort loadCandidatePort;

    @Override
    public SliceResponse<CandidateResponse> findAllByStatus(UserInfo userInfo, Status status, int page) {
        Pageable pageable = PageRequest.of(page, DEFAULT_PAGE_SIZE);
        Slice<Candidate> candidates = loadCandidatePort.findAllByStatus(status, pageable);
        return SliceResponse.of(candidates.map(CandidateResponse::from));
    }

}
