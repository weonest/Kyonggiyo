package kyonggiyo.application.port.in.candidate;


import kyonggiyo.auth.domain.vo.UserInfo;

public interface DeleteCandidateUseCase {

    void deleteCandidate(UserInfo userInfo, Long id) ;

}
