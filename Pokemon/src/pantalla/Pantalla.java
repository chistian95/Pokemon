package pantalla;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import entidades.Entidad;
import entidades.Fondo;

public class Pantalla extends JFrame implements Runnable, KeyListener, MouseListener {
	private static final long serialVersionUID = 1L;
	private static final int REFRESCO = 1000;
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	
	private List<Entidad> entidades;
	private List<GUI> guis;
	private boolean[] raton;
	private Fondo fondo;
	
	public Pantalla() {
		entidades = new ArrayList<Entidad>();
		guis = new ArrayList<GUI>();
		raton = new boolean[2];
		fondo = new Fondo();
		addKeyListener(this);
		addMouseListener(this);
		
        setUndecorated(true);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent e) {
        		dispose();
        		System.exit(0);
        	}
        });
	}
	
	public void meterEntidad(Entidad en) {
		entidades.add(en);
	}
	
	public void quitarEntidad(Entidad en) {
		entidades.remove(en);
	}
	
	public void meterGUI(GUI gui) {
		guis.add(gui);
	}
	
	public void quitarGUI(GUI gui) {
		guis.remove(gui);
	}
	
	public void paint(Graphics g) {
		BufferedImage bf = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		super.paint(bf.getGraphics());
		
		bf.getGraphics().drawImage(fondo.getCielo(), 0, 0, this.getWidth(), this.getHeight(), this);
		bf.getGraphics().drawImage(fondo.getNubes(), fondo.getX(), 0, this);
		fondo.moverNubes();
		
		for(Entidad entidad : entidades) {				
			bf.getGraphics().drawImage(entidad.getImagen(), entidad.getX(), entidad.getY(), this);
		}
		
		for(GUI gui : guis) {
			bf.getGraphics().drawImage(gui.getImagen(), gui.getX(), gui.getY(), this);
		}
		
		g.drawImage(bf, 0, 0, null);
	}
	
	@Override
	public void run() {
		while(true) {
			long comienzo = System.nanoTime();
			botones();
			repaint();
			long resto = System.nanoTime() - comienzo;
			long espera = (REFRESCO - resto) / 1000000;
			if(espera <= 0) {
				espera = 5;
			}
			try {
				Thread.sleep(espera);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	        
	        
		}
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
		System.out.println(boton.getAccion());
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
}
