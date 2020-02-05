public class Pantalla
{
public static void lineaA(int ancho)
	{
	for(int i=0;i<5;i++)
	System.out.print(" ");
	System.out.print("É");
	for(int i=0;i<ancho-2;i++)
	System.out.print("Í");
	System.out.print("»");
	System.out.println();
	}
public static void lineaB(int ancho)
	{
	for(int i=0;i<5;i++)
	System.out.print(" ");
	System.out.print("È");
	for(int i=0;i<ancho-2;i++)
	System.out.print("Í");
	System.out.print("¼");
	System.out.println();
	}
public static void dosBlanco()
	{
	System.out.println();
	System.out.println();
	}
public static void Lineas(int ancho)
	{
	for(int x=0;x<2;x++)
		{
		for(int i=0;i<5;i++)
		System.out.print(" ");
		for(int i=0;i<ancho;i++)
		System.out.print("Í");
		System.out.println();
		System.out.println();
		}
	}
public static void Mensaje(int ancho,String mensaje)
	{
	ancho=ancho-2;
	for(int i=0;i<5;i++)
	System.out.print(" ");
	System.out.print("º");
	for(int i=0;i<(ancho-mensaje.length())/2;i++)
	System.out.print(" ");
	if (mensaje.length()%2!=0)
	mensaje=mensaje+" ";
	System.out.print(mensaje);
	for(int i=0;i<(ancho-mensaje.length())/2;i++)
	System.out.print(" ");
	System.out.print("º");
	System.out.println();
	}
public static void verticales(int ancho)
	{
	for(int x=0;x<2;x++)
		{
		for(int i=0;i<5;i++)
		System.out.print(" ");
		System.out.print("º");
		for(int i=0;i<ancho-2;i++)
		System.out.print(" ");
		System.out.print("º");
		System.out.println();
		}
	}
public static void rectangulo(int ancho,String mensaje)
	{
	Pantalla.lineaA(ancho);
	Pantalla.verticales(ancho);
	Pantalla.Mensaje(ancho,mensaje);
	Pantalla.verticales(ancho);
	Pantalla.lineaB(ancho);
	}
}