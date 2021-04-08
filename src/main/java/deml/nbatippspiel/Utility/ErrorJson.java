package deml.nbatippspiel.Utility;

public class ErrorJson {

    private final ErrorType errorType;

    public ErrorJson(ErrorType errorType) {
        this.errorType = errorType;
    }

    public String toJson(final String badboi) {
        return new JsonBuilder()
                .addJsonEntry(new JsonBuilder.JsonEntry<>("Error " + this.errorType.errornumber,
                        badboi + " " + this.errorType.errormessage))
                .build();
    }

    public enum ErrorType {
        NOT_KNOWN(400, "; error not known"),
        NOT_FOUND(404, "was not found");

        private final int errornumber;
        private final String errormessage;

        ErrorType(final int errornumber, final String errormessage) {
            this.errornumber = errornumber;
            this.errormessage = errormessage;
        }

        public int getErrornumber() {
            return errornumber;
        }
    }
}
