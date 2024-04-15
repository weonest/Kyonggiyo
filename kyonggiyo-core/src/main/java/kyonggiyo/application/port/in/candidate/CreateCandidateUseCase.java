package kyonggiyo.application.port.in.candidate;

import kyonggiyo.adapter.in.web.candidate.dto.CandidateCreateRequest;
import kyonggiyo.global.auth.UserInfo;

public interface CreateCandidateUseCase {

    void createCandidate(UserInfo userInfo, CandidateCreateRequest request);

}
