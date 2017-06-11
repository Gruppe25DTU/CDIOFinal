package logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {
	private Pattern form;
	private Matcher testEmail;

	private static final String EMAIL_Form =
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	public EmailValidator() {
		form = Pattern.compile(EMAIL_Form);
	}

	public boolean validEmail(final String email) {

		testEmail = form.matcher(email);
		return testEmail.matches();
	}
	
	
}

