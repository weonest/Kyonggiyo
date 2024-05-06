package kyonggiyo.candidate.port.inbound;

import kyonggiyo.candidate.dto.CandidateUpdateCommand;

public interface UpdateCandidateUseCase {

    void updateCandidate(Long candidateId, CandidateUpdateCommand command);

}
