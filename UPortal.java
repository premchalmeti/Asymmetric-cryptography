import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.net.*;
public class UPortal extends JFrame implements ActionListener,ItemListener
{
    int ind;
	int flag = 0;	//for login
	JLabel l1,l2,l3,mainlbl1;
	JTextField utxt;
	JPanel p1,p2,p3;
	JPasswordField pwdtxt;
	JComboBox lf;
	JButton submitbt,cancelbt,clrbt,signinbt,hlp,cont;
	Connection con;
	Container c;
	ImageIcon i = new ImageIcon(".\\img\\ok.png");
	ImageIcon i2 = new ImageIcon(".\\img\\cancel.png");
	ImageIcon i3 = new ImageIcon(".\\img\\na.png");
	ImageIcon i4 = new ImageIcon(".\\img\\help.png");
	ImageIcon i5 = new ImageIcon(".\\img\\contact.png");
	
	String looks[]={"Metal","Nimbus","CDE/Motif","Windows","Windows Classic"};
	String clsNm[]={"javax.swing.plaf.metal.MetalLookAndFeel","javax.swing.plaf.nimbus.NimbusLookAndFeel",
					"com.sun.java.swing.plaf.motif.MotifLookAndFeel","com.sun.java.swing.plaf.windows.WindowsLookAndFeel",
					"com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel"};
	
