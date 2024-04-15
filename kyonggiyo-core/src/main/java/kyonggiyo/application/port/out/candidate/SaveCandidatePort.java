package kyonggiyo.application.port.out.candidate;

import kyonggiyo.domain.candidate.Candidate;

public interface SaveCandidatePort {

    Candidate save(Candidate candidate);

}
