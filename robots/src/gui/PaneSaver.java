package gui;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;

public class FrameSaver {
    private static String pathToSaves = System.getProperty("user.home") + "/RobotSaves/";

    public static void WriteToFile(JDesktopPane desktopPane) {

        for (var frame : desktopPane.getAllFrames()) {
            var file = new File(pathToSaves + frame.getTitle() + ".txt");
            try {
                if (!file.exists())
                    file.createNewFile();

                var outputStream = new FileOutputStream(file);
                var dataOutputStream = new DataOutputStream(new BufferedOutputStream(outputStream));
                dataOutputStream.writeUTF(frame.getTitle());
                dataOutputStream.writeInt(frame.getX());
                dataOutputStream.writeInt(frame.getY());
                dataOutputStream.writeInt(frame.getWidth());
                dataOutputStream.writeInt(frame.getHeight());
                dataOutputStream.writeBoolean(frame.isMaximum());
                dataOutputStream.writeBoolean(frame.isIcon());
                dataOutputStream.close();
                outputStream.close();
            } catch (Exception e) {

            }
        }
    }

    public static HashMap<String, SavableFrame> ReadFrameStates() {
        var frameStates = new HashMap<String, SavableFrame>();
        var file = new File(pathToSaves);
        for (var frameFile : file.listFiles()) {
            try {

                var inputStream = new FileInputStream(frameFile);
                var dataInputStream = new DataInputStream(new BufferedInputStream(inputStream));
                var title = dataInputStream.readUTF();
                var x = dataInputStream.readInt();
                var y = dataInputStream.readInt();
                var width = dataInputStream.readInt();
                var height = dataInputStream.readInt();
                var isMaximum = dataInputStream.readBoolean();
                var isIcon = dataInputStream.readBoolean();
                frameStates.put(title, new SavableFrame(x, y, width, height, isMaximum, isIcon));
                dataInputStream.close();
                inputStream.close();
            } catch (Exception e) {
            }

        }
        return frameStates;
    }

    public static void ModifyDesktopPaneState(JDesktopPane desktopPane) {
        HashMap<String, SavableFrame> frameStates = ReadFrameStates();
        for (var frame : desktopPane.getAllFrames()) {
            if (!frameStates.containsKey(frame.getTitle()))
                continue;

            var newFrameState = frameStates.get(frame.getTitle());
            frame.setLocation(new Point(newFrameState.x, newFrameState.y));
            frame.setSize(newFrameState.width, newFrameState.height);
            try {
                frame.setIcon(newFrameState.isIcon);
                frame.setMaximum(newFrameState.isMaximum);
            } catch (PropertyVetoException e) {
                e.printStackTrace();
            }
        }
    }
}