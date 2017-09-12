import java.io.*;
import java.util.*;
import java.net.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.math.*;
class clientEx_2 extends JFrame implements ActionListener
{
	JTabbedPane jtp;
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int width= (int)( d.width);
    int height= (int)(0.995* d.height);
	Container c = getContentPane();
	JButton signout;
	int index;
	public clientEx_2(String uname,String servername,int ind)
	{
	    index=ind;
		Image icon = Toolkit.getDefaultToolkit().getImage("img//login.png");
		setIconImage(icon);
		signout = new JButton();
		signout = new JButton("Logout",new ImageIcon(".//img//log.png"));
		signout.setBounds(1200,50,150,40);
		c.add(signout);
		signout.addActionListener(this);
		jtp = new JTabbedPane();
		jtp.addTab("  Chat   ",new ImageIcon(".//img//chat.png"),new send(uname,servername));
		jtp.addTab("  Update Info   ",new ImageIcon(".//img//info.png"),new info(uname,servername));
		jtp.addTab("  View Keys   ",new ImageIcon(".//img//keys.png"),new keys(uname,servername));
		//jtp.addTab("change password",new ImageIcon(".//img//cp.png"),new cngpwd(uname));
		c.add(jtp);
		setSize(width, height);
		setLocation((d.width- width)/2,(d.height- height)/2);
		setTitle(uname);
		setVisible(true);
	}
	public void actionPerformed(ActionEvent e1)
	{
		if(e1.getSource()==signout)
		{
			JOptionPane.showMessageDialog(this,"Log out successfully!","Sign-out",JOptionPane.INFORMATION_MESSAGE);
			try{
				UPortal u = new UPortal();
				u.lf.setSelectedIndex(index);
			}
			catch(Exception e){}
			
			setVisible(false);
		}
	}
	public static void main(String args[])
	{
		//clientEx_2 c = new clientEx_2("localhost","localhost");
	}
}
class send extends JPanel implements ActionListener
{
	String uname,fromu="",text1="",touname="",line,sname;
	JButton btnSend,btnExit;
	BigInteger e,n,decmod,decprvt;
	JTextField t3,t4,frmuser,tfInput;
	JLabel l2,l3,l5,l6;
	JTextArea taMessages;
	JScrollPane js1,js2;
	JList ls1;
	JButton b1,b2,b3,b4,b5;
	public PrintWriter pw;
	public Socket client;
    public BufferedReader br;
	
