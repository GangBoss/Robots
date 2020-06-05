package gui;

import gui.Robot.Robot;

import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class GameWindow extends JInternalFrame {
    private Robot robot;
    private final GameVisualizer m_visualizer;

    public GameWindow() {
        super("Игровое поле", true, true, true, true);
        robot = new Robot();
        addInternalFrameListener(
                new InternalFrameAdapter() {
                    public void internalFrameClosing(InternalFrameEvent e) {
                        robot.exit();
                    }
                });
        m_visualizer = new

                GameVisualizer(robot);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);

        getContentPane().

                add(panel);

        pack();
    }

    public Robot getRobot() {
        return robot;
    }
}
