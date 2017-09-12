 // Chat Server runs at port no. 9999
import java.io.*;
import java.sql.*;
import java.util.*;
import java.net.*;
import java.math.*;

public class ChatServer
{
   Vector<String> users = new Vector<String>();
   Vector<HandleClient> clients = new Vector<HandleClient>();
   String touname="";
   BigInteger n,d;
	public void process() throws Exception  
	{
		ServerSocket server = new ServerSocket(9998,10);
		System.out.println("Server Started...");
		while(true)
		{
			Socket client = server.accept();
			HandleClient c = new HandleClient(client);
			clients.add(c);
		}  
	}
	public static void main(String[]args) throws Exception 
	{
		ChatServer cs = new ChatServer();
		cs.process();
	} // end of main
	public void boradcast(String user,String fname,String tname,String message) throws Exception
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","prem","rex");
		Statement st = con.createStatement();
		touname = tname;
		System.out.println("in brodcast()...!");
		System.out.println(""+user+","+fname+","+tname+","+message);
		for (HandleClient c : clients)
		{
			String currname = c.getUserName();
			System.out.println("in handleClien()...!");
			System.out.println("currname : "+currname);
			ResultSet rs = st.executeQuery("select * from keys");
			while(rs.next())
			{
				String ln = rs.getString(1);
				if(fname.equals(ln))
				{
					d = new BigInteger(rs.getString(3));
					n = new BigInteger(rs.getString(4));
				}
			}
			if(currname.equals(touname))
			{
				c.send(user,message,n,d);
			}//outer if closed
		}//for closed
	}
	class HandleClient extends Thread
	{
		String name = "",toUname="",fromuname="";
		BufferedReader input;
		PrintWriter output;
		public HandleClient(Socket client) throws Exception 
		{
			// get input and output streams
			input = new BufferedReader(new InputStreamReader(client.getInputStream()));
			output = new PrintWriter(client.getOutputStream(),true);
			// read name
			name = input.readLine();
			users.add(name); // add to vector
			start();
        }        
		public void send(String uname,String msg,BigInteger n,BigInteger d)
		{
			System.out.println("in Send()..!");
			output.println(uname);
			output.println(msg);
			output.println(n);
			output.println(d);
		}
        public String getUserName() 
		{  
			return name;
        }
        public void run()  
		{
			try{
    	    String line;
			while(true) 
            {
				line = input.readLine();
				fromuname = input.readLine();
				touname = input.readLine();
				if(line.equals("end")) 
				{
					clients.remove(this);
					users.remove(name);
					break;
				}
				boradcast(name,fromuname,toUname,line); // method  of outer class - send messages to all
			} // end of while
			}catch(Exception e){}
        } // end of run()
   } // end of inner class
} //