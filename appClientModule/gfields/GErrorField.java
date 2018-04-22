package gfields;


public class GErrorField extends GFields {

	public GErrorField(String s, String message,int o)
	{
		setOffset(o);
		setSequence(s.substring(0,offset),0);
		setSequence(s.substring(offset),1);
		System.out.println(s.substring(0,offset) +"  "+ s.substring(offset));
		setMessage(message);
		
	}
	public GErrorField(String s, String message ,int o, String n)
	{
		setOffset(o);
		setSequence(s.substring(0,offset),0);
		setSequence(s.substring(offset),1);
		setMessage(message);
		setName(n);
	}
	
	@Override
	public String replace(String code, String lookahead) {
		
		m[0] = sequence[0].matcher(code);
		m[1] = sequence[1].matcher(code);
		
		if(m[0].find() ^ m[1].find())
		{
			error = "FATAL: "+message+" "+code;
			return code+"!";
			
		}
		return code;
	}	
}
