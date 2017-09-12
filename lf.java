import javax.swing.*;
import static java.lang.System.out;
class lf
{
	public static void main(String args[])
	{
		for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) 
		{
			out.println(""+info.getName());
			out.println(""+info.getClassName());
		}
	}
}