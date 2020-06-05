package gui;

import gui.Observer.Observer;
import gui.Robot.Robot;

import javax.swing.*;
import java.awt.TextArea;
import java.awt.BorderLayout;

public class CoordinateWindow extends JInternalFrame implements Observer {

    private TextArea textArea;

    public CoordinateWindow() {
        super("Координаты робота", true, true, true, true);
        textArea = new TextArea("");
        textArea.setSize(100, 100);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(textArea, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    @Override
    public void notify(Object obj) {
        try {
            var r = (Robot) obj;
            if(r.enabled())
            textArea.setText(r.getX() + " " + r.getY());
            else
                textArea.setText("robot closed");
        } catch (Exception e) {
        }
    }
}