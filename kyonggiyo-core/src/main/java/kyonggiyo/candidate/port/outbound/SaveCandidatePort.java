package kyonggiyo.candidate.port.outbound;

import kyonggiyo.candidate.domain.entity.Candidate;

public interface SaveCandidatePort {

    Candidate save(Candidate candidate);

}
