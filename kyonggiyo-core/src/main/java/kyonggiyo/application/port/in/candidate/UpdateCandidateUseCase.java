package kyonggiyo.application.port.in.candidate;

import kyonggiyo.application.port.in.candidate.dto.CandidateUpdateCommand;

public interface UpdateCandidateUseCase {

    void updateCandidate(Long candidateId, CandidateUpdateCommand command);

}
