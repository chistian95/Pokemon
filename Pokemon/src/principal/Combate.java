package principal;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Combate {
	private BufferedImage fondo;
	private BufferedImage pj;
	private BufferedImage rival;
	private double comienzo = 0;
	
	public Combate() {
		try {
			fondo = ImageIO.read(getClass().getResourceAsStream("../res/combate/fondo.png"));
			pj = ImageIO.read(getClass().getResourceAsStream("../res/pokemon/back/pikachu.png"));
			rival = ImageIO.read(getClass().getResourceAsStream("../res/pokemon/caterpie.png"));
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
	
	public Image getPj() {
		return pj;
	}
	
	public Image getRival() {
		return rival;
	}
	
	public double getComienzo() {
		return comienzo;
	}
}
