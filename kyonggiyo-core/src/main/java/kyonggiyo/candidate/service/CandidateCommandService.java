package kyonggiyo.candidate.service;

import kyonggiyo.auth.domain.vo.UserInfo;
import kyonggiyo.candidate.port.inbound.AcceptCandidateUseCase;
import kyonggiyo.candidate.port.inbound.CreateCandidateUseCase;
import kyonggiyo.candidate.port.inbound.DeleteCandidateUseCase;
import kyonggiyo.candidate.port.inbound.UpdateCandidateUseCase;
import kyonggiyo.candidate.dto.CandidateCreateCommand;
import kyonggiyo.candidate.dto.CandidateUpdateCommand;
import kyonggiyo.candidate.port.outbound.DeleteCandidatePort;
import kyonggiyo.candidate.port.outbound.LoadCandidatePort;
import kyonggiyo.candidate.port.outbound.SaveCandidatePort;
import kyonggiyo.application.port.out.restaurant.SaveRestaurantPort;
import kyonggiyo.candidate.domain.entity.Candidate;
import kyonggiyo.user.domain.vo.Role;
import kyonggiyo.common.exception.ForbiddenException;
import kyonggiyo.common.exception.GlobalErrorCode;
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
