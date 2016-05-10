package entidades;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import pantalla.Pantalla;

public class Entidad {
	private int x;
	private int y;
	private Pantalla pt;
	private BufferedImage imagen;
	
	public Entidad(int x, int y, Pantalla pt) {
		this.x = x;
		this.y = y;
		this.pt = pt;
	}
	
	public Entidad(String archivo, int x, int y, Pantalla pt) {
		String imagenUrl = "../res/"+archivo+".png";
        try {
			imagen = ImageIO.read(getClass().getResourceAsStream(imagenUrl));
		} catch (IOException e) {
			System.err.println("No se ha podido cargar la imagen "+archivo);
			e.printStackTrace();
		}
        this.x = x;
        this.y = y;
        this.pt = pt;
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
	
	public void setImagen(BufferedImage imagen) {
		this.imagen = imagen;
	}
	
	public Pantalla getPantalla() {
		return pt;
	}
}
