package pantalla;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.Timer;

import entidades.EntidadAnimada;
import entidades.EntidadControlable;
import entidades.GUI;
import principal.AnimacionBatalla;
import principal.Juego;

public class Pantalla extends JFrame implements Runnable, KeyListener, MouseListener {
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 1280;	//720, 1280;
	private static final int HEIGHT = 720;	//480, 720;
	
	private Juego jg;
	private BufferedImage bf;
	private List<EntidadAnimada> animadas;
	private List<GUI> guis;
	private List<EntidadControlable> controlables;
	private boolean[] raton;
	private Fondo fondo;
	private Terreno terreno;
	private AnimacionBatalla animBat;
	
	public Pantalla(Juego jg) {
		this.jg = jg;
		animadas = new ArrayList<EntidadAnimada>();
		guis = new ArrayList<GUI>();
		controlables = new ArrayList<EntidadControlable>();
		raton = new boolean[2];
		fondo = new Fondo();
		terreno = new Terreno(this);
		animBat = null;
		
        setUndecorated(true);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);
        
        bf = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        //bf.setAccelerationPriority(1);
        
        addKeyListener(this);
		addMouseListener(this);
        
        addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent e) {
        		dispose();
        		System.exit(0);
        	}
        });
	}
	
	
	public void paint(Graphics g) {
		switch(jg.getEstado()) {
		case -1:
			break;
		case 0:
			pintarMundo(g);
			break;
		case 1:
			pintarBatallaAnimacion(g);
			break;
		}	
	}
	
	private void pintarBatallaAnimacion(Graphics g) {
		Graphics2D bff = (Graphics2D) bf.getGraphics();
		
		if(animBat == null) {
			animBat = new AnimacionBatalla(this);
		}
		
		double escala = animBat.getCont();
		int width = animBat.getImagen().getWidth(this)/2;
		int height = animBat.getImagen().getHeight(this)/2;
		int x1 = getWidth()/2 - (int) (width*escala);
		int y1 = getHeight()/2 - (int) (height*escala);
		
		if(escala <= 2.125) {
			AffineTransform trans = new AffineTransform();
			trans.translate(x1, y1);
			trans.scale(escala, escala);
			trans.rotate(Math.toRadians(escala*180), width, height);
			bff.drawImage(animBat.getImagen(), trans, this);
		}
		animBat.sumarCont();
		if(escala >= 4) {
			animBat = null;
			jg.setEstado(0);
		}
		
		g.drawImage(bf, 0, 0, null);
	}
	
	private void pintarMundo(Graphics g) {
		Graphics2D bff = (Graphics2D) bf.getGraphics();
		bff.drawImage(fondo.getCielo(), 0, 0, this.getWidth(), 250, 0, 0, this.getWidth(), 250, this);
		bff.drawImage(fondo.getNubes(), 0, 0, this.getWidth(), 250, fondo.getX(), 0, fondo.getX()+this.getWidth(), 250, this);
		fondo.moverNubes();
		
		bff.drawImage(terreno.getMontes().getImagen(), 0, 180, this.getWidth(), this.getHeight(), terreno.getMontes().getX(), 180, terreno.getMontes().getX()+this.getWidth(), this.getHeight(), this);
		bff.drawImage(terreno.getBosque().getImagen(), 0, 120, this.getWidth(), this.getHeight(), terreno.getBosque().getX(), 120, terreno.getBosque().getX()+this.getWidth(), this.getHeight(), this);
		bff.drawImage(terreno.getImagen(), 0, 180, this.getWidth(), this.getHeight(), terreno.getX(), 180, terreno.getX()+this.getWidth(), this.getHeight(), this);
		
		for(EntidadAnimada entidad : animadas) {
			bff.drawImage(entidad.getImagen(), entidad.getX(), entidad.getY(), this);
		}
		
		boolean mov = false;
		for(EntidadControlable entidad : controlables) {
			if(entidad.getPasos() > 0) {
				entidad.animacion();			
			}
			if(entidad.getAnimacion() > 0) {
				mov = true;
			}
			int escala = entidad.getEscala();
			int dx1 = entidad.getX() - 8*escala;
			int dy1 = entidad.getY() - 10*escala;
			int dx2 = (dx1 + (16*escala));
			int dy2 = (dy1 + (20*escala));
			int sx1 = entidad.getSx();
			int sy1 = 0;
			int sx2 = sx1+16;
			int sy2 = sy1+20;
			bff.drawImage(entidad.getImagen(), dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, this);
		}
		if(mov) {
			terreno.moverTerreno();
		}
		
		for(GUI gui : guis) {
			bff.drawImage(gui.getImagen(), gui.getX(), gui.getY(), this);
		}
		g.drawImage(bf, 0, 0, null);
	}
	
	@Override
	public void run() {
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				botones();
				repaint();   
			}
		};
		Timer timer = new Timer(20, listener);
		timer.setRepeats(true);
		timer.start();
	}
	
	private void botones() {
		if(raton[0]) {
			Point posicion = this.getMousePosition();
			for(GUI gui : guis) {
				if(!gui.getPulsado() && posicion.getX() >= gui.getX() && posicion.getX() <= gui.getX2() && posicion.getY() >= gui.getY() && posicion.getY() <= gui.getY2()) {
					gui.pulsar();
					accionBoton(gui);
				}
			}
			raton[0] = false;
		}
	}
	
	private void accionBoton(GUI boton) {
		if(boton.getAccion().equals("circulo")) {
			if(jg.getEstado() == 0) {
				for(EntidadControlable en : controlables) {
					if(en.getPasos() <= 1) {
						en.animar();
					}				
				}
			}			
		}
	}

	//TECLADO
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
		if(e.getKeyCode() == KeyEvent.VK_W) {
			for(GUI gui : guis) {
				if(gui.getAccion().equals("triangulo")) {
					if(!gui.getPulsado()) {
						gui.pulsar();
						accionBoton(gui);
					}
					break;
				}
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_A) {
			for(GUI gui : guis) {
				if(gui.getAccion().equals("cuadrado")) {
					if(!gui.getPulsado()) {
						gui.pulsar();
						accionBoton(gui);
					}
					break;
				}
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_D) {
			for(GUI gui : guis) {
				if(gui.getAccion().equals("equis")) {
					if(!gui.getPulsado()) {
						gui.pulsar();
						accionBoton(gui);
					}
					break;
				}
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_L) {
			for(GUI gui : guis) {
				if(gui.getAccion().equals("circulo")) {
					if(!gui.getPulsado()) {
						gui.pulsar();
						accionBoton(gui);
					}
					break;
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	//RATON
	private void estadoRaton(MouseEvent e, boolean estado) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			raton[0] = estado;
		}
		if(e.getButton() == MouseEvent.BUTTON2) {
			raton[1] = estado;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		estadoRaton(e, true);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		estadoRaton(e, false);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}
	
	public void meterEntidadAnimada(EntidadAnimada en) {
		animadas.add(en);
	}
	
	public void quitarEntidadAnimada(EntidadAnimada en) {
		animadas.remove(en);
	}
	
	public void meterGUI(GUI gui) {
		guis.add(gui);
	}
	
	public void quitarGUI(GUI gui) {
		guis.remove(gui);
	}
	
	public void meterEntidadControlable(EntidadControlable en) {
		controlables.add(en);
	}
	
	public void quitarEntidadControlable(EntidadControlable en) {
		controlables.remove(en);
	}
	
	public Juego getJuego() {
		return jg;
	}
}
