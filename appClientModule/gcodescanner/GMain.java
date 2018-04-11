package gcodescanner;

import java.awt.Color;
import java.util.Stack;

import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import gfields.GField;
import gfields.GFields;

public class GMain implements Runnable{
	
	private Stack<GFields> modifications = new Stack<GFields>();
	private int lineNumber=0;
	private JTextPane console;
	private boolean error = false;
	private String s;
	private String lookAhead;
	private String retStr;
	public static void appendToPane(JTextPane tp, String msg, Color c)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }
	
	public GMain(JTextPane c)
	{
		console = c;
	}
	public void add(GFields t)
	{
		modifications.push(t);
	}
	public void setLineNumber(int l)
	{
		lineNumber = l;
	}
	public JTextPane getConsole() {
		return console;
	}
	public void setConsole(JTextPane console) {
		this.console = console;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public void prepair(String se,String la)
	{
		s = se;
		lookAhead = la;
	}
	@Override
	public void run() {
		lineNumber++;
		String temp="";
		for(GFields g : modifications)
		{
			g.prepair(s, lookAhead);
			Thread t = new Thread(g);
			t.start();	
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			temp=g.getStrReturn();
			if(temp != s)
			{
				if(!g.isRepeating())
					modifications.remove(g);
				
				if(g.hasMessage())
				{
					appendToPane(console,lineNumber+": "+g.getMessage()+"\n",Color.BLACK);
				}
				if(g.hasError())
				{
					appendToPane(console,lineNumber+": "+g.getError()+"\n",Color.RED);
						if(g.getError().contains("FATAL"))
							setError(true);
				}
				retStr = temp;
				return;
			}
		}
		retStr = s;
		return;
		
	}

	public String getRetStr() {
		return retStr;
	}
}
