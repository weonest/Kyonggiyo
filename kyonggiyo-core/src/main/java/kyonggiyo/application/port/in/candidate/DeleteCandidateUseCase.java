package kyonggiyo.application.port.in.candidate;


import kyonggiyo.application.port.in.auth.dto.UserInfo;

public interface DeleteCandidateUseCase {

    void deleteCandidate(UserInfo userInfo, Long id) ;

}
