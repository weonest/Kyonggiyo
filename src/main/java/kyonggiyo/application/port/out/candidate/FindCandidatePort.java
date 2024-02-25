package kyonggiyo.application.port.out.candidate;


import kyonggiyo.domain.candidate.Candidate;
import kyonggiyo.domain.candidate.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface FindCandidatePort {

    Slice<Candidate> findAllByStatus(Status status, Pageable pageable);

}
