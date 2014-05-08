package com.edinarobotics.zeke.vision;

import com.edinarobotics.utils.log.LogSystem;
import com.edinarobotics.utils.log.Logger;

public class VisionServer {

    private static VisionServer instance;
    private int port;
    private Logger logger;
    private VisionConnectThread connThread;
    private int counts;
    private boolean requireSequential;

    private VisionServer() {
        this.port = 1180;
        this.logger = LogSystem.getLogger("zeke.vision");
        counts = 0;
        requireSequential = false;
    }

    public void setPort(int port) {
        this.port = port;
        start();
    }

    public static VisionServer getInstance() {
        if (instance == null) {
            instance = new VisionServer();
        }
        return instance;
    }
    
    public void setRequireSequential(boolean requireSequential){
        this.requireSequential = requireSequential;
    }

    public void start() {
        stop();
        connThread = new VisionConnectThread(this.port, this);
        connThread.start();
    }

    public void stop() {
        if (connThread != null && connThread.isAlive()) {
            connThread.requestStop();
            try {
                connThread.join();
                VisionReadingThread readThread = connThread.getReadingThread();
                if (readThread != null && readThread.isAlive()) {
                    readThread.requestStop();
                    readThread.join();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void resetCounts() {
        counts = 0;
    }

    public VisionConnectThread getConnectThread() {
        return connThread;
    }

    public void incrementCount() {
        counts++;
    }

    public void reportHotGoal(boolean isHot) {
        if(requireSequential && !isHot){
            resetCounts();
        }
        if (isHot) {
            incrementCount();
        }
    }

    public int getCount() {
        return counts;
    }
}
