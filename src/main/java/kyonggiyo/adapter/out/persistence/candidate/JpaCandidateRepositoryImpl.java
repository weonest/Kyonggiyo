package kyonggiyo.adapter.out.persistence.candidate;

import kyonggiyo.domain.candidate.Candidate;
import kyonggiyo.domain.candidate.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaCandidateRepositoryImpl implements CandidateRepository {

    private final CandidateJpaRepository jpaRepository;

    @Override
    public Slice<Candidate> findAllByStatus(Status status, Pageable pageable) {
        return jpaRepository.findAllByStatus(status);
    }

    @Override
    public Candidate save(Candidate candidate) {
        return jpaRepository.save(candidate);
    }
    
}
