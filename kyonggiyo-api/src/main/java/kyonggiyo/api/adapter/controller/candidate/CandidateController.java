package kyonggiyo.api.adapter.controller.candidate;

import kyonggiyo.api.adapter.controller.candidate.dto.CandidateCreateRequest;
import kyonggiyo.application.port.in.candidate.dto.CandidateResponse;
import kyonggiyo.api.adapter.controller.candidate.dto.CandidateUpdateRequest;
import kyonggiyo.application.port.in.candidate.AcceptCandidateUseCase;
import kyonggiyo.application.port.in.candidate.CreateCandidateUseCase;
import kyonggiyo.application.port.in.candidate.DeleteCandidateUseCase;
import kyonggiyo.application.port.in.candidate.LoadCandidateUseCase;
import kyonggiyo.application.port.in.candidate.UpdateCandidateUseCase;
import kyonggiyo.domain.candidate.Status;
import kyonggiyo.auth.Admin;
import kyonggiyo.auth.Auth;
import kyonggiyo.application.port.in.auth.dto.UserInfo;
import kyonggiyo.common.response.SliceResponse;
import lombok.RequiredArgsConstructor;
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

    private final CreateCandidateUseCase createCandidateUseCase;
    private final AcceptCandidateUseCase acceptCandidateUseCase;
    private final UpdateCandidateUseCase updateCandidateUseCase;
    private final LoadCandidateUseCase loadCandidateUseCase;
    private final DeleteCandidateUseCase deleteCandidateUseCase;

    @PostMapping
    public ResponseEntity<Void> candidateCreate(@Auth UserInfo userInfo, @RequestBody CandidateCreateRequest request) {
        createCandidateUseCase.createCandidate(userInfo, request.toCommand());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<SliceResponse<CandidateResponse>> candidateListByStatus(@Auth UserInfo userInfo,
                                                                                  @RequestParam Status status,
                                                                                  @RequestParam(required = false, defaultValue = "0", value = "page") int page) {
        SliceResponse<CandidateResponse> response = loadCandidateUseCase.findAllByStatus(userInfo, status, page);
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

        updateCandidateUseCase.updateCandidate(candidateId, request.toCommand());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{candidateId}")
    public ResponseEntity<Void> candidateDelete(@Auth UserInfo userInfo, @PathVariable Long candidateId) {
        deleteCandidateUseCase.deleteCandidate(userInfo, candidateId);
        return ResponseEntity.ok().build();
    }

}
