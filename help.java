import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
class help extends JFrame implements ActionListener
{
	JLabel l,l1,l2,l3,l4;
	JButton b1,b2,b3,b4,cls;
	JTextArea t;
	Font f,g;
	JScrollPane js1;
	JComboBox lf;
	int index;
	help(int ind)
	{
	    index=ind;
		f=new Font("TimesNewRoman",Font.PLAIN,18);
		g=new Font("Matura MT Script Capitals",Font.PLAIN,25);
		setSize(1360,760);
		l=new JLabel("Assymetric Cryptography overview");
		l.setFont(g);  
		l1=new JLabel("What is Assymetric cryptography???");
		l1.setFont(f);
		b1=new JButton("More..");
		b1.addActionListener(this); 
		b1.setFont(f);  
		l2=new JLabel("How to use Assymetric cryptography???");
		l2.setFont(f);
		b2=new JButton("More..");
		b2.addActionListener(this); 
		b2.setFont(f);
		l3=new JLabel("How to register in Assymetric cryptography???");
		l3.setFont(f);
		b3=new JButton("More..");
		b3.addActionListener(this); 
		b3.setFont(f);
		l4=new JLabel("Examples of Assymetric cryptography???");
		l4.setFont(f);
		b4=new JButton("More..");
		b4.addActionListener(this);   
		b4.setFont(f);
		cls=new JButton("BACK");
		cls.addActionListener(this);
		cls.setFont(f);
		t=new JTextArea();
		js1 = new JScrollPane(t);
		t.setFont(f);
		setLayout(null);
		l.setBounds(500,80,500,60);
		l1.setBounds(100,100,300,200);
		b1.setBounds(500,190,85,35);
		l2.setBounds(100,200,350,200);
		b2.setBounds(500,290,85,35);
		l3.setBounds(100,300,400,200);
		b3.setBounds(500,390,85,35);
		l4.setBounds(100,400,400,200);
		b4.setBounds(500,490,85,35);
		js1.setBounds(655,195,675,350);
		cls.setBounds(1230,600,85,40);
		add(l);
		add(l1);
		add(b1);
		add(l2);
		add(b2);
		add(l3);
		add(b3);
		add(l4);    
		add(b4);    
		add(js1);  
		add(cls); 
        setVisible(true);
		t.setEditable(false);
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==b1)
		{ 
			String str="In todays world,information technology plays an important role.\n" +
			"As it internet to share their information world wide.\n "+
			"The user can need it. The information may be secret or personal.\n"+
			"It may be important for national security. So their may be chance of illegle access\n"+
			"to the information.\n So it is necessary to encrypt the contents which are going to chase.\n"+
			"The Assymetric cryptography uses the one key for encryption and decryption.\n"+
			"Whereas the asymmetric cryptography uses the public key and private key both\n"+
			"for encryption and decryption.The public key is easily share on the network or\n"+
			"by message or on phone. The process is illustrated as bellow.\n"+
			"The sender encrypt the document using his private key and public key’s combination.\n"+
			"The receiver decrypt the message using his private key and public key both.\n";
			t.setText(str);
		}
		if(ae.getSource()==b2)
		{   
			String str="Traditional cryptography is based on the  sender and receiver of a  \n"+
			"message knowing and using the same secret key: the sender uses the secret ,\n"+
			"key to encrypt the message,and the receiver uses the same secret key to decrypt\n"+
			" the message.This method is known as secret-key cryptography.\n"+
		   "In the new system, each person gets a pair of keys, called the public key and the\n"+
		   " private key.Each person's public key is published while the private key is kept secret.\n"+
		   "Anyone can send a confidential message just using public information,\n"+
		   "but it can only be decrypted with a private key that is in the sole possession .\n"+
		   "of the intended recipient First there are two users client1 and client2,\n"+"client1 takes an message encrypt with public key and \n"+
		   "send to client2 and send client2 decrypt with private key and show original message.";
			t.setText(str);
		}
		if(ae.getSource()==b3)
		{   
			String str="Just fillup your information in registration form. \n"+
			"There is need to fill all information no leave any blank field you will get error. \n"+
			"There is important to fill 'lid and password' because you will get a unique login id\n"+"and password."+ 
			"After filling all information your public key and private key\n"+"stored in database with your lid.\n"+"Then after registration you modify your database as per your requirement";
			t.setText(str);
		}
		if(ae.getSource()==b4)
		{  
			String str="Other examples include:\n"+
			"In our project you first register your account then you will get \n"+
			"unique login id and password with public key and private key and stored in database\n"+
			"In our project there is one server and 10 no of clients connect to server.\n"+
			"we can justify how many clients connect to server at a time.\n"+
			"so I will give information about one server and two clients\n"+
			"Frist one start the server and login client1 and client2 there are connected to server.\n"+
			"client1 takes a message and Encrypt with public key,where public key taken from\n"+" database.\n"+
			"And send the Encrypted text to client2.\n"+
			"The Encrypted text goes from server and server to client2,\n"+
			"and client2 get the Encyrypted text and decrypt with private key.";
			t.setText(str);
		}
		if(ae.getSource()==cls)
		{   
			try{
			UPortal u = new UPortal();
			
			u.lf.setSelectedIndex(index);
			}catch(Exception e){}
			setVisible(false);
		}
	}
	public static void main(String arg[])
	{
	//	help h = new help();
		//h.setVisible(true);
	}
}