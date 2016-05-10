package principal;

import java.awt.Image;

import javax.swing.ImageIcon;

import pantalla.Pantalla;

public class AnimacionBatalla {
	private Image imagen;
	private double cont;
	
	public AnimacionBatalla(Pantalla pt) {
		ImageIcon img = new ImageIcon("src/res/combate/pokeball.png");
		imagen = img.getImage();

		cont = 0.01;
	}
	
	public double getCont() {
		return cont;
	}
	
	public void sumarCont() {
		cont+=0.02;
	}
	
	public Image getImagen() {
		return imagen;
	}
}
