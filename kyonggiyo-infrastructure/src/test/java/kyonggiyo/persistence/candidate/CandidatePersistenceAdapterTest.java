package kyonggiyo.persistence.candidate;

import kyonggiyo.persistence.AdapterTest;
import kyonggiyo.domain.candidate.Candidate;
import kyonggiyo.domain.candidate.Status;
import kyonggiyo.fixture.CandidateFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


class CandidatePersistenceAdapterTest extends AdapterTest {

    @Autowired
    private CandidateJpaRepository candidateJpaRepository;

    @Autowired
    private CandidatePersistenceAdapter candidatePersistenceAdapter;

    @Test
    void DB에_저장된_후보들을_상태로_조회할_수_있다() {
        // given
        List<Candidate> waiting = CandidateFixtures.generateDomainListWithStatusAndSize(Status.WAITING, 5);
        List<Candidate> accepted = CandidateFixtures.generateDomainListWithStatusAndSize(Status.ACCEPTED, 9);

        List<Candidate> candidates = Stream.of(waiting, accepted)
                .flatMap(Collection::stream)
                .toList();

        candidateJpaRepository.saveAll(candidates);

        entityManager.flush();
        entityManager.clear();

        // when
        Slice<Candidate> waitingResult = candidatePersistenceAdapter.findAllByStatus(Status.WAITING, PageRequest.of(0, 10));
        Slice<Candidate> acceptedResult = candidatePersistenceAdapter.findAllByStatus(Status.ACCEPTED, PageRequest.of(0, 10));

        // then
        Assertions.assertThat(waitingResult).hasSameSizeAs(waiting);
        Assertions.assertThat(acceptedResult).hasSameSizeAs(accepted);
    }

}
