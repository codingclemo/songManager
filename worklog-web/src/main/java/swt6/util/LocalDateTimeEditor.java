package swt6.util;

import java.beans.PropertyEditorSupport;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateTimeEditor extends PropertyEditorSupport {

	private DateTimeFormatter formatter;

	public LocalDateTimeEditor(DateTimeFormatter formatter) {
		this.formatter = formatter;
	}
	
  @Override
  public void setAsText(String text) throws IllegalArgumentException {
    try {
  	  setValue(LocalDateTime.parse(text, formatter));
    }
		catch (DateTimeParseException ex) {
				throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
		}
  }
  
  @Override
  public String getAsText() {
  	LocalDateTime dt = (LocalDateTime)getValue();
  	return getValue() != null ? dt.format(formatter) : "";
  }
}
