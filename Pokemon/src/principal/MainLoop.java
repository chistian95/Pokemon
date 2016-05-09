package principal;

import pantalla.GUI;
import pantalla.Pantalla;

public class MainLoop {
	public static void main(String[] args) {
		Pantalla pt = new Pantalla();
		Thread trPt = new Thread(pt);
		trPt.start();
		
		GUI cuadrado = new GUI("botones/cuadrado", 0, 0, "cuadrado");
		cuadrado.setY(pt.getHeight() - cuadrado.getY2());
		GUI triangulo = new GUI("botones/triangulo", 0, 0, "triangulo");
		triangulo.setY(cuadrado.getY() - triangulo.getY2());
		GUI equis = new GUI("botones/equis", 0, 0, "equis");
		equis.setY(cuadrado.getY() - (equis.getY2() / 2));
		equis.setX(cuadrado.getX2());
		GUI circulo = new GUI("botones/circulo", 0, 0, "circulo");
		circulo.setX(pt.getWidth() - circulo.getX2());
		circulo.setY(cuadrado.getY() - (circulo.getY2() / 2));
		
		pt.meterGUI(cuadrado);
		pt.meterGUI(triangulo);
		pt.meterGUI(equis);
		pt.meterGUI(circulo);
	}
}
