import java.io.*;
public class Lee
{
	public static void main(String []args) throws Exception
	{
		FileOutputStream fis=new FileOutputStream("Test.txt");
		String text="Texto de prueba";
		char []texto=text.toCharArray();
		for(int i=0;i<texto.length;i++)
		fis.write((byte)texto[i]);
	}
}