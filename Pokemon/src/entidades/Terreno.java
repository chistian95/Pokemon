package entidades;

public class Terreno extends Entidad {
	private Entidad monte;
	private Entidad bosque;

	public Terreno() {
		super("fondo/suelo", 1280, 0);
		bosque = new Entidad("fondo/suelo2", 1280, 0);
		monte = new Entidad("fondo/montes", 1280, 0);
	}
	
	public void moverTerreno() {
		setX(getX()-4);
		if(getX() <= 0) {
			setX(1280);
		}
		bosque.setX(bosque.getX()-3);
		if(bosque.getX() <= 0) {
			bosque.setX(1280);
		}
		monte.setX(monte.getX()-2);
		if(monte.getX() <= 0) {
			monte.setX(1280);
		}
	}
	
	public Entidad getMontes() {
		return monte;
	}
	
	public Entidad getBosque() {
		return bosque;
	}
}
