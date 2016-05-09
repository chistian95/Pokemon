package entidades;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Entidad {
	private int x;
	private int y;
	private Image imagen;
	
	public Entidad(String archivo, int x, int y) {
		String imagenUrl = "../res/"+archivo+".png";
        try {
			imagen = ImageIO.read(getClass().getResourceAsStream(imagenUrl));
		} catch (IOException e) {
			System.err.println("No se ha podido cargar la imagen "+archivo);
			e.printStackTrace();
		}
        this.x = x;
        this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Image getImagen() {
		return imagen;
	}
	
	public void setImagen(String archivo) {
		String imagenUrl = "../res/"+archivo+".png";
        try {
			imagen = ImageIO.read(getClass().getResourceAsStream(imagenUrl));
		} catch (IOException e) {
			System.err.println("No se ha podido cargar la imagen "+archivo);
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
