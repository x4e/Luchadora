package cookiedragon.luchadora;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;

/**
 * @author cookiedragon234 17/Feb/2020
 */
public class Main {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		List<Image> icons = Arrays.asList(
			new ImageIcon("icon_16.png").getImage(),
			new ImageIcon("icon_32.png").getImage(),
			new ImageIcon("icon_64.png").getImage());
		frame.setUndecorated(true);
		frame.setIconImages(icons);
		frame.setTitle("Luchadora");
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.toFront();
		frame.setAlwaysOnTop(true);
		JOptionPane.showMessageDialog(
			frame,
			"Please place both Luchadora client and dependencies jar in .minecraft/mods/1.12.2",
			"Luchadora",
			INFORMATION_MESSAGE
		);
		System.exit(0);
	}
}
