package grace.assignment3.CallHistory;

/**
 * Wrapper class for incoming calls, saving id, phone number and date & time of
 * incoming call
 * Source: Julia Bergmayr, https://github.com/bergmali/android/tree/master/ass3
 * This class is not my, David Szabo's work!
 */
public class CallInfo {

	private String date;
	private String number;
	private int id;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
