package kyonggiyo.adapter.out.persistence.candidate;

import kyonggiyo.domain.candidate.Candidate;
import kyonggiyo.domain.candidate.Status;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateJpaRepository extends JpaRepository<Candidate, Long> {

    Slice<Candidate> findAllByStatus(Status status);

}
