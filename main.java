import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
class setting extends Dialog
{
	JButton jb1;
	public setting(main up)
	{
		super(up,"Settings",false);
		jb1 = new JButton("looks");
		setLayout(new FlowLayout());
		add(jb1);
	}
}
class main extends JFrame implements ActionListener
{
	JButton jb1;
	main()
	{
		jb1 = new JButton("open");
		setLayout(new FlowLayout());
		add(jb1);
		jb1.addActionListener(this);
	}
	public void actionPerformed(ActionEvent ae)
	{
		setting s = new setting(new main());
		s.show();
	}
	public static void main(String args[])
	{
		main mn = new main();
		mn.setVisible(true);
		mn.setSize(700,600);
	}
}