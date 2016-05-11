package pantalla;

import java.awt.Color;
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

import combate.AnimacionBatalla;
import combate.AnimacionFinBatalla;
import combate.Combate;
import entidades.Entidad;
import entidades.EntidadControlable;
import entidades.GUI;
import principal.EstadoJuego;
import principal.Juego;

public class Pantalla extends JFrame implements KeyListener, MouseListener {
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 720;	//720, 1280;
	private static final int HEIGHT = 480;	//480, 720;
	
	private Juego jg;
	private BufferedImage bf;
	private List<Entidad> entidades;
	private List<GUI> guis;
	private List<EntidadControlable> controlables;
	private boolean[] raton;
	private Fondo fondo;
	private Terreno terreno;
	private AnimacionBatalla animBat;
	private Combate combate;
	private AnimacionFinBatalla finBatalla;
	
	public Pantalla(Juego jg) {
		this.jg = jg;
		entidades = new ArrayList<Entidad>();
		guis = new ArrayList<GUI>();
		controlables = new ArrayList<EntidadControlable>();
		raton = new boolean[2];
		fondo = new Fondo();
		terreno = new Terreno(this);
		animBat = null;
		combate = null;
		finBatalla = null;
		
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
		case EstadoJuego.REINICIAR:
			reiniciarJuego(g);
			break;
		case EstadoJuego.MUNDO:
			pintarMundo(g);
			break;
		case EstadoJuego.ANIM_BATALLA:
			pintarBatallaAnimacion(g);
			break;
		case EstadoJuego.BATALLA_COMIENZO:
			pintarBatallaComienzo(g);
			break;
		case EstadoJuego.BATALLA:
			pintarBatalla(g);
			break;
		case EstadoJuego.FIN_BATALLA:
			pintarFinBatalla(g);
			break;
		}		
	}
	
	private void pintarFinBatalla(Graphics g) {
		Graphics2D bff = (Graphics2D) bf.getGraphics();
		
		if(finBatalla == null) {
			finBatalla = new AnimacionFinBatalla();
			bff.setColor(Color.BLACK);
			bff.fillRect(0, 0, WIDTH, HEIGHT);
		}
		
		bff.drawImage(combate.getFondo(), 0, 0, WIDTH, HEIGHT, this);		
		pintarPjBatalla(bff);
		pintarRvBatalla(bff);
		
		if(finBatalla.getCont() < 200) {
			String mensaje = "HAS GANADO!";
			if(combate.getPj().getVida() <= 0) {
				mensaje = "HAS PERDIDO";
			}
			int x = getWidth()/2-(mensaje.length()/2)*22 - 200 + finBatalla.getCont();
			bff.drawImage(finBatalla.getCaja(), x-14, getHeight()/2-60, this);
			bff.setColor(Color.WHITE);
			bff.setFont(finBatalla.getFuente());
			bff.drawString(mensaje, x, getHeight()/2);
		} else if(finBatalla.getCont() < 600) {
			String mensaje = "HAS GANADO!";
			if(combate.getPj().getVida() <= 0) {
				mensaje = "HAS PERDIDO";
			}
			int x = getWidth()/2-(mensaje.length()/2)*22 - 200 + 200;
			bff.drawImage(finBatalla.getCaja(), x-14, getHeight()/2-60, this);
			bff.setColor(Color.WHITE);
			bff.setFont(finBatalla.getFuente());
			bff.drawString(mensaje, x, getHeight()/2);
		} else {
			jg.setEstado(EstadoJuego.REINICIAR);
		}
		finBatalla.sumarCont();
		
		g.drawImage(bf, 0, 0, null);
	}
	
	private void pintarBatalla(Graphics g) {
		Graphics2D bff = (Graphics2D) bf.getGraphics();
		
		bff.drawImage(combate.getFondo(), 0, 0, WIDTH, HEIGHT, this);
		
		pintarPjBatalla(bff);
		pintarRvBatalla(bff);
		
		for(Entidad en : entidades) {
			bff.drawImage(en.getImagen(), en.getX(), en.getY(), this);
		}
		
		for(GUI gui : guis) {
			bff.drawImage(gui.getImagen(), gui.getX(), gui.getY(), this);
		}
		
		g.drawImage(bf, 0, 0, null);
	}
	
	private void pintarBatallaComienzo(Graphics g) {
		Graphics2D bff = (Graphics2D) bf.getGraphics();
		
		if(combate == null) {
			guis = new ArrayList<GUI>();
			combate = new Combate(this);			
		}
		
		bff.drawImage(combate.getFondo(), 0, 0, WIDTH, HEIGHT, this);
		
		pintarPjBatalla(bff);	
		pintarRvBatalla(bff);
		
		if(combate.getComienzo() <= 100) {
			combate.aumentarCont();
		} else {			
			getJuego().setEstado(EstadoJuego.BATALLA);
			combate.comenzarCombate();
		}
		
		g.drawImage(bf, 0, 0, null);
	}
	
	private void pintarPjBatalla(Graphics2D bff) {
		double escala = 4;
		AffineTransform afPj = new AffineTransform();
		afPj.translate((-100+combate.getComienzo()), HEIGHT - (combate.getPj().getImg().getHeight(null)*escala) + 100);
		afPj.scale(escala, escala);
		bff.drawImage(combate.getPj().getImg(), afPj, this);
		
		escala = 0.35;
		AffineTransform afBarraDrc = new AffineTransform();
		afBarraDrc.translate(WIDTH-(combate.getBarraDrc().getWidth()*escala) + (100-combate.getComienzo()), HEIGHT-200);
		afBarraDrc.scale(escala, escala);
		
		int vida = combate.getPj().getVida();
		int verde = 255 - (100 - vida)*2;
		int rojo = 255 - verde/2;
		bff.setColor(new Color(rojo, verde, 0, 255));
		int x1 = (int) (WIDTH-(combate.getBarraDrc().getWidth()* escala) + (100-combate.getComienzo())) + 25 + (int) ((100-vida)*2.2);
		int y1 = HEIGHT-200 + 10;
		int x2 = (int) (combate.getBarraDrc().getWidth()*escala) - 45 - (int) ((100-vida)*2.2);
		int y2 = 15;
		bff.fillRect(x1, y1, x2, y2);
		bff.drawImage(combate.getBarraDrc(), afBarraDrc, this);
	}
	
	private void pintarRvBatalla(Graphics2D bff) {
		double escala = 2;
		AffineTransform afRv = new AffineTransform();
		afRv.translate(WIDTH-(combate.getRival().getImg().getWidth()*escala) - 50 + (100-combate.getComienzo()), combate.getRival().getImg().getHeight() - 25);
		afRv.scale(escala, escala);
		bff.drawImage(combate.getRival().getImg(), afRv, this);
		
		escala = 0.35;
		AffineTransform afBarraIzq = new AffineTransform();
		afBarraIzq.translate(combate.getComienzo()-100, 75);
		afBarraIzq.scale(escala, escala);
		
		int vida = combate.getRival().getVida();
		int verde = 255 - (100 - vida)*2;
		int rojo = 255 - verde/2;
		bff.setColor(new Color(rojo, verde, 0, 255));
		int x1 = (int) combate.getComienzo()-100 + 25;
		int y1 = 75 + 10;
		int x2 = (int) (combate.getBarraIzq().getWidth()*escala) - 45 - (int) ((100-vida)*2.2);
		int y2 = 15;
		bff.fillRect(x1, y1, x2, y2);
		bff.drawImage(combate.getBarraIzq(), afBarraIzq, this);
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
		if(escala > 2.125) {
			bff.setColor(new Color(0, 0, 0, (int) (escala*20)));
			bff.fillRect(0, 0, WIDTH, HEIGHT);
		}
		if(escala >= 3) {
			animBat = null;
			jg.setEstado(EstadoJuego.BATALLA_COMIENZO);
		}
		
		g.drawImage(bf, 0, 0, null);
	}
	
	private void pintarMundo(Graphics g) {
		Graphics2D bff = (Graphics2D) bf.getGraphics();
		bff.drawImage(fondo.getCielo(), 0, 0, this.getWidth(), this.getHeight()/3, 0, 0, this.getWidth(), this.getHeight()/3, this);
		bff.drawImage(fondo.getNubes(), 0, 0, this.getWidth(), this.getHeight()/3, fondo.getX(), 0, fondo.getX()+this.getWidth(), this.getHeight()/3, this);
		fondo.moverNubes();
		
		bff.drawImage(terreno.getMontes().getImagen(), 0, 120, this.getWidth(), this.getHeight(), terreno.getMontes().getX(), 120, terreno.getMontes().getX()+this.getWidth(), this.getHeight(), this);
		bff.drawImage(terreno.getBosque().getImagen(), 0, 60, this.getWidth(), this.getHeight(), terreno.getBosque().getX(), 60, terreno.getBosque().getX()+this.getWidth(), this.getHeight(), this);
		bff.drawImage(terreno.getImagen(), 0, 120, this.getWidth(), this.getHeight(), terreno.getX(), 120, terreno.getX()+this.getWidth(), this.getHeight(), this);
		
		for(Entidad entidad : entidades) {
			AffineTransform af = new AffineTransform();
			af.translate(entidad.getX(), entidad.getY());
			af.scale(entidad.getEscala(), entidad.getEscala());
			bff.drawImage(entidad.getImagen(), af, this);
		}
		
		boolean mov = false;
		for(EntidadControlable entidad : controlables) {
			if(entidad.getPasos() > 0) {
				entidad.animacion();			
			}
			if(entidad.getAnimacion() > 0) {
				mov = true;
			}
			int escala = (int) (entidad.getEscala());
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
	
	public void comenzar() {
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
	
	private void reiniciarJuego(Graphics g) {		
		Graphics2D bff = (Graphics2D) bf.getGraphics();
		bff.setColor(Color.BLACK);
		bff.fillRect(0, 0, WIDTH, HEIGHT);
		g.drawImage(bf, 0, 0, null);
		entidades = new ArrayList<Entidad>();
		guis = new ArrayList<GUI>();
		controlables = new ArrayList<EntidadControlable>();
		raton = new boolean[2];
		fondo = new Fondo();
		terreno = new Terreno(this);
		animBat = null;
		combate = null;
		finBatalla = null;
		
		GUI circulo = new GUI("botones/circulo", 0, 0, "circulo", this);
		circulo.setX(getWidth() - circulo.getX2() -4);
		circulo.setY(getHeight() - circulo.getY2() -4);
		meterGUI(circulo);
		
		EntidadControlable pj = new EntidadControlable("sprites/pj", getWidth()/2, getHeight()-100, this);	
		meterEntidadControlable(pj);
		jg.setEstado(EstadoJuego.MUNDO);
	}
	
	private void botones() {
		if(raton[0]) {
			Point posicion = this.getMousePosition();
			for(GUI gui : guis) {
				if(!gui.isPulsado() && posicion.getX() >= gui.getX() && posicion.getX() <= gui.getX2() && posicion.getY() >= gui.getY() && posicion.getY() <= gui.getY2()) {
					gui.pulsar();
					accionBoton(gui);
				}
			}
			raton[0] = false;
		}
	}
	
	private void accionBoton(GUI boton) {
		if(boton.getAccion().equals("circulo")) {
			if(jg.getEstado() == EstadoJuego.MUNDO) {
				for(EntidadControlable en : controlables) {
					if(en.getPasos() <= 1) {
						en.animar();
					}				
				}
			}			
		} else if(jg.getEstado() == EstadoJuego.BATALLA) {
			int cont = 0;
			for(GUI gui : guis) {
				if(gui.isPulsado()) {
					cont++;
				}
			}
			if(cont <= 1) {
				combate.lanzarAtaque(boton.getAccion());
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
					if(!gui.isPulsado()) {
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
					if(!gui.isPulsado()) {
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
					if(!gui.isPulsado()) {
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
					if(!gui.isPulsado()) {
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
