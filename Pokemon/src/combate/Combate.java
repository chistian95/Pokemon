package combate;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import entidades.Entidad;
import entidades.GUI;
import pantalla.Pantalla;
import principal.EstadoJuego;

public class Combate {
	private static final int DIFICULTAD_VIDA = -3;
	private static final int DIFICULTAD_ATAQUE = 200;
	private static final int DIFICULTAD_CAMBIO = 550;
	
	private Pantalla pt;
	private BufferedImage fondo;
	private Pokemon pj;
	private Pokemon rival;
	private BufferedImage barraIzq;
	private BufferedImage barraDrc;
	private BufferedImage imgTriangulo;
	private BufferedImage imgCuadrado;
	private BufferedImage imgEquis;
	private String accionRival;
	private String accionPj;
	private Entidad accion;
	private Timer cambiarAtaque;
	private Timer atacar;
	private double comienzo;
	
	public Combate(Pantalla pt) {
		accionPj = "";
		accionRival = "cuadrado";
		this.pt = pt;
		try {
			fondo = ImageIO.read(getClass().getResourceAsStream("../res/combate/fondo.png"));
			barraIzq = ImageIO.read(getClass().getResourceAsStream("../res/combate/barraIzq.png"));
			barraDrc = ImageIO.read(getClass().getResourceAsStream("../res/combate/barraDrc.png"));
			imgTriangulo = ImageIO.read(getClass().getResourceAsStream("../res/botones/triangulo.png"));
			imgCuadrado = ImageIO.read(getClass().getResourceAsStream("../res/botones/cuadrado.png"));
			imgEquis = ImageIO.read(getClass().getResourceAsStream("../res/botones/equis.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		comienzo = 0;
		pj = new Pokemon("pikachu", true, this);
		rival = new Pokemon("caterpie", false, this);
		ponerBotones();
	}
	
	public void lanzarAtaque(String accion) {
		accionPj = accion;
		if(accionPj.equals(accionRival)) {
			rival.cambiarVida(DIFICULTAD_VIDA);
		}			
	}
	
	public void comenzarCombate() {
		ActionListener listenerCambiarAtaque = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rnd = (int) (Math.random()*3);
				switch(rnd) {
				case 0:
					accionRival = "triangulo";
					accion.setImagen(imgTriangulo);
					break;
				case 1:
					accionRival = "cuadrado";
					accion.setImagen(imgCuadrado);
					break;
				case 2:
					accionRival = "equis";
					accion.setImagen(imgEquis);
					break;
				}
			}
		};
		ActionListener listenerAtaque = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!accionRival.equals(accionPj)) {
					pj.cambiarVida(DIFICULTAD_VIDA);
				}
			}
		};
		cambiarAtaque = new Timer(DIFICULTAD_CAMBIO, listenerCambiarAtaque);
		cambiarAtaque.setRepeats(true);
		cambiarAtaque.start();
		atacar = new Timer(DIFICULTAD_ATAQUE, listenerAtaque);
		atacar.setRepeats(true);
		atacar.start();
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
		
		double x = pt.getWidth() - 100;
		double y = 100;
		accion = new Entidad("botones/cuadrado", (int) x, (int) y, pt);
		pt.meterEntidad(accion);
	}
	
	public void terminar() {		
		cambiarAtaque.stop();
		atacar.stop();
		pt.getJuego().setEstado(EstadoJuego.FIN_BATALLA);
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
