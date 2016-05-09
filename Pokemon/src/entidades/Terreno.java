package entidades;

public class Terreno extends Entidad {

	public Terreno() {
		super("fondo/suelo", -1240, 0);
	}
	
	public void moverTerreno() {
		setX(getX()+2);
		if(getX() >= 0) {
			setX(-1240);
		}
	}
}
