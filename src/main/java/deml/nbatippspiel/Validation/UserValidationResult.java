package deml.nbatippspiel.Validation;

public class UserValidationResult<B, S> {
    private final B isError;
    private final S errorMessage;

    public UserValidationResult(final B b, final S s) {
        this.isError = b;
        this.errorMessage = s;
    }

    public B getIsError() {
        return isError;
    }

    public S getErrorMessages() {
        return errorMessage;
    }
}
