class Presentacion
{
public static void main(String[]args)
{
Presentacion.lineaga();
Presentacion.solo();
Presentacion.solo();
Presentacion.lineap();
Presentacion.solo();
Presentacion.lineap();
Presentacion.solo();
Presentacion.lineama();
Presentacion.soloa();
Presentacion.presenta();
Presentacion.soloa();
Presentacion.lineamb();
Presentacion.solo();
Presentacion.lineap();
Presentacion.solo();
Presentacion.lineap();
Presentacion.solo();
Presentacion.solo();
Presentacion.lineagb();
}
public static void lineap()
{
System.out.print("º");
for(int i=0;i<14;i++)
System.out.print(" ");
for(int i=0;i<50;i++)
System.out.print("Í");
for(int i=0;i<14;i++)
System.out.print(" ");
System.out.print("º");
}
public static void lineamb()
{
System.out.print("º");
for(int i=0;i<4;i++)
System.out.print(" ");
System.out.print("È");
for(int i=0;i<68;i++)
System.out.print("Í");
System.out.print("¼");
for(int i=0;i<4;i++)
System.out.print(" ");
System.out.print("º");
}
public static void lineagb()
{
System.out.print("È");
for(int i=0;i<78;i++)
System.out.print("Í");
System.out.print("¼");
}
public static void presenta()
{
String mensaje="MOMENTO JUAN JOSE \"lo mac\"";
if (mensaje.length()%2!=0)
mensaje=mensaje+" ";
int espacio=(68-mensaje.length())/2;
System.out.print("º");
for(int i=0;i<4;i++)
System.out.print(" ");
System.out.print("º");
for(int i=0;i<espacio;i++)
System.out.print(" ");
System.out.print(mensaje);
for(int i=0;i<espacio;i++)
System.out.print(" ");
System.out.print("º");
for(int i=0;i<4;i++)
System.out.print(" ");
System.out.print("º");
}
public static void solo()
{
System.out.print("º");
for(int i=0;i<78;i++)
System.out.print(" ");
System.out.print("º");
}
public static void lineaga()
{
System.out.print("É");
for(int i=0;i<78;i++)
System.out.print("Í");
System.out.print("»");
}
public static void lineama()
{
System.out.print("º");
for(int i=0;i<4;i++)
System.out.print(" ");
System.out.print("É");
for(int i=0;i<68;i++)
System.out.print("Í");
System.out.print("»");
for(int i=0;i<4;i++)
System.out.print(" ");
System.out.print("º");
}
public static void soloa()
{
System.out.print("º");
for(int i=0;i<4;i++)
System.out.print(" ");
System.out.print("º");
for(int i=0;i<68;i++)
System.out.print(" ");
System.out.print("º");
for(int i=0;i<4;i++)
System.out.print(" ");
System.out.print("º");
}
}
