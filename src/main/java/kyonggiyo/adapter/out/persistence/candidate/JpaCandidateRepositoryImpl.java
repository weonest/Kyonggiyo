package kyonggiyo.adapter.out.persistence.candidate;

import kyonggiyo.domain.candidate.Candidate;
import kyonggiyo.domain.candidate.Status;
import kyonggiyo.global.exception.GlobalErrorCode;
import kyonggiyo.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaCandidateRepositoryImpl implements CandidateRepository {

    private final CandidateJpaRepository jpaRepository;

    @Override
    public Candidate getById(Long id) {
        return jpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(GlobalErrorCode.NOT_FOUND_ENTITY_EXCEPTION));
    }

    @Override
    public Slice<Candidate> findAllByStatus(Status status, Pageable pageable) {
        return jpaRepository.findAllByStatus(status);
    }

    @Override
    public Candidate save(Candidate candidate) {
        return jpaRepository.save(candidate);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

}
