package kyonggiyo.persistence.candidate;

import kyonggiyo.candidate.domain.entity.Candidate;
import kyonggiyo.candidate.domain.vo.Status;
import kyonggiyo.common.exception.GlobalErrorCode;
import kyonggiyo.common.exception.NotFoundException;
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
        return jpaRepository.findAllByStatus(status, pageable);
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
