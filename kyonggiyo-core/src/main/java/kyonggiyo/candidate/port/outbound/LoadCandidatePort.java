package kyonggiyo.candidate.port.outbound;


import kyonggiyo.candidate.domain.entity.Candidate;
import kyonggiyo.candidate.domain.vo.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface LoadCandidatePort {

    Candidate getById(Long id);

    Slice<Candidate> findAllByStatus(Status status, Pageable pageable);

}
