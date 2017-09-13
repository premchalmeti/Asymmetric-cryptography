import java.io.*;
import java.util.*;
import java.net.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.math.*;

class clientEx extends JFrame implements ActionListener
{
    static String uname,fromu="",text1="",touname="",line;
    PrintWriter pw;
	JTabbedPane jt1;
    BufferedReader br;
    JButton btnSend,btnExit,signout;
    Socket client;
	static BigInteger e,n,decmod,decprvt;
	JTextField t1,t2,t3,t4,tfInput;
	JLabel l1,l2,l3,l4,l5,l6,l7;
	JLabel im=new JLabel(new ImageIcon("login.png"));
	static JTextArea taMessages;
	JScrollPane js1,js2;
	JList ls1;
	JButton b1,b2,b3,b4,b5;
	Container cona;
	public clientEx(String utname,String servername)throws Exception
	{
		uname = utname;
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int width= (int)( d.width);
        int height= (int)(0.995* d.height);
		taMessages = new JTextArea();
		tfInput = new JTextField();
		b1 = new JButton("Send");
		signout = new JButton("Logout");
		b2 = new JButton("Cancel");
		b3 = new JButton("Clear");
		b4 = new JButton("Encrypt");
		b5 = new JButton("Decrypt");
		l5 = new JLabel("Enter Message : ");
		l6 = new JLabel("Received Message : ");
		cona = getContentPane();
		cona.setLayout(null);
		t3 = new JTextField();t3.setOpaque(false);
		Image icon = Toolkit.getDefaultToolkit().getImage("img//login.png");
		setIconImage(icon); 
		l3 = new JLabel("To User : ");
		/*
		t2 = new JTextField();
		ls1 = new JList();
		t1 = new JTextField();
		l1 = new JLabel("To User : ");
		l2 = new JLabel("Port No : ");
		l4 = new JLabel("Port No : ");
		l7 = new JLabel("Active Clients : ");
		l2.setBounds(350,90,100,40);
		t2.setBounds(450,90,150,40);
		l4.setBounds(350,170,100,40);
		t4.setBounds(450,170,150,40);
		l7.setBounds(750,110,100,40);
		ls1.setBounds(750,160,200,340);
		l1.setBounds(40,90,100,40);
		t1.setBounds(150,90,150,40);*/
		signout.setBounds((width-310),10,150,40);
		im.setBounds(0,0,500,500);
		l3.setBounds(40,170,100,40);
		t3.setBounds(150,170,150,40);
		l5.setBounds(40,300,100,40);
		l6.setBounds(20,400,120,40);
		b1.setBounds(160,600,85,35);
		b2.setBounds(280,600,85,35);
		b3.setBounds(400,600,85,35);
		b4.setBounds(540,600,95,35);
		b5.setBounds(660,600,95,35);
		js2 = new JScrollPane(taMessages);
		js1 = new JScrollPane(tfInput);
		js2.setBounds(150,400,500,90);
		js1.setBounds(150,300,500,45);
		tfInput.addActionListener(this);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		signout.addActionListener(this);
		b5.addActionListener(this);
		//add(t1);add(l2);add(t2);add(l3);add(t3);add(l4);add(t4);add(l7);add(ls1);
		cona.add(im);
		cona.add(signout);cona.add(t3);cona.add(b1);cona.add(b2);cona.add(b3);cona.add(l6);cona.add(l5);cona.add(b4);cona.add(b5);
		cona.add(js1);cona.add(js2);cona.add(l3);//cona.add(js2);
		setSize(width, height);
		setLocation((d.width- width)/2,(d.height- height)/2);
		setVisible(true);
		//connecting
        client  = new Socket(servername,9999);
        br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        pw = new PrintWriter(client.getOutputStream(),true);
        pw.println(uname);  // send name to server
        new MessagesThread().start();  // create thread for listening for messages
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","mymotog2O");
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
		setTitle(uname);
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
		return message.modPow(decprvt, decmod);
	}
	public void actionPerformed(ActionEvent e1)
	{
		if(e1.getSource()==tfInput)
		{
			String text = tfInput.getText();
			BigInteger plaintext = new BigInteger(text.getBytes());
			BigInteger ciphertext = encrypt(plaintext);
			tfInput.setText(""+ciphertext);
			JOptionPane.showMessageDialog(this,"Encryption done!","User : "+uname,JOptionPane.INFORMATION_MESSAGE);
		}
		if(e1.getSource()==b1)
		{
			text1 = tfInput.getText();
			pw.println(text1);
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
			//StringBuffer Ttext1 = new StringBuffer(tfInput.getText());
			String text = tfInput.getText();
			BigInteger plaintext = new BigInteger(text.getBytes());
			BigInteger ciphertext = encrypt(plaintext);
			tfInput.setText(""+ciphertext);
			JOptionPane.showMessageDialog(this,"Encryption done!","User : "+uname,JOptionPane.INFORMATION_MESSAGE);
		}
		else if(e1.getSource()==signout)
		{
			JOptionPane.showMessageDialog(this,"Log out successfully!","Sign-out",JOptionPane.INFORMATION_MESSAGE);
			UPortal u = new UPortal();
			setVisible(false);
		}
		else
		{
			BigInteger plaintext,ciphertext;
			ciphertext = new BigInteger(taMessages.getText());
			plaintext = decrypt(ciphertext);
			taMessages.setText(""+new String(plaintext.toByteArray()));
			JOptionPane.showMessageDialog(this,"Decryption done!","User : "+uname,JOptionPane.INFORMATION_MESSAGE);
		}
	}
	public static void main(String args[])throws Exception
	{
		//clientEx c = new clientEx("localhost","localhost");
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
					t3.setText(fromu);
                    line = br.readLine();
					taMessages.setText("");
					taMessages.append(line);
					String line1=br.readLine();
					String line2=br.readLine();
					decmod = new BigInteger(line1);//n
					decprvt = new BigInteger(line2);//dine2
                 } // end of while
            } 
			catch(Exception ex) 
			{
			}
        }//run()
    }//MessagesThread
}