package kyonggiyo.adapter.in.web.candidate;

import kyonggiyo.adapter.in.web.candidate.dto.CandidateCreateRequest;
import kyonggiyo.adapter.in.web.candidate.dto.CandidateResponse;
import kyonggiyo.application.port.in.candidate.CreateCandidateUseCase;
import kyonggiyo.application.port.in.candidate.FindCandidateUseCase;
import kyonggiyo.domain.candidate.Status;
import kyonggiyo.global.auth.UserInfo;
import kyonggiyo.global.response.SliceResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/candidates/")
public class CandidateController {

    private CreateCandidateUseCase createCandidateUseCase;
    private FindCandidateUseCase findCandidateUseCase;

    @PostMapping
    public ResponseEntity<Void> createCandidate(UserInfo userInfo, CandidateCreateRequest request) {
        createCandidateUseCase.createCandidate(userInfo, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<SliceResponse<CandidateResponse>> findAllCandidatesByStatus(@RequestParam Status status,
                                                                                      @PageableDefault Pageable pageable) {
        SliceResponse<CandidateResponse> response = findCandidateUseCase.findAllByStatus(status, pageable);
        return ResponseEntity.ok(response);
    }

}
