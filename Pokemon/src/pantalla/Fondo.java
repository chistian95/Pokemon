package pantalla;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Fondo {
	private static final int VELOCIDAD = 2;
	
	Image cielo;
	Image nubes;
	int x = 1240;
	int cont = 0;

	public Fondo() {
		try {
			cielo = ImageIO.read(getClass().getResourceAsStream("../res/fondo/cielo.png"));
			nubes = ImageIO.read(getClass().getResourceAsStream("../res/fondo/nubes.png"));
		} catch (IOException e) {
			System.err.println("No se han podido cargar las imagenes de fondo");
			e.printStackTrace();
		}
	}
	
	public Image getCielo() {
		return cielo;
	}
	
	public Image getNubes() {
		return nubes;
	}
	
	public int getX() {
		return x;
	}
	
	public void moverNubes() {
		cont = cont%VELOCIDAD + 1;
		if(cont == VELOCIDAD) {
			x--;
		}
		if(x <= 0) {
			x = 1240;
		}
	}
}
