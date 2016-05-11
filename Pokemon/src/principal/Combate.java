package principal;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Combate {
	private BufferedImage fondo;
	private BufferedImage pj;
	private BufferedImage rival;
	private BufferedImage barraIzq;
	private BufferedImage barraDrc;
	private int vidaPj;
	private int vidaEnemigo;
	private double comienzo;
	
	public Combate() {
		try {
			fondo = ImageIO.read(getClass().getResourceAsStream("../res/combate/fondo.png"));
			pj = ImageIO.read(getClass().getResourceAsStream("../res/pokemon/back/pikachu.png"));
			rival = ImageIO.read(getClass().getResourceAsStream("../res/pokemon/caterpie.png"));
			barraIzq = ImageIO.read(getClass().getResourceAsStream("../res/combate/barraIzq.png"));
			barraDrc = ImageIO.read(getClass().getResourceAsStream("../res/combate/barraDrc.png"));
			comienzo = 0;
			vidaPj = 100;
			vidaEnemigo = 100;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void aumentarCont() {
		comienzo+=4;
	}
	
	public void quitarVidaPj(int n) {
		vidaPj -= n;
		if(vidaPj < 0) {
			vidaPj = 0;
		}
	}
	
	public void quitarVidaEnemigo(int n) {
		vidaEnemigo -= n;
		if(vidaEnemigo < 0) {
			vidaEnemigo = 0;
		}
	}
	
	public BufferedImage getFondo() {
		return fondo;
	}
	
	public BufferedImage getPj() {
		return pj;
	}
	
	public BufferedImage getRival() {
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
	
	public int getVidaPj() {
		return vidaPj;
	}
	
	public int getVidaEnemigo() {
		return vidaEnemigo;
	}
}
