import java.math.BigInteger;
import java.sql.*;
import javax.swing.*;
import java.security.*;
import java.awt.event.*;
import java.awt.*;
import java.util.regex.*;

public class Usignin extends JFrame implements ActionListener
{
    String fnm="",dob="",mnm="",snm="",email="",address="",lid="",pwd="",cpwd="",pNo="",sname;
    Connection con;
	int index;
    Container cona = getContentPane();
    Statement st;
    private BigInteger n,d,e;
    private int bitlen = 512;
    private JTextField Lidtxt,adrstxt,dobd,dobm,doby,mailtxt,mnametxt,fnametxt,snametxt,pnotxt;
    private JButton cancelbt,clrbt,newacc,submitbt;
    private JPasswordField cpwdtxt,pwdtxt;
    private JLabel fname,mname,jLabel1,jLabel2,jLabel3,jLabel4,jLabel5,jLabel6,jLabel7,jLabel8,jLabel9,jLabel10,mainlbl1,img;
    ImageIcon i = new ImageIcon(".\\img\\cn2.png");
	ImageIcon i2 = new ImageIcon(".\\img\\ok.png");
	ImageIcon i3 = new ImageIcon(".\\img\\cancel.png");
	ImageIcon i4 = new ImageIcon(".\\img\\log.png");
    public Usignin(String servername,int ind) 
    {
		index = ind;
		sname = servername;
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)(d.width);
        int height = (int)(d.height);
		setSize(width, height);
		cona.setBackground(new Color(255,255,255));
        setTitle("Registration Portal");
		Image icon = Toolkit.getDefaultToolkit().getImage("img//login.png");
		setIconImage(icon);
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
        cpwdtxt = new JPasswordField();
		
		img = new JLabel(i);	//back img
		
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
        jLabel10 = new JLabel("Confirm Password");
        mainlbl1 = new JLabel("AsyMMetric CryptØgraphy");
		
		submitbt = new JButton("Submit",i2);
        cancelbt = new JButton("Cancel",i3);
        newacc = new JButton("Login",i4);
        clrbt = new JButton("Clear");
        
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pwdtxt.setEchoChar('•');
        cpwdtxt.setEchoChar('•');
		setLayout(null);
		mainlbl1.setFont(new Font("Matura MT Script Capitals",0,30));
	    mainlbl1.setForeground(new Color(0, 79, 0));
		mainlbl1.setBounds(400,10,400,60);
		fname.setBounds(30,100,90,30);
		fnametxt.setBounds(160,100,150,35);
		img.setBounds(800,100,550,550);
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
		
		jLabel10.setBounds(30,570,120,20);
		cpwdtxt.setBounds(160,570,300,35);

		submitbt.setBounds(100,height-110,110,40);
		cancelbt.setBounds(230,height-110,120,40);
		clrbt.setBounds(370,height-110,100,40);
		newacc.setBounds(510,height-110,150,40);
		
		//Adding components
		cona.add(mainlbl1);	cona.add(img);
		cona.add(fname);
		cona.add(fnametxt);
		cona.add(mname);
		cona.add(mnametxt);
		cona.add(jLabel1);
		cona.add(snametxt);
		cona.add(jLabel2);
		cona.add(dobd);
		cona.add(jLabel3);
		cona.add(dobm);
		cona.add(jLabel4);
		cona.add(doby);
		cona.add(jLabel5);
		cona.add(mailtxt);
		cona.add(jLabel6);
		cona.add(pnotxt);
		cona.add(jLabel7);
		cona.add(adrstxt);
		cona.add(jLabel8);
		cona.add(Lidtxt);
		cona.add(jLabel9);
		cona.add(pwdtxt);
		cona.add(jLabel10);
		cona.add(cpwdtxt);
		cona.add(submitbt);
		cona.add(cancelbt);
		cona.add(newacc);
		cona.add(clrbt);
		setVisible(true);
		
