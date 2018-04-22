package gfields;

public class GLookAheadField extends GFields{
	// S = Sequence = TxMx
	// R = replace = Tx
	public GLookAheadField(String s, String lookForSequence){
		setSequence(lookForSequence,1);
		setSequence(s,0);
		setReplace(lookForSequence);
	}
	public GLookAheadField(String s, String lookForSequence, String n) {
		setSequence(lookForSequence,1);
		setSequence(s,0);
		setReplace(lookForSequence);
		setName(n);
	}
	public GLookAheadField(String s, String lookForSequence, int length){
		setSequence(lookForSequence,1);
		setSequence(s,0);
		setReplace(lookForSequence);
		setOffset(length);
	}
	public GLookAheadField(String s, String lookForSequence, String n, int length){
		setSequence(lookForSequence,1);
		setSequence(s,0);
		setReplace(lookForSequence);
		setOffset(length);
		setName(n);
	}
	
	@Override
	public String replace(String code, String lookahead) {
		m[0] = sequence[0].matcher(code);
		m[1] = sequence[1].matcher(lookahead);
		if(m[0].find())
		{
			if(m[1].find())
			{
				setMessage("inserted "+ m[1].group(0));
				return code+="\n"+m[1].group(0);
				
			}
			else
			{
				setMessage("No More "+replace+" to insert");
			}
		}
		return code;
	}
	
	
}
