package entidades;

import java.awt.Image;

import javax.swing.ImageIcon;

public class EntidadAnimada {
	private int x;
	private int y;
	private Image imagen;
	
	public EntidadAnimada(String archivo, int x, int y) {
		ImageIcon img = new ImageIcon("src/res/"+archivo+".gif");
		imagen = img.getImage();
		
		this.x = x;
		this.y = y;
	}
	
	public void moverEntidad() {
		x+=4;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Image getImagen() {
		return imagen;
	}
}
