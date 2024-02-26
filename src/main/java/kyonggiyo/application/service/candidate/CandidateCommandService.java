package kyonggiyo.application.service.candidate;

import kyonggiyo.adapter.in.web.candidate.dto.CandidateCreateRequest;
import kyonggiyo.application.port.in.candidate.CreateCandidateUseCase;
import kyonggiyo.application.port.out.candidate.SaveCandidatePort;
import kyonggiyo.domain.candidate.Candidate;
import kyonggiyo.global.auth.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CandidateCommandService implements CreateCandidateUseCase {

    private final SaveCandidatePort saveCandidatePort;

    @Override
    public void createCandidate(UserInfo userInfo, CandidateCreateRequest request) {
        Candidate candidate = request.toEntity(userInfo.userId());
        saveCandidatePort.save(candidate);
    }

}
