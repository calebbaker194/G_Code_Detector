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
	
	public String replace(String code,String Look)
	{
		if(code.matches(".*"+sequence.pattern().substring(0,offset)+".*") ^ code.matches(".*"+sequence.pattern().substring(offset)+".*"))
		{
			error = "FATAL: "+message+" "+code;
			return code+"!";
		}
		return code;
	}	
}
