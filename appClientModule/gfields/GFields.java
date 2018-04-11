package gfields;

import java.util.regex.Pattern;

public abstract class GFields {
	protected String name;
	protected Pattern sequence;
	protected String replace;
	protected int offset;
	protected String error;
	protected String message;
	protected boolean repeating = true;
	
	public abstract String replace(String code,String lookahead);

	public String getSequence() {
		return sequence.pattern();
	}
	public void setSequence(String sequence) {
		this.sequence = Pattern.compile(sequence);
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public String getReplace() {
		return replace;
	}
	public void setReplace(String replace) {
		this.replace = replace;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public boolean hasError() {
		if(error == null)
			return false;
		
		return error.length() != 0;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String m)
	{
		message = m;
	}
	public boolean hasMessage()
	{
		if(message == null)
			return false;
		
		return message.length() != 0;
	}
	public void setRepeating(boolean b)
	{
		repeating = b;
	}
	public boolean isRepeating()
	{
		return repeating;
	}
}
