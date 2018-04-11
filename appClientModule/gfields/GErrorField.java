package gfields;

public class GErrorField extends GFields {

	public GErrorField(String s, String message,int o)
	{
		setSequence(s);
		setMessage(message);
		setOffset(o);
	}
	public GErrorField(String s, String message ,int o, String n)
	{
		setSequence(s);
		setMessage(message);
		setOffset(o);
		setName(n);
	}
	
	public void run()
	{
		if(code.matches(".*"+sequence.pattern().substring(0,offset)+".*") ^ code.matches(".*"+sequence.pattern().substring(offset)+".*"))
		{
			error = "FATAL: "+message+" "+code;
			strReturn = code+"!";
			return;
		}
		strReturn = code;
		return;
	}	
}
