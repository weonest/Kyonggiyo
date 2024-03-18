package kyonggiyo.application.port.out.candidate;


import kyonggiyo.domain.candidate.Candidate;
import kyonggiyo.domain.candidate.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface LoadCandidatePort {

    Candidate getById(Long id);

    Slice<Candidate> findAllByStatus(Status status, Pageable pageable);

}
