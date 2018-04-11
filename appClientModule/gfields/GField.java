package gfields;

import java.util.regex.Matcher;

public class GField extends GFields{
	public GField(String s,String r)
	{
		setSequence(s);
		setReplace(r);
	}
	public GField(String s,String r, String n)
	{
		setSequence(s);
		setReplace(r);
		setName(n);
	}
	
	
	public String replace(String code,String lookahead)
	{
		Matcher m = sequence.matcher(code);
		if(m.find())
		{			
			return code.replaceFirst(m.group(0), replace);
		}
		return code;
	}
}
