package com.edinarobotics.zeke.vision;

import com.edinarobotics.utils.log.Level;
import edu.wpi.first.wpilibj.Timer;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.io.SocketConnection;

public class VisionReadingThread extends Thread {

    private volatile boolean stop;
    private SocketConnection connection;
    private VisionServer server;
    private static final double TIMEOUT = 10.0;

    public VisionReadingThread(SocketConnection connection, VisionServer server) {
        stop = false;
        this.connection = connection;
        this.server = server;
    }

    public void requestStop() {
        this.stop = true;
    }

    public void run() {
        InputStream inputStream = null;
        try {
            inputStream = connection.openInputStream();
            byte[] readBytes = new byte[5];
            double lastHeartbeat = Timer.getFPGATimestamp();
            while (!stop) {
                try {
                    if (Timer.getFPGATimestamp() > lastHeartbeat + TIMEOUT) {
                        requestStop();
                        continue;
                    }
                    int inputData = inputStream.read(readBytes);
                    if(inputData == -1){
                        requestStop();
                    }
                    for (int i = 0; i < inputData; i++) {
                        byte reading = readBytes[i];
                        boolean status = reading != 0;
                        this.server.reportHotGoal(status);
                    }
                    lastHeartbeat = Timer.getFPGATimestamp();
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            try {
                this.connection.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
