package combate;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import entidades.GUI;
import pantalla.Pantalla;
import principal.EstadoJuego;

public class Combate {
	private Pantalla pt;
	private BufferedImage fondo;
	private Pokemon pj;
	private Pokemon rival;
	private BufferedImage barraIzq;
	private BufferedImage barraDrc;
	private double comienzo;
	
	public Combate(Pantalla pt) {
		this.pt = pt;
		try {
			fondo = ImageIO.read(getClass().getResourceAsStream("../res/combate/fondo.png"));
			barraIzq = ImageIO.read(getClass().getResourceAsStream("../res/combate/barraIzq.png"));
			barraDrc = ImageIO.read(getClass().getResourceAsStream("../res/combate/barraDrc.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		comienzo = 0;
		pj = new Pokemon("pikachu", true, this);
		rival = new Pokemon("caterpie", false, this);
		ponerBotones();
	}
	
	public void lanzarAtaque(String accion) {
		rival.cambiarVida(-1);
	}
	
	public void comenzarCombate() {
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		};
		Timer timer = new Timer(100, listener);
		timer.setRepeats(true);
		timer.start();
	}
	
	private void ponerBotones() {
		int cool = 100;
		GUI cuadrado = new GUI("botones/cuadrado", 4, 0, "cuadrado", cool, pt);
		cuadrado.setY(pt.getHeight() - cuadrado.getY2()-4);
		GUI triangulo = new GUI("botones/triangulo", 4, 0, "triangulo", cool, pt);
		triangulo.setY(cuadrado.getY() - triangulo.getY2()-4);
		GUI equis = new GUI("botones/equis", 0, 0, "equis", cool, pt);
		equis.setY(cuadrado.getY() - (equis.getY2() / 2) -4);
		equis.setX(cuadrado.getX2()+4);
		
		pt.meterGUI(cuadrado);
		pt.meterGUI(triangulo);
		pt.meterGUI(equis);
	}
	
	public void terminar() {		
		pt.getJuego().setEstado(EstadoJuego.REINICIAR);
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
