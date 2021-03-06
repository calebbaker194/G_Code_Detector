package gfields;


public class GField extends GFields{
	public GField(String s,String r)
	{
		setSequence(s,0);
		setReplace(r);
	}
	public GField(String s,String r, String n)
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
			return code.replaceFirst(m[0].group(0), replace);
		}

		return code;
	}
}