    send(String uname2,String servername)
	{
		uname = uname2;
		sname = servername;
		try
		{
			client  = new Socket(servername,9998);
			br = new BufferedReader(new InputStreamReader(client.getInputStream()));
			pw = new PrintWriter(client.getOutputStream(),true);
			pw.println(uname);  // send name to server
			MessagesThread mt = new MessagesThread();
			mt.start();
		}
		catch(Exception e){}
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@"+sname+":1521:XE","prem","rex");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from keys");
			while(rs.next())
			{
				String ln = rs.getString(1);
				if(uname.equals(ln))
				{
					e = new BigInteger(rs.getString(2));
					n = new BigInteger(rs.getString(4));
				}
			}
		}
		catch(Exception e1)
		{
			JOptionPane.showMessageDialog(this,"Error while Accessing Records"+e1,"Error",JOptionPane.ERROR_MESSAGE);
		}
		b1 = new JButton("Send");
		b2 = new JButton("Cancel");
		b3 = new JButton("Clear");
		b4 = new JButton("Encrypt");
		b5 = new JButton("Decrypt");
		l5 = new JLabel("Enter Message : ");
		l6 = new JLabel("Received Message : ");
		t3 = new JTextField();
		l3 = new JLabel("To User : ");
		l2 = new JLabel("From User : ");
		taMessages = new JTextArea();
		tfInput = new JTextField();
		frmuser = new JTextField();
		setLayout(null);
		l3.setBounds(80,170,100,40);
		t3.setBounds(190,170,150,40);
		l2.setBounds(430,170,100,40);
		frmuser.setBounds(530,170,150,40);
		l5.setBounds(80,300,100,40);
		l6.setBounds(60,400,120,40);
		b1.setBounds(180,600,85,40);
		b2.setBounds(320,600,85,40);
		b3.setBounds(460,600,85,40);
		b4.setBounds(600,600,95,45);
		b5.setBounds(740,600,95,45);
		js2 = new JScrollPane(taMessages);
		js1 = new JScrollPane(tfInput);
		js2.setBounds(190,400,500,90);
		js1.setBounds(190,300,500,45);
		tfInput.addActionListener(this);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		add(b1);add(b2);add(b3);  add(b4); add(b5);
		add(t3);add(js1); add(js2); add(l6); add(l5);add(l3);// add(js2);
		add(l2);add(frmuser);
	}
	public synchronized String encrypt(String message) 
	{
		return(new BigInteger(message.getBytes())).modPow(e, n).toString();	//(e,n) is public key
	}
	public synchronized BigInteger encrypt(BigInteger message)
	{
		return message.modPow(e, n);
	}
	public synchronized String decrypt(String message) 
	{
		return new String((new BigInteger(message)).modPow(decprvt, decmod).toByteArray());	//(d,n) is private key
	}
	public synchronized BigInteger decrypt(BigInteger message) 
	{
		return message.modPow(decprvt,decmod);
	}
	public void actionPerformed(ActionEvent e1)
	{
		if(e1.getSource()==tfInput)
		{
			String text = tfInput.getText();
			if(text.equals(""))
			{
				JOptionPane.showMessageDialog(this,"No message to Encrypt!",uname,JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				BigInteger plaintext = new BigInteger(text.getBytes());
				BigInteger ciphertext = encrypt(plaintext);
				tfInput.setText(""+ciphertext);
				JOptionPane.showMessageDialog(this,"Message Encrypted!",uname,JOptionPane.INFORMATION_MESSAGE);
			}
		}
		if(e1.getSource()==b1)
		{
			text1 = tfInput.getText();
			pw.println(text1);
			pw.println(uname);
			pw.println(t3.getText());//to user
			tfInput.setText("");
		}
		else if(e1.getSource()==b2)
		{
			System.exit(0);
		}
		else if(e1.getSource()==b3)
		{
			tfInput.setText("");
			taMessages.setText("");
		}
		else if(e1.getSource()==b4)
		{
			String text = tfInput.getText();
			if(text.equals(""))
			{
				JOptionPane.showMessageDialog(this,"Enter message to Encrypt",uname,JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				BigInteger plaintext = new BigInteger(text.getBytes());
				BigInteger ciphertext = encrypt(plaintext);
				tfInput.setText(""+ciphertext);
				JOptionPane.showMessageDialog(this,"Encryption done!",uname,JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else if(e1.getSource()==b5)
		{
			String text = taMessages.getText();
			if(text.equals(""))
			{
				JOptionPane.showMessageDialog(this,"Message Field Empty!",uname,JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				BigInteger plaintext,ciphertext;
				ciphertext = new BigInteger(text);
				plaintext = decrypt(ciphertext);
				taMessages.setText(""+new String(plaintext.toByteArray()));
				JOptionPane.showMessageDialog(this,"Decryption done!",uname,JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	class  MessagesThread extends Thread 
	{
        public void run() 
		{
            try
			{
                while(true) 
				{
					fromu = br.readLine();
					frmuser.setText(fromu);
                    line = br.readLine();
					taMessages.setText("");
					taMessages.append(line);
					String line1=br.readLine();
					String line2=br.readLine();
					decmod = new BigInteger(line1);//n
					decprvt = new BigInteger(line2);//d
                 } // end of while
            } 
			catch(Exception ex) 
			{
			}
        }//run()
    }//MessagesThread
}
class info extends JPanel implements ActionListener
{
	private JTextField Lidtxt,adrstxt,dobd,dobm,doby,mailtxt,mnametxt,fnametxt,snametxt,pnotxt;
    private JPasswordField pwdtxt;
	private JButton cancelbt,submitbt,Edit;
    private JLabel fname,mname,jLabel1,jLabel2,jLabel3,jLabel4,jLabel5,jLabel6,jLabel7,jLabel8,jLabel9,jLabel10,mainlbl1,img;
	String puname,sname;
	info(String name,String servername)
	{
		puname = name;
		sname = servername;
		mnametxt = new JTextField();
        snametxt = new JTextField();
        fnametxt = new JTextField();
		dobd = new JTextField();
		dobm = new JTextField();
		doby = new JTextField();
		mailtxt = new JTextField();
		pnotxt = new JTextField();
		adrstxt = new JTextField();
		Lidtxt = new JTextField();
		pwdtxt = new JPasswordField();
		
		fname = new JLabel("First name");
        mname = new JLabel("Middle name");
        jLabel1 = new JLabel("surname");
        jLabel2 = new JLabel("Date of birth");
        jLabel3 = new JLabel("/");
        jLabel4 = new JLabel("/");
        jLabel5 = new JLabel("E-mail ID");
        jLabel6 = new JLabel("Contact No");
        jLabel7 = new JLabel("Address");
        jLabel8 = new JLabel("login ID");
        jLabel9 = new JLabel("Password");
        //jLabel10 = new JLabel("Confirm Password");
        mainlbl1 = new JLabel("AssyMetric CryptØgraphy");
		
		Lidtxt.setEditable(false);adrstxt.setEditable(false);dobd.setEditable(false);dobm.setEditable(false);doby.setEditable(false);
		mailtxt.setEditable(false);mnametxt.setEditable(false);fnametxt.setEditable(false);snametxt.setEditable(false);
		pnotxt.setEditable(false);pwdtxt.setEditable(false);
		//cpwdtxt.setEditable(false);
		submitbt = new JButton("Submit");
        cancelbt = new JButton("Cancel");
		Edit = new JButton("Edit");
        
        //pwdtxt.setEchoChar('\u2219');
        //cpwdtxt.setEchoChar('\u2219');
		setLayout(null);
		mainlbl1.setFont(new Font("Matura MT Script Capitals",0,30));
	    mainlbl1.setForeground(new Color(0, 79, 0));
		mainlbl1.setBounds(210,10,400,60);
		fname.setBounds(30,100,90,30);
		fnametxt.setBounds(160,100,150,35);
		//img.setBounds(800,100,550,550);
		mname.setBounds(330,100,90,30);
		mnametxt.setBounds(420,100,150,35);
		
		jLabel1.setBounds(590,100,90,30);
		snametxt.setBounds(665,100,137,35);

		jLabel2.setBounds(30,170,120,20);
		dobd.setBounds(160,170,40,35);
		jLabel3.setBounds(230,170,30,30);
		dobm.setBounds(260,170,40,35);
		jLabel4.setBounds(340,170,30,30);
		doby.setBounds(380,170,40,35);

		jLabel5.setBounds(30,240,90,20);
		mailtxt.setBounds(160,240,300,35);
		jLabel6.setBounds(30,310,90,20);
		pnotxt.setBounds(160,310,300,35);
	
		jLabel7.setBounds(30,380,90,20);
		adrstxt.setBounds(160,380,300,35);
		jLabel8.setBounds(30,430,90,20);
		Lidtxt.setBounds(160,430,300,35);
		jLabel9.setBounds(30,500,90,20);
		pwdtxt.setBounds(160,500,300,35);
		
		//jLabel10.setBounds(30,570,120,20);
		//cpwdtxt.setBounds(160,570,300,35);

		submitbt.setBounds(70,600,80,40);
		cancelbt.setBounds(190,600,80,40);
		Edit.setBounds(310,600,80,40);
		
		Edit.addActionListener(this);
		submitbt.addActionListener(this);
		cancelbt.addActionListener(this);
		//Adding components
		 add(mainlbl1);	 //add(img);
		 add(Edit);
		 add(fname);
		 add(fnametxt);
		 add(mname);
		 add(mnametxt);
		 add(jLabel1);
		 add(snametxt);
		 add(jLabel2);
		 add(dobd);
		 add(jLabel3);
		 add(dobm);
		 add(jLabel4);
		 add(doby);
		 add(jLabel5);
		 add(mailtxt);
		 add(jLabel6);
		 add(pnotxt);
		 add(jLabel7);
		 add(adrstxt);
		 add(jLabel8);
		 add(Lidtxt);
		 add(jLabel9);
		 add(pwdtxt);
		 //add(jLabel10);
		 //add(cpwdtxt);
		 add(submitbt);
		 add(cancelbt);
		 String str[] = new String[3];// = new String[3];
		 int i=0;
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@"+sname+":1521:XE","prem","rex");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from Usign");
			while(rs.next())
			{
				String ln = rs.getString(8);
				if(puname.equals(ln))
				{
					String date = rs.getString(4);
					StringTokenizer s = new StringTokenizer(date,"/");
					while(s.hasMoreTokens())
					{
						str[i] = s.nextToken();
						i++;
					}
					Lidtxt.setText(rs.getString(8));
					adrstxt.setText(rs.getString(7));
					dobd.setText(str[0]);
					dobm.setText(str[1]);
					doby.setText(str[2]);
					mailtxt.setText(rs.getString(5));
					mnametxt.setText(rs.getString(2));
					fnametxt.setText(rs.getString(1));
					snametxt.setText(rs.getString(3));
					pnotxt.setText(rs.getString(6));
					pwdtxt.setText(rs.getString(9));
				}//if closed
			}//while closed
		}//try closed
		catch(Exception e1)
		{
			JOptionPane.showMessageDialog(this,"Error while Accessing Records2"+e1,"Error",JOptionPane.ERROR_MESSAGE);
		}
	}//info() closed
	public void actionPerformed(ActionEvent e)
	{
			if(e.getSource()==Edit)
			{
				Lidtxt.setEditable(true);adrstxt.setEditable(true);dobd.setEditable(true);dobm.setEditable(true);doby.setEditable(true);
				mailtxt.setEditable(true);mnametxt.setEditable(true);fnametxt.setEditable(true);snametxt.setEditable(true);
				pnotxt.setEditable(true);
				pwdtxt.setEditable(true);
			}
	}
}//class closed
class keys extends JPanel
{
	JTextField t1,t2,t3,t4;
	JLabel l1,l2,l3,l4;
	BigInteger e,n,d;
	JScrollPane js1,js2,js3;
	String uname,sname;
	public keys(String name,String servername)
	{
		try{
			uname = name;
			sname = servername;
			t1 = new JTextField();
			t2 = new JTextField();
			t3 = new JTextField();
			t4 = new JTextField();
			js1 = new JScrollPane(t2);
			js2 = new JScrollPane(t3);
			js3 = new JScrollPane(t4);
			l1 = new JLabel("Login ID:");
			l2 = new JLabel("E value:");
			l3 = new JLabel("D value:");
			l4 = new JLabel("N value:");
			setLayout(null);
			l1.setBounds(200,130,50,60);
			t1.setBounds(350,130,600,60);
			l2.setBounds(200,280,50,60);
			js1.setBounds(350,280,600,60);
			l3.setBounds(200,430,50,60);
			js2.setBounds(350,430,600,70);
			l4.setBounds(200,560,50,60);
			js3.setBounds(350,560,600,70);
			add(l1);add(l2);add(l3);add(l4);add(t1);add(js1);
			add(js2);add(js3);
			t1.setEditable(false);t2.setEditable(false);t3.setEditable(false);
			t4.setEditable(false);
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@"+sname+":1521:XE","prem","rex");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from keys");
			while(rs.next())
			{
				String ln = rs.getString(1);
				if(uname.equals(ln))
				{
					e = new BigInteger(rs.getString(2));
					d = new BigInteger(rs.getString(3));
					n = new BigInteger(rs.getString(4));
				}
			}
		}
		catch(Exception e2)
		{
			JOptionPane.showMessageDialog(this,"Error fetching keys!",uname,JOptionPane.ERROR_MESSAGE);
		}
		t1.setText(uname);
		t2.setText(""+e);
		t3.setText(""+d);
		t4.setText(""+n);
	}
}
/*
class cngpwd extends JPanel implements ActionListener
{
	JTextField t1;
	JPasswordField t2,t3;
	JLabel l1,l2,l3;
	JButton b1,b2;
	String cpname,sname;
	String lid2,opwd;
	public cngpwd(String uname,String servername)
	{
			cpname = uname;
			sname = servername;
			t1 = new JTextField();
			t2 = new JPasswordField();
			t3 = new JPasswordField();

			l1 = new JLabel("Old password:");
			l2 = new JLabel("New password");
			l3 = new JLabel("Confirm password");

			b1=new JButton("Change");
			b2=new JButton("Cancel");
			
			setLayout(null);
			
			l1.setBounds(200,130,130,60);
			t1.setBounds(350,130,250,60);
			l2.setBounds(200,280,130,60);
			t2.setBounds(350,280,250,60);
			l3.setBounds(200,430,130,60);
			t3.setBounds(350,430,250,60);
			b1.setBounds(200,550,150,60);
			b2.setBounds(400,550,150,60);
			
			add(l1);add(l2);add(l3);add(t1);add(t2);add(t3);add(b1);add(b2);
			
			b1.addActionListener(this);
			b2.addActionListener(this);
					
			
	}
	
	public void actionPerformed(ActionEvent e)
	{
			if(e.getSource()==b1)
			{
				
				try
				{

					Class.forName("oracle.jdbc.driver.OracleDriver");
					Connection con = DriverManager.getConnection("jdbc:oracle:thin:@"+addr+":1521:XE","system","prem");
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery("select * from Usign");
					while(rs.next())
					{
						lid2 = rs.getString(8);
						opwd = rs.getString(9);
						if(cpname.equals(lid2))
						{			
							Statement st1= con.createStatement();
							String newpwd=t2.getText();//new password
							int cnt1=0;
							cnt1=st1.executeUpdate("update Usign set pwd='"+newpwd+"' where lid='"+lid2+"'");
							
							 
							if(t2.getText()==t3.getText()&& t1.getText()==opwd)
							{
								cnt1=st1.executeUpdate("update Usign set pwd='"+newpwd+"' where lid='"+lid2+"'");
							} 
							
							
							if(t1.getText()==t2.getText())
							{
								JOptionPane.showMessageDialog(this,"Plz Enter Another Password!",cpname,JOptionPane.ERROR_MESSAGE);
							}
							

							 if(t2.getText()!=t3.getText())
							{
								JOptionPane.showMessageDialog(this,"Password not match!",cpname,JOptionPane.ERROR_MESSAGE);
							} 
						 	
							if(cnt1>0)
							{
								JOptionPane.showMessageDialog(this,"Password Changed!",cpname,JOptionPane.INFORMATION_MESSAGE);
							}
							
							else
							{
								JOptionPane.showMessageDialog(this,"Password not match!",cpname,JOptionPane.ERROR_MESSAGE);
							} 
 
						}
					}
					
				}
				catch(Exception e2)
				{
					//JOptionPane.showMessageDialog(this,"Password not match!",cpname,JOptionPane.ERROR_MESSAGE);
					System.out.println(e2);
				}
			}
			else
			{				
				UPortal up = new UPortal();
				setVisible(false);
				//System.exit(0);
			}
	}
}
*/