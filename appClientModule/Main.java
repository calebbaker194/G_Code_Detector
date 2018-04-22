import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import gcodescanner.GMain;
import gfields.GErrorField;
import gfields.GField;
import gfields.GFieldLineReplacer;
import gfields.GFields;
import gfields.GLookAheadField;

public class Main extends JFrame implements Runnable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public static void main(String[] args) {
		new Main();
	}

	private JPanel north = new JPanel();
	private JPanel center = new JPanel();
	private JPanel south = new JPanel();
	
	private JButton check;
	private JButton browse;
	private String GCodeString;
	
	private JLabel programNumberL = new JLabel("Program Number");
	private JLabel G55L = new JLabel("G55");
	private JTextField G55F = new JTextField("G56");
	private JTextField  programNumberF = new JTextField("123");
	private JTextField path = new JTextField();
	private JTextPane readout= new JTextPane();
	
	
	private GMain gscanner = new GMain(readout);
	
	
	/* (non-Java-doc)
	 * @see java.lang.Object#Object()
	 */
	public Main() {
		super();
		/*
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		setIconImage(new ImageIcon("appClientModule\\images\\g.png").getImage());
		
		initGUI();		
		setSize(getWidth()+1, getHeight());
	}
	// G54,G55,G56,G57,G58,G59 or G99
	private void initGFileds()
	{
		GFields t;
		if(programNumberF.getText().length()>0)
		{
			t = new GFieldLineReplacer(":",":"+programNumberF.getText());
			t.setRepeating(false);
			t.setMessage("Programm Number Changed To: "+programNumberF.getText());
			t.setName("Program Number");
			gscanner.add(t);
		}
		if(G55F.getText().length()>0)
		{
			t = new GField("(G5[4-9]|G99)",G55F.getText());
			t.setRepeating(false);
			t.setMessage("Cordanite System Changed To: "+ G55F.getText());
			t.setName("Coord");
			gscanner.add(t);
		}
		
		t = new GLookAheadField("T\\d*M[0-9]", "T\\d*" , 2);
		t.setMessage(" Added Field at the line number");
		t.setName("T5M6");
		gscanner.add(t);
		
		gscanner.add(new GErrorField("(I-?\\d*\\.\\d{4})(J-?\\d*\\.\\d{4})"," I without J or J without I",15) );
	}

	private void initGUI() {
		
		frameAddInit();
		initButtons();
		initLabelsAndFont();
		path.setPreferredSize(new Dimension(300, 28));
		
		center.setLayout(new BorderLayout());
		
		//Center South
		
		JPanel jp = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		center.add(jp,BorderLayout.SOUTH);
		
		jp.add(browse);
		jp.add(path,0);
		
		//End Center South
		//////////////////
		//Center
		
		JScrollPane sp = new JScrollPane(readout);
		sp.setPreferredSize(new Dimension(420, 420));
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		center.add(sp, BorderLayout.CENTER);
		
		JPanel pad = new JPanel();
			pad.setPreferredSize(new Dimension(7,100));
			
		center.add(pad, BorderLayout.EAST);
			pad = new JPanel();
			pad.setPreferredSize(new Dimension(7,100));
		center.add(pad, BorderLayout.WEST);
		//End Center
		////////////
		//South
		
		south.add(check);
		
		//EndSouth
		///////////
		//North
		
		JPanel temp = new JPanel();
		north.add(temp,0);
		GridLayout tempg = new GridLayout(2, 2);
		tempg.setHgap(6);
		tempg.setVgap(3);
		temp.setLayout(tempg);
		
		temp.add(programNumberL);
		temp.add(programNumberF);
		temp.add(G55L);
		temp.add(G55F);	
		//End North
	}
	private void initLabelsAndFont() {
		
		Font fontG55 = G55F.getFont();
		Font def = new Font(fontG55.getFontName(), Font.BOLD, fontG55.getSize());
		
		G55F.setFont(def);
		programNumberF.setFont(def);
		path.setFont(new Font(def.getFontName(),Font.BOLD,def.getSize()+2));
		
		G55F.setHorizontalAlignment(SwingConstants.RIGHT);
		programNumberF.setHorizontalAlignment(SwingConstants.RIGHT);		
	}
	private void initButtons() {
		
		check = new JButton("Check");
		check.setLocation(100, 100);
		check.setPreferredSize(new Dimension(200,28));
		check.setBackground(Color.BLUE);
		check.setForeground(Color.WHITE);
		Main self = this;
		check.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				Thread t = new Thread(self);
				t.start();
				
			}
		});
		
		browse = new JButton("Search...");
		browse.setPreferredSize(new Dimension(100, 28));
		browse.setBackground(Color.BLUE);
		browse.setForeground(Color.WHITE);
		
		browse.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				selectFile();
				
				
			}
		});
		
	}
	private void frameAddInit() {
		
		setVisible(true);
		setSize(350,600);
		setMinimumSize(new Dimension(440, 200));
		setBackground(Color.GRAY.darker().darker());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setTitle("GCode Scanner");
		
		add(south,BorderLayout.PAGE_END);
		add(north,BorderLayout.PAGE_START);
		
		
		add(center,BorderLayout.CENTER);
		
	}

	private void selectFile() {
		java.awt.FileDialog test = new java.awt.FileDialog((java.awt.Frame) null);
		test.setVisible(true);
		path.setText(test.getDirectory()+"/"+test.getFile());
		
		
	}

	@Override
	public void run() 
	{
		System.out.println(readout.getSize());
		
		File GCode = new File(path.getText());
		GCodeString="";
		String LookAhead="";
		Scanner GCodeScanner = null;	
		
		try
		{			
			GCodeScanner= new Scanner(GCode);
			initGFileds();

			try {
				LookAhead = new String(Files.readAllBytes(Paths.get(path.getText())));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			GCodeScanner.close();
			GCodeScanner= new Scanner(GCode);
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
			//TODO: an accurate error report
		}
			
		
		if(GCodeScanner != null)
		{
			String GCodeLine;
			while(GCodeScanner.hasNextLine())
			{	
				
				GCodeLine = GCodeScanner.nextLine();
				LookAhead=LookAhead.substring(LookAhead.indexOf(GCodeLine)+GCodeLine.length());
				GCodeLine = gscanner.checkLine(GCodeLine, LookAhead); 
				GCodeString += GCodeLine+"\n";
				
			}
			gscanner.setLineNumber(0);
			GCodeScanner.close();
		}
		
		if(gscanner.isError())
		{
			GMain.appendToPane(readout, "An Error Occured Pleas read log", Color.RED);
		}
		else
		{
			GMain.appendToPane(readout, "File Parsed Succesfully as "+path.getText()+".fix", Color.GREEN);
			try {
				FileWriter temp = new FileWriter(path.getText()+".fix");
				temp.write(GCodeString);
				temp.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}