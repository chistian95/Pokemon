package combate;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Pokemon {
	String pokemon;
	BufferedImage img;
	Combate combate;
	private int vida;
	
	public Pokemon(String pokemon, boolean back, Combate combate) {
		this.combate = combate;
		this.pokemon = pokemon;		
		try {
			if(back) {
				img = ImageIO.read(getClass().getResourceAsStream("../res/pokemon/back/"+pokemon+".png"));
			} else {
				img = ImageIO.read(getClass().getResourceAsStream("../res/pokemon/"+pokemon+".png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		vida = 100;
	}
	
	public int getVida() {
		return vida;
	}
	
	public void cambiarVida(int n) {
		vida += n;
		if(vida < 0) {
			vida = 0;
			combate.terminar();
		} else if(vida > 100) {
			vida = 100;
		}
	}
	
	public BufferedImage getImg() {
		return img;
	}
}
