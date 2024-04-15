package kyonggiyo.application.port.in.candidate;

import kyonggiyo.adapter.in.web.candidate.dto.CandidateUpdateRequest;

public interface UpdateCandidateUseCase {

    void updateCandidate(Long candidateId, CandidateUpdateRequest request);

}
