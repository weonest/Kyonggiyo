package kyonggiyo.candidate.port.inbound;

import kyonggiyo.auth.domain.vo.UserInfo;
import kyonggiyo.candidate.dto.CandidateResponse;
import kyonggiyo.candidate.domain.vo.Status;
import kyonggiyo.common.response.SliceResponse;

public interface LoadCandidateUseCase {

    SliceResponse<CandidateResponse> findAllByStatus(UserInfo userInfo, Status status, int page);


}
