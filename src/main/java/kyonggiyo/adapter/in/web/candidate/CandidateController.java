package kyonggiyo.adapter.in.web.candidate;

import kyonggiyo.adapter.in.web.candidate.dto.CandidateCreateRequest;
import kyonggiyo.adapter.in.web.candidate.dto.CandidateResponse;
import kyonggiyo.application.port.in.candidate.CreateCandidateUseCase;
import kyonggiyo.application.port.in.candidate.FindCandidateUseCase;
import kyonggiyo.domain.candidate.Status;
import kyonggiyo.global.auth.UserInfo;
import kyonggiyo.global.response.SliceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/candidates")
public class CandidateController {

    private static final int DEFAULT_PAGE_SIZE = 10;

    private final CreateCandidateUseCase createCandidateUseCase;
    private final FindCandidateUseCase findCandidateUseCase;

    @PostMapping
    public ResponseEntity<Void> createCandidate(UserInfo userInfo, @RequestBody CandidateCreateRequest request) {
        createCandidateUseCase.createCandidate(userInfo, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<SliceResponse> findAllCandidatesByStatus(UserInfo userInfo,
                                                                   @RequestParam Status status,
                                                                   @RequestParam(required = false, defaultValue = "0", value = "page") int page) {
        Pageable pageable = PageRequest.of(page, DEFAULT_PAGE_SIZE);
        SliceResponse<CandidateResponse> response = findCandidateUseCase.findAllByStatus(userInfo, status, pageable);
        return ResponseEntity.ok(response);
    }

}
