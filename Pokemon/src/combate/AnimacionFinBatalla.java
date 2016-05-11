package combate;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AnimacionFinBatalla {
	private int cont;
	private Font fuente;
	private BufferedImage caja;
	
	public AnimacionFinBatalla() {
		cont = 0;
		fuente = new Font("Verdana", Font.BOLD, 32);
		try {
			caja = ImageIO.read(getClass().getResourceAsStream("../res/combate/contenedor.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getCont() {
		return cont;
	}
	
	public void sumarCont() {
		cont+=4;
	}
	
	public Font getFuente() {
		return fuente;
	}
	
	public BufferedImage getCaja() {
		return caja;
	}
	
}
