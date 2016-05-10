package entidades;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import pantalla.Pantalla;

public class GUI extends Entidad {
	
	private String accion;
	private boolean pulsado;
	private int cooldown;
	private BufferedImage imagen_n;
	private BufferedImage imagen_p;

	public GUI(String archivo, int x, int y, Pantalla pt) {
		this(archivo, x, y, archivo, pt);
	}
	
	public GUI(String archivo, int x, int y, String accion, Pantalla pt) {
		this(archivo, x, y, accion, 100, pt);
	}
	
	public GUI(String archivo, int x, int y, String accion, int cooldown, Pantalla pt) {
		super(archivo, x, y, pt);
		try {
			imagen_n = ImageIO.read(getClass().getResourceAsStream("../res/"+archivo+".png"));
			imagen_p = ImageIO.read(getClass().getResourceAsStream("../res/"+archivo+"_p.png"));
		} catch (IOException e) {
			System.err.println("No se ha podido cargar la imagen "+archivo);
			e.printStackTrace();
		}
		this.accion = accion;
		this.cooldown = cooldown;
		pulsado = false;
	}
	
	public void pulsar() {
		this.setImagen(imagen_p);
		pulsado = true;
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setImagen(imagen_n);
				pulsado = false;
			}
		};
		Timer timer = new Timer(cooldown, listener);
		timer.setRepeats(false);
		timer.start();
	}
	
	public String getAccion() {
		return accion;
	}
	
	public boolean getPulsado() {
		return pulsado;
	}
	
	public int getX2() {
		return this.getImagen().getWidth(null) + this.getX();
	}
	
	public int getY2() {
		return this.getImagen().getHeight(null) + this.getY();
	}
	
}
