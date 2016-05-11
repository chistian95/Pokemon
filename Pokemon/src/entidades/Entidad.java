package entidades;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import pantalla.Pantalla;

public class Entidad {
	private double escala;
	private int x;
	private int y;
	private Pantalla pt;
	private BufferedImage imagen;
	
	public Entidad(int x, int y, Pantalla pt) {
		this(x, y, 1, pt);
	}
	
	public Entidad(int x, int y, double escala, Pantalla pt) {
		this.escala = escala;
		this.x = x;
		this.y = y;
		this.pt = pt;
	}
	
	public Entidad(String archivo, int x, int y, Pantalla pt) {
		this(archivo, x, y, 1, pt);
	}
	
	public Entidad(String archivo, int x, int y, double escala, Pantalla pt) {
		String imagenUrl = "res/"+archivo+".png";
        try {
			imagen = ImageIO.read(ClassLoader.getSystemResourceAsStream(imagenUrl));
		} catch (IOException e) {
			System.err.println("No se ha podido cargar la imagen "+archivo);
			e.printStackTrace();
		}
        this.escala = escala;
        this.x = x;
        this.y = y;
        this.pt = pt;
	}
	
	public void moverConSuelo() {
		x+=4;
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
	
	public double getEscala() {
		return escala;
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
