import java.net.*;
import java.io.*;
import static java.lang.System.out;
import java.sql.*;
import java.math.*;

public class server
{
	public static void main(String args[])throws Exception
	{
		ServerSocket ss1 = new ServerSocket(9999);
		System.out.println("server started..!");
		Socket s1 = ss1.accept();
		String msg,fname,toname;
		ServerSocket ss2 = new ServerSocket(6666);
		Socket s2 = ss2.accept();
		BigInteger d=BigInteger.ONE,n=BigInteger.ONE,decmod=BigInteger.ONE,decprvt=BigInteger.ONE;
		BufferedReader br1 = new BufferedReader(new InputStreamReader(s1.getInputStream()));
		PrintWriter pw1 = new PrintWriter(s1.getOutputStream(),true);
		String client1 = br1.readLine();
		BufferedReader br2 = new BufferedReader(new InputStreamReader(s2.getInputStream()));
		PrintWriter pw2 = new PrintWriter(s2.getOutputStream(),true);
		String client2 = br2.readLine();
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","prem");
		Statement st = con.createStatement();			
		ResultSet rs = st.executeQuery("select * from keys");
		while(true)
		{
			msg = br1.readLine();
			out.println(msg);
			fname = br1.readLine();
			out.println(fname);
			toname = br1.readLine();
			while(rs.next())
			{
				String ln = rs.getString(1);
				if(fname.equals(ln))
				{
					d = new BigInteger(rs.getString(3));
					n = new BigInteger(rs.getString(4));
				}
			}
			pw2.println(fname);
			pw2.println(msg);
			pw2.println(d);
			pw2.println(n);
			
			msg = br2.readLine();
			fname = br2.readLine();
			out.println(fname);
			toname = br2.readLine();
			while(rs.next())
			{
				String ln = rs.getString(1);
				if(fname.equals(ln))
				{
					decprvt = new BigInteger(rs.getString(3));
					decmod = new BigInteger(rs.getString(4));
				}
			}
			pw1.println(fname);
			pw1.println(msg);
			pw1.println(decprvt);
			pw1.println(decmod);
		}
	}
}