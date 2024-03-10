package kyonggiyo.domain.candidate;

public enum Status {
    ACCEPTED,
    WAITING,
    ;

    public static Status from(String status) {
        return Status.valueOf(status.toUpperCase());
    }

}
