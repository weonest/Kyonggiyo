package kyonggiyo.application.service.candidate;

import kyonggiyo.adapter.in.web.candidate.dto.CandidateCreateRequest;
import kyonggiyo.adapter.in.web.candidate.dto.CandidateUpdateRequest;
import kyonggiyo.application.port.in.candidate.AcceptCandidateUseCase;
import kyonggiyo.application.port.in.candidate.CreateCandidateUseCase;
import kyonggiyo.application.port.in.candidate.DeleteCandidateUseCase;
import kyonggiyo.application.port.in.candidate.UpdateCandidateUseCase;
import kyonggiyo.application.port.out.candidate.DeleteCandidatePort;
import kyonggiyo.application.port.out.candidate.LoadCandidatePort;
import kyonggiyo.application.port.out.candidate.SaveCandidatePort;
import kyonggiyo.application.port.out.restaurant.SaveRestaurantPort;
import kyonggiyo.domain.candidate.Candidate;
import kyonggiyo.domain.user.Role;
import kyonggiyo.global.auth.UserInfo;
import kyonggiyo.global.exception.ForbiddenException;
import kyonggiyo.global.exception.GlobalErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CandidateCommandService implements CreateCandidateUseCase, AcceptCandidateUseCase,
        UpdateCandidateUseCase, DeleteCandidateUseCase {

    private final SaveCandidatePort saveCandidatePort;
    private final SaveRestaurantPort saveRestaurantPort;
    private final LoadCandidatePort loadCandidatePort;
    private final DeleteCandidatePort deleteCandidatePort;

    @Override
    public void createCandidate(UserInfo userInfo, CandidateCreateRequest request) {
        Candidate candidate = request.toEntity(userInfo.userId());
        saveCandidatePort.save(candidate);
    }

    @Override
    public void updateCandidate(Long candidateId, CandidateUpdateRequest request) {
        Candidate candidate = loadCandidatePort.getById(candidateId);
        candidate.updateName(request.name());
        candidate.updateCategory(request.category());
        candidate.updateContact(request.contact());
        candidate.updateAddress(request.address(), request.lat(), request.lng());
        candidate.updateReason(request.reason());
    }

    @Override
    public void acceptCandidate(Long candidateId) {
        Candidate candidate = loadCandidatePort.getById(candidateId);
        candidate.accept();
        saveRestaurantPort.save(candidate.toRestaurant());
    }

    @Override
    public void deleteCandidate(UserInfo userInfo, Long id) {
        Candidate candidate = loadCandidatePort.getById(id);
        if (userInfo.role().equals(Role.ADMIN) || candidate.getRequesterId().equals(userInfo.userId())) {
            deleteCandidatePort.deleteById(id);
            return;
        }
        throw new ForbiddenException(GlobalErrorCode.INVALID_REQUEST_EXCEPTION,
                String.format("유저 식별자 불일치 -> %d", userInfo.userId()));
    }

}
