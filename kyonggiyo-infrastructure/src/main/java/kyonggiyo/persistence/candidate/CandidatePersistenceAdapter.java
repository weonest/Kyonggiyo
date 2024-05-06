package kyonggiyo.persistence.candidate;

import kyonggiyo.candidate.port.outbound.DeleteCandidatePort;
import kyonggiyo.candidate.port.outbound.LoadCandidatePort;
import kyonggiyo.candidate.port.outbound.SaveCandidatePort;
import kyonggiyo.candidate.domain.entity.Candidate;
import kyonggiyo.candidate.domain.vo.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CandidatePersistenceAdapter implements LoadCandidatePort, SaveCandidatePort, DeleteCandidatePort {

    private final CandidateRepository repository;

    @Override
    public Candidate getById(Long id) {
        return repository.getById(id);
    }

    @Override
    public Slice<Candidate> findAllByStatus(Status status, Pageable pageable) {
        return repository.findAllByStatus(status, pageable);
    }

    @Override
    public Candidate save(Candidate candidate) {
        return repository.save(candidate);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
