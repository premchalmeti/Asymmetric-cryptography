import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class contact extends JFrame implements ActionListener
{
	Container c = getContentPane();
	JButton jb = new JButton("Back");
	int index;
	public contact(int ind)
	{   
		super("Contact_Us...");
		index=ind;
		Image icon = Toolkit.getDefaultToolkit().getImage(".\\img\\contact.png");
		setIconImage(icon); 
		jb.setBounds(955,595,80,40);
		c.add(jb);
		JTabbedPane jtp=new JTabbedPane();
		jtp.addTab("Email us @...   ",new Email_usPanel());
		jtp.addTab("Contact_us  ",new Contact_usPanel());
		c.add(jtp);
		jb.addActionListener(this);
		setSize(1340,720);
		setVisible(true);
	}
	public void actionPerformed(ActionEvent ae)
	{
		try{
		UPortal up = new UPortal();
		
		up.lf.setSelectedIndex(index);
		}catch(Exception e){}
		setVisible(false);
	}
    public static void main(String arg[])
    {
    }
}
class Email_usPanel extends JPanel 
{
	public Email_usPanel()
	{ 
		JButton b1=new JButton("vivekvannam95@gmail.com");
		add(b1);
		JButton b2=new JButton("rangam.guru@gmail.com");
		add(b2);
		JButton b3=new JButton("naroleanand@gmail.com");
		add(b3);
		JButton b4=new JButton("prem.chalmeti1289@gmail.com");
		add(b4);
		setLayout(null);
		b1.setBounds(450,450,250,40); 
		b2.setBounds(450,350,250,40);
		b3.setBounds(450,250,250,40); 
		b4.setBounds(450,150,250,40); 
	}
}
class Contact_usPanel extends JPanel
{
	public Contact_usPanel()
	{
		JButton  b1=new JButton("vivek vannam :-8446914712");
		add(b1);
		JButton b2=new JButton("Guruprasad Rangam :-8888660591");
		add(b2);
		JButton b3=new JButton("Anand Narole :-8862062137");
		add(b3);
		JButton b4=new JButton("prem chalmeti :-8180011318");
		add(b4);
		setLayout(null);
		b1.setBounds(450,450,250,40);
		b2.setBounds(450,350,250,40);
		b3.setBounds(450,250,250,40);
		b4.setBounds(450,150,250,40);
	}
}