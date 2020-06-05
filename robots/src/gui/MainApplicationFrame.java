package gui;

import gui.FrameSaver.PaneSaver;
import log.Logger;

import javax.swing.*;
import java.awt.*;

class MainApplicationFrame extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private final PaneSaver saver=new PaneSaver();

    MainApplicationFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width - inset * 2,
                screenSize.height - inset * 2);

        setContentPane(desktopPane);

        var a=desktopPane.getAllFrames();
        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = new GameWindow();
        gameWindow.setSize(400, 400);
        addWindow(gameWindow);

        CoordinateWindow coordWindow = new CoordinateWindow();
        gameWindow.getRobot().addObserver(coordWindow);
        addWindow(coordWindow);
        MenuBarGenerator menuGenerator = new MenuBarGenerator();
        setJMenuBar(menuGenerator.generateMenuBar(this));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        menuGenerator.addExitAction(e-> saver.WriteToFile(desktopPane));
        saver.LoadSettings(this.desktopPane);

    }



    private LogWindow createLogWindow() {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10, 10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }

    private void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }
}
