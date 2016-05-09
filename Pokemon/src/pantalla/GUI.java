package pantalla;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import entidades.Entidad;

public class GUI extends Entidad {
	
	private String archivo;
	private String accion;
	private boolean pulsado;
	private int cooldown;

	public GUI(String archivo, int x, int y) {
		this(archivo, x, y, archivo);
	}
	
	public GUI(String archivo, int x, int y, String accion) {
		this(archivo, x, y, accion, 100);
	}
	
	public GUI(String archivo, int x, int y, String accion, int cooldown) {
		super(archivo, x, y);
		this.archivo = archivo;
		this.accion = accion;
		this.cooldown = cooldown;
		pulsado = false;
	}
	
	public void pulsar() {
		this.setImagen(archivo+"_p");
		pulsado = true;
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setImagen(archivo);
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
