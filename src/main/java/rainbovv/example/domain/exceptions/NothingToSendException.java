package rainbovv.example.domain.exceptions;

public class NothingToSendException extends Exception {

	public NothingToSendException() {
		super("Nothing to send!");
	}
}
