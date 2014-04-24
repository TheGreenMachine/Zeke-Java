package com.edinarobotics.zeke.vision;

import com.edinarobotics.utils.log.Level;
import com.edinarobotics.utils.log.LogSystem;
import com.edinarobotics.utils.log.Logger;
import java.io.IOException;
import javax.microedition.io.ServerSocketConnection;
import javax.microedition.io.SocketConnection;

/**
 *
 * @author GreenMachine
 */
public class VisionConnectThread extends Thread {

    private int port;
    private Logger logger;
    private volatile boolean stop;
    private VisionServer server;
    private VisionReadingThread currentReadingThread;

    public VisionConnectThread(int port, VisionServer server) {
        this.port = port;
        this.logger = LogSystem.getLogger("zeke.vision.conn");
        stop = false;
        this.server = server;
    }

    public void requestStop() {
        this.stop = true;
        if (currentReadingThread != null) {
            currentReadingThread.requestStop();
        }
    }

    public VisionReadingThread getReadingThread() {
        return currentReadingThread;
    }

    public void run() {
        ServerSocketConnection serverSocket = null;
        try {
            while (!stop) {
                if (currentReadingThread != null && currentReadingThread.isAlive()) {
                    currentReadingThread.join();
                }
                SocketConnection connection = (SocketConnection) serverSocket.acceptAndOpen();
                currentReadingThread = new VisionReadingThread(connection, server);
                currentReadingThread.start();
                Thread.sleep(100);
            }
        } catch (InterruptedException ex) {
            logger.log(Level.INFO, "Connect thread interrupted.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to receive new connections.");
            e.printStackTrace();
        } finally {
            // Close the server socket if we need to.
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException ex) {
                    logger.log(Level.SEVERE, "Failed to close server socket.");
                }
            }
        }
        logger.log(Level.INFO, "Vision connect thread exited.");
    }
}
