package kyonggiyo.application.port.in.candidate;

import kyonggiyo.auth.domain.vo.UserInfo;
import kyonggiyo.application.port.in.candidate.dto.CandidateCreateCommand;

public interface CreateCandidateUseCase {

    void createCandidate(UserInfo userInfo, CandidateCreateCommand command);

}
