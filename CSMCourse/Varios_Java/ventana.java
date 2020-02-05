import java.awt.*;
import java.awt.event.*;

public class ventana
{
public static Button b=new Button("Cerrar");
public static Frame f=new Frame("Ventana");
public static void main(String[]args)
{
f.setSize(200,200);
f.add(b);
f.show();
}
}

