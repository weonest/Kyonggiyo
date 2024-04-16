package kyonggiyo.application.port.in.candidate;

import kyonggiyo.application.port.in.candidate.dto.CandidateCreateCommand;
import kyonggiyo.global.auth.UserInfo;

public interface CreateCandidateUseCase {

    void createCandidate(UserInfo userInfo, CandidateCreateCommand command);

}
