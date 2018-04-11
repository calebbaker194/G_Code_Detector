package gfields;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	@Override
	public void run()
	{
		Matcher m = sequence.matcher(code);
		if(m.find())
		{			
			strReturn = code.replaceFirst(m.group(0), replace);
			return;
		}
		strReturn = code;
		return;
	}
}
