import java.io.*;
public class Cesar
{
	public static BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in) ); 
	public static void main (String args[])throws Exception
	{
		String cifrado="cifrado";
		String descifrado="descifrado";
		String salir="salir";
		int opcion=0;
		do
		{
			System.out.println("Introduzca cifrado o descifrado .(SALIR)");
			String operacion=teclado.readLine();
			if(operacion.equalsIgnoreCase(salir))
			{
				System.out.println("Adios...");
				opcion=1;
				return;
			}
			if(!operacion.equalsIgnoreCase(descifrado)&&!operacion.equalsIgnoreCase(cifrado))
			System.out.println("Opcion no valida.");
			else
			{
				System.out.println("Introduzca la frase .");
				String frase=teclado.readLine();
				if(operacion.equalsIgnoreCase(cifrado))
				System.out.println(cifradoCesar(frase));
				if(operacion.equalsIgnoreCase(descifrado))
				System.out.println(descifradoCesar(frase));
			}
		}
		while(opcion==0);
	}
	public static String cifradoCesar(String f)
	{
		char [] letras=f.toCharArray();
		for(int i=0;i<letras.length;i++)
		{
			if (letras [i]>='a' && letras [i]<='z')
			{
				letras[i]+=3;
				if(letras[i]>'z')
				letras[i]-=26;
			}
			if (letras [i]>='A' && letras [i]<='Z')
			{
				letras[i]+=3;
                		if(letras[i]>'Z')
				letras[i]-=26;
			}
		}
		String cif=String.valueOf(letras);
		return cif;
	}
	public static String descifradoCesar(String f)
	{
		char [] letras=f.toCharArray();
		for(int i=0;i<letras.length;i++)
		{
			if (letras [i]>='a' && letras [i]<='z')
			{
				letras[i]-=3;
				if(letras[i]<'a')
				letras[i]+=26;
			}
			if (letras [i]>='A' && letras [i]<='Z')
			{
				letras[i]-=3;
                		if(letras[i]<'A')
				letras[i]+=26;
			}
		}
		String cif=String.valueOf(letras);
		return cif;
	}
}