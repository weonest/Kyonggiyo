package kyonggiyo.application.port.in.candidate;

import kyonggiyo.adapter.in.web.candidate.dto.CandidateResponse;
import kyonggiyo.domain.candidate.Status;
import kyonggiyo.global.response.SliceResponse;
import org.springframework.data.domain.Pageable;

public interface FindCandidateUseCase {

    SliceResponse<CandidateResponse> findAllByStatus(Status status, Pageable pageable);


}
