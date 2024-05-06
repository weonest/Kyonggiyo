package kyonggiyo.candidate.port.inbound;


import kyonggiyo.auth.domain.vo.UserInfo;

public interface DeleteCandidateUseCase {

    void deleteCandidate(UserInfo userInfo, Long id) ;

}
