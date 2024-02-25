package kyonggiyo.adapter.out.persistence.candidate;

import kyonggiyo.application.port.out.candidate.FindCandidatePort;
import kyonggiyo.application.port.out.candidate.SaveCandidatePort;
import kyonggiyo.domain.candidate.Candidate;
import kyonggiyo.domain.candidate.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CandidatePersistenceAdapter implements FindCandidatePort, SaveCandidatePort {

    private final CandidateRepository repository;

    @Override
    public Slice<Candidate> findAllByStatus(Status status, Pageable pageable) {
        return repository.findAllByStatus(status, pageable);
    }

    @Override
    public Candidate save(Candidate candidate) {
        return repository.save(candidate);
    }

}
