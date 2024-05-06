package kyonggiyo.persistence.candidate;

import kyonggiyo.candidate.domain.entity.Candidate;
import kyonggiyo.candidate.domain.vo.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateJpaRepository extends JpaRepository<Candidate, Long> {

    Slice<Candidate> findAllByStatus(Status status, Pageable pageable);

}
