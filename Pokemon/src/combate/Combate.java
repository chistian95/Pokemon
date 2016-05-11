package combate;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Combate {
	private BufferedImage fondo;
	private Pokemon pj;
	private Pokemon rival;
	private BufferedImage barraIzq;
	private BufferedImage barraDrc;
	private double comienzo;
	
	public Combate() {
		try {
			fondo = ImageIO.read(getClass().getResourceAsStream("../res/combate/fondo.png"));
			barraIzq = ImageIO.read(getClass().getResourceAsStream("../res/combate/barraIzq.png"));
			barraDrc = ImageIO.read(getClass().getResourceAsStream("../res/combate/barraDrc.png"));
			comienzo = 0;
			pj = new Pokemon("pikachu", true);
			rival = new Pokemon("caterpie", false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void aumentarCont() {
		comienzo+=4;
	}
	
	
	public BufferedImage getFondo() {
		return fondo;
	}
	
	public Pokemon getPj() {
		return pj;
	}
	
	public Pokemon getRival() {
		return rival;
	}
	
	public BufferedImage getBarraIzq() {
		return barraIzq;
	}
	
	public BufferedImage getBarraDrc() {
		return barraDrc;
	}
	
	public double getComienzo() {
		return comienzo;
	}
}
