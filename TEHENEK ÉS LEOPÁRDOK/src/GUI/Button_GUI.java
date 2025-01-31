package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Button_GUI {
	
	//konstruktor
	public Button_GUI() {};
	
	//gombok beállítása
	public void button_options(int height, int width, Color colour, float component, JButton... buttons) {
        Dimension buttonSize = new Dimension(width, height);

        for (JButton button : buttons) {
            button.setPreferredSize(buttonSize);
            button.setMinimumSize(buttonSize);
            button.setMaximumSize(buttonSize);
            button.setBackground(colour);
            button.setAlignmentX(component);
        }
    }
	
	//gombok eseménykezelése
	public void setup_buttons(JFrame frame, Map<JButton, Runnable> buttonActions) {
        for (Map.Entry<JButton, Runnable> entry : buttonActions.entrySet()) {
            JButton button = entry.getKey();
            final Runnable action = entry.getValue();

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    action.run();
                }
            });
        }
	}
	
}
