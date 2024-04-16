package kyonggiyo.application.service.candidate;

import kyonggiyo.application.port.in.candidate.AcceptCandidateUseCase;
import kyonggiyo.application.port.in.candidate.CreateCandidateUseCase;
import kyonggiyo.application.port.in.candidate.DeleteCandidateUseCase;
import kyonggiyo.application.port.in.candidate.UpdateCandidateUseCase;
import kyonggiyo.application.port.in.candidate.dto.CandidateCreateCommand;
import kyonggiyo.application.port.in.candidate.dto.CandidateUpdateCommand;
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
    public void createCandidate(UserInfo userInfo, CandidateCreateCommand command) {
        Candidate candidate = command.toEntity(userInfo.userId());
        saveCandidatePort.save(candidate);
    }

    @Override
    public void updateCandidate(Long candidateId, CandidateUpdateCommand command) {
        Candidate candidate = loadCandidatePort.getById(candidateId);
        candidate.updateName(command.name());
        candidate.updateCategory(command.category());
        candidate.updateContact(command.contact());
        candidate.updateAddress(command.address(), command.lat(), command.lng());
        candidate.updateReason(command.reason());
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