	String lname,pwd,lid,pwdt,addr;
    static int flaginput=0;
    ResultSet rs;
	public void setlook(String clsnm)	throws Exception
	{
		UIManager.setLookAndFeel(clsnm);	//UIManager.setLookAndFeel(clsNm[x]);
			hlp.updateUI();
			lf.updateUI();
			l2.updateUI();
			l3.updateUI();
			utxt.updateUI();
			pwdtxt.updateUI();
			submitbt.updateUI();
			cont.updateUI();
			cancelbt.updateUI();
			clrbt.updateUI();
			mainlbl1.updateUI();
			signinbt.updateUI();
			mainlbl1.updateUI();
	}
	public UPortal()	throws Exception
	{
		c = getContentPane();
		if(flaginput==0)
		{
			addr = JOptionPane.showInputDialog(this,"Enter Server Address : ","User Login",JOptionPane.DEFAULT_OPTION);
			flaginput = 1;
		}
		JLabel im = new JLabel(new ImageIcon(".//img//clouds.jpg"));//background image
		Image icon = Toolkit.getDefaultToolkit().getImage("img//login.png");
		setIconImage(icon);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int width= (int) (d.width);
        int height= (int) (d.height);
		im.setBounds(0,0,width,height);
		c.add(im);
		l2 = new JLabel("Login Id");
		l2.setFont(new Font("verdana",0,20));
		l3 = new JLabel("Password");
		l3.setFont(new Font("verdana",0,20));
		utxt = new JTextField();
		lf = new JComboBox();
		pwdtxt = new JPasswordField();
		pwdtxt.setEchoChar('ï');
		submitbt = new JButton("Submit",i);
		cancelbt = new JButton("Cancel",i2);
		hlp = new JButton("Help",i4);
		cont = new JButton("Contact",i5);
		clrbt = new JButton("Clear");
		signinbt = new JButton("New Account",i3);
		setLayout(null);
		mainlbl1 = new JLabel("AssyMetric Cryptÿgraphy");
		mainlbl1.setFont(new Font("Matura MT Script Capitals",0,30));
		mainlbl1.setBounds(400,150,400,160);
		l2.setBounds(330,300,150,30);
		utxt.setBounds(450,300,300,40);
		l3.setBounds(330,360,150,30);
		pwdtxt.setBounds(450,360,300,37);
		submitbt.setBounds(440,420,100,37);
		cancelbt.setBounds(570,420,120,37);
		clrbt.setBounds(720,420,110,37);
		signinbt.setBounds(width-170,50,150,40);
		lf.setBounds(width-170,100,150,40);
		hlp.setBounds(440,540,150,45);
        cont.setBounds(660,540,150,45);
		c.add(mainlbl1);
		c.add(hlp);
		c.add(lf);
		c.add(cont);
		c.add(l2);c.add(l3);
		c.add(utxt);c.add(pwdtxt);
		c.add(submitbt);c.add(cancelbt);c.add(clrbt);
		c.add(signinbt);
		c.add(im);
		setVisible(true);
		lf.addItemListener(this);
		pwdtxt.addActionListener(this);
		cont.addActionListener(this);
		hlp.addActionListener(this);
		submitbt.addActionListener(this);
		cancelbt.addActionListener(this);
		clrbt.addActionListener(this);
		signinbt.addActionListener(this);
		setTitle("User Login");
		setSize(width,height);
		for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) 
		{
			lf.addItem(info.getName());
			if( (info.getClassName()).equals("javax.swing.plaf.nimbus.NimbusLookAndFeel") ==true )
			{
				//System.out.println(info.getClassName()+"= javax.swing.plaf.nimbus.NimbusLookAndFeel");
				try{
					lf.setSelectedIndex(1);
					setlook(info.getClassName());
				}catch(Exception e){}
			}
		}
		
	}
	public void itemStateChanged(ItemEvent ie)
	{
		String choice = lf.getSelectedItem().toString();
		ind = lf.getSelectedIndex();
		int x;
		for(x = 0;x<looks.length;x++)
		{
			if(looks[x].equals(choice))
				break;
		}
		try{
			setlook(clsNm[x]);
		}catch(Exception e){}
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==pwdtxt)
		{
			try
			{
				if((utxt.getText()).equals("") || (pwdtxt.getText()).equals(""))
					JOptionPane.showMessageDialog(this, "Check Login Id or Password!", "User Login",JOptionPane.ERROR_MESSAGE);
				else
				{//else1 start
					lname = utxt.getText();
					pwd = pwdtxt.getText();
					Class.forName("oracle.jdbc.driver.OracleDriver");
					Connection con = DriverManager.getConnection("jdbc:oracle:thin:@"+addr+":1521:XE","prem","rex");
					Statement st=con.createStatement();
					ResultSet rs = st.executeQuery("select * from Usign");
					while(rs.next())
					{
						lid = rs.getString(8);
						pwdt = rs.getString(9);
						if(lname.equals(lid) && pwd.equals(pwdt))
						{
							flag = 1;
						}
					}
					if(flag==1)
					{
						JOptionPane.showMessageDialog(this, "Access Granted!","User Login", JOptionPane.INFORMATION_MESSAGE);
						clientEx_2 c = new clientEx_2(lname,addr,ind);
						setVisible(false);
					}
					else
					{
						JOptionPane.showMessageDialog(this, "Access Denied!","User Login", JOptionPane.ERROR_MESSAGE);
						utxt.setText("");
						pwdtxt.setText("");
					}
					flag = 0;
					st.close();
					con.close();
				}//else1 closed
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(this,"Error : "+ex,"User Login",JOptionPane.ERROR_MESSAGE);
			}
		}//outer if closed
		else if(e.getSource()==submitbt)
		{
			try
			{
				if((utxt.getText()).equals("") || (pwdtxt.getText()).equals(""))
					JOptionPane.showMessageDialog(this, "Check Login Id or Password!", "User Login",JOptionPane.ERROR_MESSAGE);
				else
				{
					lname = utxt.getText();
					pwd = pwdtxt.getText();
					Class.forName("oracle.jdbc.driver.OracleDriver");
					con = DriverManager.getConnection("jdbc:oracle:thin:@"+addr+":1521:XE","prem","rex");
					Statement st=con.createStatement();
					rs = st.executeQuery("select * from Usign");
					while(rs.next())
					{
						lid = rs.getString(8);
						pwdt = rs.getString(9);
						if(lname.equals(lid) && pwd.equals(pwdt))
						{
							flag = 1;
						}
					}
					if(flag==1)
					{
						JOptionPane.showMessageDialog(this, "Access Granted!","User Login", JOptionPane.INFORMATION_MESSAGE);
						clientEx_2 c = new clientEx_2(lname,addr,ind);
						
						setVisible(false);
					}
					else
					{
						JOptionPane.showMessageDialog(this, "Access Denied!","User Login", JOptionPane.ERROR_MESSAGE);
						utxt.setText("");
						pwdtxt.setText("");
					}
					flag = 0;
					st.close();
					con.close();
				}//outer else closed
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(this,"Error : "+ex,"User Login",JOptionPane.ERROR_MESSAGE);
			}
		}//if closed
		else if(e.getSource()==cancelbt)
		{
			setVisible(false);
		}
		else if(e.getSource()==clrbt)
		{
			utxt.setText("");
			pwdtxt.setText("");
			flag = 0;
		}
		else if(e.getSource()==hlp)
		{
			help h = new help(ind);
			setVisible(false);
		}
		else if(e.getSource()==cont)
		{
			contact jt = new contact(ind);
			setVisible(false);
		}
		else
		{
			Usignin us = new Usignin(addr,ind);
			setVisible(false);
		}
	}
	public static void main(String args[])throws Exception
	{
		SwingUtilities.invokeAndWait(new Runnable()
		{
			public void run()
			{
				try{
				UPortal up = new UPortal();
				up.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				}catch(Exception e){;}
			}
		});
	}
}