		//Register Listener
		submitbt.addActionListener(this);
		cancelbt.addActionListener(this);
		clrbt.addActionListener(this);
		newacc.addActionListener(this);
	}
	public void generateKeys(int bits)	//complex RSA algorithm
    {
		bitlen = bits;
        SecureRandom r = new SecureRandom();
        BigInteger p = new BigInteger(bitlen / 2, 100, r);
        BigInteger q = new BigInteger(bitlen / 2, 100, r);
        n = p.multiply(q);
        BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        e = new BigInteger("3");	//(e,n);- encrypt  	(d,n);- decrypt
        while(m.gcd(e).intValue() > 1)
        {
			e = e.add(new BigInteger("2"));
        }
        d = e.modInverse(m); //i.e inverse of e % m and assign it to d
    }
	public boolean chkdob()  //checking date
    {   
		int flag=0;
		try
		{
			if((dobd.getText()).equals("") && (dobm.getText()).equals("") && (doby.getText()).equals(""))
			{
				flag = 0;
			}
			else
			{
				int day = Integer.parseInt(dobd.getText());
				int month = Integer.parseInt(dobm.getText());
				int year = Integer.parseInt(doby.getText());           
				if((day>31 && day<1) || (month>12 && month<1) ||(year<1920 && year>2007))
					flag = 1;
				else
				   flag = 0;
			}
		}
		catch(NumberFormatException ne)
		{
			JOptionPane.showMessageDialog(this,"Enter number in date","User Registration",JOptionPane.ERROR_MESSAGE);
		}
		if(flag==1)
			return false;
		else
			return true;
    }
	public boolean chkph()	//checking contact no
	{
		int flag = 1;
		String s = pnotxt.getText();
		if(s.equals(""))
		{}
		else
		{
			if(s.length() < 10 || s.length() > 10)//checking size
				flag = 0;
			//checking content
			for(int z=0;z<s.length();z++)
			{
				if(s.charAt(z)>=0 && s.charAt(z)<=9)
					flag = 0;
			}
		}
		if(flag==1)
			return true;
		else
			return false;
	}
	public boolean mailchk()	//checking mail using regular expr.
	{
		String mailstr = mailtxt.getText();
		int flag = 0;
		if(mailstr.equals(""))
		{}
		else
		{
			Pattern p = Pattern.compile("^[a-zA-Z]+[0-9]*[.]*[_]*[0-9]*[a-zA-Z]*@[a-zA-Z]+\\.([a-zA-Z]+)$");
			Matcher m = p.matcher(mailstr);
			if(m.find())
				flag = 0;//return true;
			else
				flag = 1;//return false;
		}
		if(flag==0)
			return true;
		else
			return false;
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==submitbt)
		{
			try 
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				if(!chkdob())
				{
					JOptionPane.showMessageDialog(this,"Invalid date!","User Registration",JOptionPane.ERROR_MESSAGE);
				}
				else if(!chkph())
				{
					JOptionPane.showMessageDialog(this,"Please check your Contact Number!","User Registration",JOptionPane.ERROR_MESSAGE);
				}
				else if((Lidtxt.getText()).equals(""))
				{	
					JOptionPane.showMessageDialog(this,"Please enter your Login ID","User Registration",JOptionPane.ERROR_MESSAGE);
				}
				else if((pwdtxt.getText()).equals(""))
				{	
					JOptionPane.showMessageDialog(this,"Please Enter Password","User Registration",JOptionPane.ERROR_MESSAGE);
				}
				else if(!pwdtxt.getText().equals(cpwdtxt.getText()))
				{
					
					JOptionPane.showMessageDialog(this,"Password not matched","User Registration",JOptionPane.ERROR_MESSAGE);
				}
				else if(!mailchk())
				{
					JOptionPane.showMessageDialog(this,"Invalid email id","User Registration",JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					con = DriverManager.getConnection("jdbc:oracle:thin:@"+sname+":1521:XE","system","mymotog2O");
					st = con.createStatement();
					fnm = fnametxt.getText();
					mnm = mnametxt.getText();
					snm = snametxt.getText();
					dob = dobd.getText()+"/"+dobm.getText()+"/"+doby.getText();
					email = mailtxt.getText();
					pNo = pnotxt.getText();
					address = adrstxt.getText();
					lid = Lidtxt.getText();
					pwd = pwdtxt.getText();
					cpwd = cpwdtxt.getText();
					String regQuery = "insert into Usign values('"+fnm+"','"+mnm+"','"+snm+"','"+dob+"','"+email+"','"+pNo+"','"+address+"','"+lid+"','"+pwd+"')";
					int r1 = st.executeUpdate(regQuery);
					//generating keys for new User
					generateKeys(512); //calling function
					String s = "insert into keys values('"+lid+"','"+e+"','"+d+"','"+n+"')";
					int r2 = st.executeUpdate(s);
					st.close();
					con.close();
					JOptionPane.showMessageDialog(this,"Submitted sucessfully","User Registration",JOptionPane.INFORMATION_MESSAGE);
				}//else closed
			}//try closed
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(this,"Error : \n"+e,"User Registration",JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(ae.getSource()==cancelbt)
		{
			setVisible(false);
		}
		else if(ae.getSource()==clrbt)
		{
			fnametxt.setText("");
            mnametxt.setText("");
            snametxt.setText("");
            dobd.setText("");
			dobm.setText("");
			doby.setText("");
            mailtxt.setText("");
            pnotxt.setText("");
            adrstxt.setText("");
            Lidtxt.setText("");
            pwdtxt.setText("");
            cpwdtxt.setText("");
		}
		else
		{
			try{
			UPortal u2 = new UPortal();
			u2.lf.setSelectedIndex(index);
			}catch(Exception e){}
			setVisible(false);
		}
	}
}