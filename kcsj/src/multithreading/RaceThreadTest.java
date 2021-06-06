package multithreading;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;

public class RaceThreadTest extends Applet implements ActionListener, ItemListener {
	int j = 0;
	Image image1, image2, image3, image4;
	Label lb1 = new Label("1号:");
	Label lb2 = new Label("2号:");
	Label output = new Label("200米决赛:         ");
	Button b1 = new Button("开始");
	Button b2 = new Button("挂起");
	Button b3 = new Button("唤醒");
	Choice sj = new Choice();
	RaceThread ren1, ren2;
	Thread thread1, thread2;

	public void init() {
		this.setLayout(null);
		setBackground(Color.white);
		this.setForeground(Color.blue);
		image1 = getImage(this.getCodeBase(), "ren1.jpg");
		image2 = getImage(this.getCodeBase(), "ren1.gif");
		image3 = getImage(this.getCodeBase(), "ren2.jpg");
		image4 = getImage(this.getCodeBase(), "ren2.gif");
		ren1 = new RaceThread(image1, image2);
		ren2 = new RaceThread(image3, image4);
		thread1 = new Thread(ren1);
		thread2 = new Thread(ren2);
		thread1.start();
		thread2.start();
		sj.add("1号");
		sj.add("2号");
		sj.setBounds(50, 20, 40, 20);
		b1.setBounds(100, 20, 40, 20);
		b2.setBounds(150, 20, 40, 20);
		b3.setBounds(200, 20, 40, 20);
		add(sj);
		add(b1);
		add(b2);
		add(b3);
		ren1.setBounds(20, 50, 250, 100);
		ren2.setBounds(20, 150, 250, 100);
		add(ren1);
		add(ren2);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		sj.addItemListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "挂起") {
			if (j == 0)
				ren1.z = 0;
			else
				ren2.z = 0;
		}
		if (e.getActionCommand() == "唤醒") {
			if (j == 0)
				ren1.z = 1;
			else
				ren2.z = 1;
		}
		if (e.getActionCommand() == "开始") {
			ren1.x = 10;
			ren1.y = 10;
			ren2.x = 10;
			ren2.y = 10;
			ren1.z = 1;
			ren2.z = 1;
		}
		repaint();
	}

	public void itemStateChanged(ItemEvent e) {
		j = sj.getSelectedIndex();
	}
}