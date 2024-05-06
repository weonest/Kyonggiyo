package kyonggiyo.persistence.candidate;

import kyonggiyo.candidate.domain.entity.Candidate;
import kyonggiyo.candidate.domain.vo.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CandidateRepository {

    Candidate getById(Long id);

    Slice<Candidate> findAllByStatus(Status status, Pageable pageable);

    Candidate save(Candidate candidate);

    void deleteById(Long id);

}
