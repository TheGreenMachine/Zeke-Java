package com.edinarobotics.zeke.vision;

import com.edinarobotics.utils.log.Level;
import com.edinarobotics.utils.log.LogSystem;
import com.edinarobotics.utils.log.Logger;
import edu.wpi.first.wpilibj.Timer;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.ServerSocketConnection;
import javax.microedition.io.SocketConnection;

/**
 * Singleton server that spawns new {@link ConnectionHandler}s when a connection
 * is received
 */
public class VisionServer implements Runnable {
    private static VisionServer instance;
    
    private Logger zekeLogger;
    private Vector connections;
    private Thread visionServerThread;
    
    private int port;
    private boolean shouldListen = true;
    private boolean isGoalHot = false;
    
    double lastHeartbeatTime = -1.0;
    
    private VisionServer() {
        this.port = 1180; // Default port
        this.visionServerThread = new Thread(this);
        this.connections = new Vector();
        this.zekeLogger = LogSystem.getLogger("zeke");
    }
    
    public void setPort(int port) {
        this.port = port;
    }
    
    public boolean isGoalHot() {
        return isGoalHot;
    }
    
    public void start() {
        visionServerThread.start();
    }
    
    public void stop() {
        shouldListen = false;
    }
    
    public void reset() {
        isGoalHot = false;
    }
    
    public void run() {
        ServerSocketConnection serverSocket = null;
        
        try {
            serverSocket = (ServerSocketConnection) Connector.open("serversocket://:" + port);
            while(shouldListen) {
                SocketConnection socket = (SocketConnection) serverSocket.acceptAndOpen();
                Thread connection = new Thread(new ConnectionHandler(socket));
                connection.start();
                connections.addElement(connection);
                try {
                    Thread.sleep(100); // Pause for 100ms
                } catch (InterruptedException ex) {
                    System.out.println("Thread sleep failed.");
                }
            }
        } catch(IOException e) {
            zekeLogger.log(Level.SEVERE, "Socket failed!");
            e.printStackTrace();
        }
    }
    
    /**
     * Returns a new instance of {@link VisionServer}, creating one if null.
     * @return {@link VisionServer}
     */
    public static VisionServer getInstance() {
        if(instance == null){
            instance = new VisionServer();
        }
        return instance;
    }
    
    private class ConnectionHandler implements Runnable {
        SocketConnection socket;
        private final double timeout = 10.0;

        public ConnectionHandler(SocketConnection socket) {
            this.socket = socket;
        }
    
        public void run() {
            try {
                InputStream inputStream = socket.openInputStream();
                byte[] b = new byte[1024];
                double lastHeartbeat = Timer.getFPGATimestamp();
                VisionServer.this.lastHeartbeatTime = lastHeartbeat;
                
                while (Timer.getFPGATimestamp() < lastHeartbeat + timeout) {
                    while (inputStream.available() > 0) {
                        int inputData = inputStream.read(b);
                        for (int i = 0; i < inputData; ++i) {
                            byte reading = b[i];
                            boolean status = (reading & (1 << 1)) > 0;
                            VisionServer.this.isGoalHot = status;
                        }
                        lastHeartbeat = Timer.getFPGATimestamp();
                        VisionServer.this.lastHeartbeatTime = lastHeartbeat;
                    }
                    try {
                        Thread.sleep(50); // sleep a bit
                    } catch (InterruptedException ex) {
                        System.out.println("Thread sleep failed.");
                    }
                }
                
                inputStream.close();
                socket.close();
            }
            catch(IOException e) {
            }
        }
    }
}
