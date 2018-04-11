package gfields;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GLookAheadField extends GFields{
	// S = Sequence = TxMx
	// R = replace = Tx
	public GLookAheadField(String s, String lookForSequence){
		setSequence(s);
		setReplace(lookForSequence);
	}
	public GLookAheadField(String s, String lookForSequence, String n) {
		setSequence(s);
		setReplace(lookForSequence);
		setName(n);
	}
	public GLookAheadField(String s, String lookForSequence, int length){
		setSequence(s);
		setReplace(lookForSequence);
		setOffset(length);
	}
	public GLookAheadField(String s, String lookForSequence, String n, int length){
		setSequence(s);
		setReplace(lookForSequence);
		setOffset(length);
		setName(n);
	}
	public String replace(String code,String file)
	{
		Matcher m = sequence.matcher(code);
		Pattern p = Pattern.compile(replace);
		Matcher la = p.matcher(file);
		if(m.find())
		{
			if(la.find())
			{
				setMessage("inserted "+ file.substring(file.indexOf(la.group(0)),file.indexOf(la.group(0))+offset));
				return code+="\n"+file.substring(file.indexOf(la.group(0)),file.indexOf(la.group(0))+offset);
			}
			else
			{
				setMessage("No More "+replace+" to insert");
			}
		}
		return code;
	}
	
	
}