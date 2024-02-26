package kyonggiyo.fixture;

import kyonggiyo.domain.candidate.Candidate;
import kyonggiyo.domain.candidate.Status;
import org.instancio.Instancio;

import java.util.List;

import static org.instancio.Select.field;

public class CandidateFixtures {

    private CandidateFixtures() {
    }

    public static List<Candidate> generateAcceptedCandidateList() {
        return Instancio.ofList(Candidate.class)
                .set(field(Candidate::getStatus), Status.ACCEPTED)
                .create();
    }

    public static List<Candidate> generateWaitingCandidateList() {
        return Instancio.ofList(Candidate.class)
                .set(field(Candidate::getStatus), Status.WAITING)
                .create();
    }

    public static List<Candidate> generateRejectedCandidateList() {
        return Instancio.ofList(Candidate.class)
                .set(field(Candidate::getStatus), Status.REJECTED)
                .create();
    }

}
