package kyonggiyo.application.service.candidate;

import kyonggiyo.adapter.in.web.candidate.dto.CandidateCreateRequest;
import kyonggiyo.adapter.in.web.candidate.dto.CandidateResponse;
import kyonggiyo.application.port.in.candidate.CreateCandidateUseCase;
import kyonggiyo.application.port.in.candidate.FindCandidateUseCase;
import kyonggiyo.application.port.out.candidate.FindCandidatePort;
import kyonggiyo.application.port.out.candidate.SaveCandidatePort;
import kyonggiyo.domain.candidate.Candidate;
import kyonggiyo.domain.candidate.Status;
import kyonggiyo.global.auth.UserInfo;
import kyonggiyo.global.response.SliceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CandidateCommandService implements FindCandidateUseCase, CreateCandidateUseCase {

    private FindCandidatePort findCandidatePort;
    private SaveCandidatePort saveCandidatePort;

    @Override
    public SliceResponse<CandidateResponse> findAllByStatus(Status status, Pageable pageable) {
        Slice<Candidate> candidates = findCandidatePort.findAllByStatus(status, pageable);
        Slice<CandidateResponse> candidateResponses = candidates.map(CandidateResponse::from);
        return SliceResponse.from(candidateResponses);
    }

    @Override
    public void createCandidate(UserInfo userInfo, CandidateCreateRequest request) {
        Candidate candidate = request.toEntity(userInfo.userId());
        saveCandidatePort.save(candidate);
    }

}
