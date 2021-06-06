package multithreading;

import java.awt.*;

public class RaceThread extends Panel implements Runnable {
	int x = 10, y = 10, z = 0;
	Image image, image1, image2;

	RaceThread(Image image1, Image image2) {
		this.setBackground(Color.white);
		this.image1 = image1;
		this.image2 = image2;
		image = image1;
	}

	public void paint(Graphics g) {
		g.drawImage(image, x, y, this);
	} 
	public void run() {
		while (true) {
			switch (z) {
			case 0:
				image = image1;
				break;
			case 1:
				image = image2;
				x += (int) (6 + 10 * Math.random());
				if (x > 200)
					z = 0;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			repaint();
		}
	}
}