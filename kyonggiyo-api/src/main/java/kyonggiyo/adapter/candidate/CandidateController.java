package kyonggiyo.adapter.candidate;

import kyonggiyo.adapter.in.web.candidate.dto.CandidateCreateRequest;
import kyonggiyo.adapter.in.web.candidate.dto.CandidateResponse;
import kyonggiyo.adapter.in.web.candidate.dto.CandidateUpdateRequest;
import kyonggiyo.application.port.in.candidate.AcceptCandidateUseCase;
import kyonggiyo.application.port.in.candidate.CreateCandidateUseCase;
import kyonggiyo.application.port.in.candidate.DeleteCandidateUseCase;
import kyonggiyo.application.port.in.candidate.LoadCandidateUseCase;
import kyonggiyo.application.port.in.candidate.UpdateCandidateUseCase;
import kyonggiyo.domain.candidate.Status;
import kyonggiyo.global.auth.Admin;
import kyonggiyo.global.auth.Auth;
import kyonggiyo.global.auth.UserInfo;
import kyonggiyo.global.response.SliceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final AcceptCandidateUseCase acceptCandidateUseCase;
    private final UpdateCandidateUseCase updateCandidateUseCase;
    private final LoadCandidateUseCase loadCandidateUseCase;
    private final DeleteCandidateUseCase deleteCandidateUseCase;

    @PostMapping
    public ResponseEntity<Void> candidateCreate(@Auth UserInfo userInfo, @RequestBody CandidateCreateRequest request) {
        createCandidateUseCase.createCandidate(userInfo, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<SliceResponse<CandidateResponse>> candidateListByStatus(@Auth UserInfo userInfo,
                                                                   @RequestParam Status status,
                                                                   @RequestParam(required = false, defaultValue = "0", value = "page") int page) {
        Pageable pageable = PageRequest.of(page, DEFAULT_PAGE_SIZE);
        SliceResponse<CandidateResponse> response = loadCandidateUseCase.findAllByStatus(userInfo, status, pageable);
        return ResponseEntity.ok(response);
    }

    @Admin
    @PostMapping("/{candidateId}")
    public ResponseEntity<Void> candidateAccept(@PathVariable Long candidateId) {
        acceptCandidateUseCase.acceptCandidate(candidateId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{candidateId}")
    public ResponseEntity<Void> candidateUpdate(@PathVariable Long candidateId, @RequestBody CandidateUpdateRequest request) {
        updateCandidateUseCase.updateCandidate(candidateId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{candidateId}")
    public ResponseEntity<Void> candidateDelete(@Auth UserInfo userInfo, @PathVariable Long candidateId) {
        deleteCandidateUseCase.deleteCandidate(userInfo, candidateId);
        return ResponseEntity.ok().build();
    }

}
