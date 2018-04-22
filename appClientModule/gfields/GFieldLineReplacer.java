package gfields;

public class GFieldLineReplacer extends GFields{
	public GFieldLineReplacer(String s,String r)
	{
		setSequence(s,0);
		setReplace(r);
	}
	public GFieldLineReplacer(String s,String r, String n)
	{
		setSequence(s,0);
		setReplace(r);
		setName(n);
	}
	
	
	public String replace(String code,String lookahead)
	{
		m[0] = sequence[0].matcher(code);
		
		if(m[0].find())
		{		
			return replace;
		}
		return code;
	}
}
