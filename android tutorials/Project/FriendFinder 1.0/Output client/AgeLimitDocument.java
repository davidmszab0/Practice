import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/** Class for limiting a text field to only take two digits */
public class AgeLimitDocument extends PlainDocument {
	
	public void insertString(int offset, String str, AttributeSet attr)
			throws BadLocationException {

		if (str == null)
			return;
		if ((getLength() + str.length()) <= 2) {
			for (int i = 0; i < str.length(); i++) {
				if (!Character.isDigit(str.charAt(i)))
					return;
			}
			super.insertString(offset, str, attr);
			
		}
	}
}
