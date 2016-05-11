package combate;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import pantalla.Pantalla;

public class AnimacionBatalla {
	private BufferedImage imagen;
	private double cont;
	
	public AnimacionBatalla(Pantalla pt) {
		try {
			imagen = ImageIO.read(ClassLoader.getSystemResourceAsStream("res/combate/pokeball.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		cont = 0.01;
	}
	
	public double getCont() {
		return cont;
	}
	
	public void sumarCont() {
		cont+=0.02;
	}
	
	public BufferedImage getImagen() {
		return imagen;
	}
}
