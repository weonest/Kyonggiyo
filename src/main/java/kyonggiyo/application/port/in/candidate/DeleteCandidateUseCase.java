package kyonggiyo.application.port.in.candidate;

import kyonggiyo.global.auth.UserInfo;

public interface DeleteCandidateUseCase {

    void deleteCandidate(UserInfo userInfo, Long id) ;

}
