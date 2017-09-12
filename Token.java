import java.util.*;
class Token
{
  public static void main(String args[])
  {
     String str1="Hello Prem Kumar";
	 StringTokenizer str=new StringTokenizer(str1," ");
	 while(str.hasMoreTokens())
	 {
	    System.out.println(" "+str.nextToken());
	}
}	
}	