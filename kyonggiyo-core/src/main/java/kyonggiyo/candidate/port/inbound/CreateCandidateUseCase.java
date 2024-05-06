package kyonggiyo.candidate.port.inbound;

import kyonggiyo.auth.domain.vo.UserInfo;
import kyonggiyo.candidate.dto.CandidateCreateCommand;

public interface CreateCandidateUseCase {

    void createCandidate(UserInfo userInfo, CandidateCreateCommand command);

}
