package kyonggiyo.application.port.in.candidate;

import kyonggiyo.application.port.in.auth.dto.UserInfo;
import kyonggiyo.application.port.in.candidate.dto.CandidateResponse;
import kyonggiyo.domain.candidate.Status;
import kyonggiyo.common.response.SliceResponse;

public interface LoadCandidateUseCase {

    SliceResponse<CandidateResponse> findAllByStatus(UserInfo userInfo, Status status, int page);


}
