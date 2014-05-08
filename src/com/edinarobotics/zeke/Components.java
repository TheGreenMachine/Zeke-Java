package com.edinarobotics.zeke;

import com.edinarobotics.zeke.subsystems.Collector;
import com.edinarobotics.zeke.subsystems.Drivetrain;
import com.edinarobotics.zeke.subsystems.DrivetrainRotation;
import com.edinarobotics.zeke.subsystems.DrivetrainStrafe;
import com.edinarobotics.zeke.subsystems.Shooter;
import com.edinarobotics.zeke.vision.VisionServer;
import edu.wpi.first.wpilibj.Compressor;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class Components {
    private static Components instance;
    
    public static final boolean IS_PRACTICEBOT = true; //Practice bot switch
    private RobotComponentsMapping mapping;
    
    // Subsystems
    public final Drivetrain drivetrain;
    public final DrivetrainStrafe drivetrainStrafe;
    public final DrivetrainRotation drivetrainRotation; 
    public final Shooter shooter;
    public final Collector collector;
    // END Subsystems
    
    //Vision Server
    public final VisionServer visionServer;
    
    // Compressor
    public final Compressor compressor;
    
    /**
     * Private constructor for the Components singleton. This constructor
     * is only called once and handles creating all the robot subsystems.
     */
    private Components(){
        if(IS_PRACTICEBOT){
            mapping = new PracticeRobotComponentsMapping();
        }
        else{
            mapping = new RobotComponentsMapping();
        }
        drivetrain = new Drivetrain(mapping.FRONT_LEFT_DRIVE, mapping.REAR_LEFT_DRIVE,
                mapping.FRONT_RIGHT_DRIVE, mapping.REAR_RIGHT_DRIVE, mapping.ULTRASONIC_SENSOR);
        drivetrainStrafe = new DrivetrainStrafe(drivetrain);
        drivetrainRotation = new DrivetrainRotation(drivetrain);
        shooter = new Shooter(mapping.WINCH_TALON, mapping.SHOOTER_DOUBLESOLENOID_FORWARD,
                mapping.SHOOTER_DOUBLESOLENOID_REVERSE, mapping.PUSHER_DOUBLESOLENOID_FORWARD,
                mapping.PUSHER_DOUBLESOLENOID_REVERSE, mapping.SHOOTER_LOWER_LIMIT);
        collector = new Collector(mapping.COLLECTOR_FRONT_TALON, mapping.COLLECTOR_BACK_TALON, 
                    mapping.COLLECTOR_DOUBLESOLENOID_FORWARD, mapping.COLLECTOR_DOUBLESOLENOID_REVERSE,
                    mapping.COLLECTOR_DOUBLESOLENOID_VALVE_ON, mapping.COLLECTOR_DOUBLESOLENOID_VALVE_OFF);
        compressor = new Compressor(mapping.COMPRESSOR_PRESSURE_SWITCH, mapping.COMPRESSOR_RELAY);
        compressor.start();
        visionServer = VisionServer.getInstance();
        visionServer.setPort(mapping.VISION_SERVER_PORT);
    }
    
    /**
     * Returns a new instance of {@link Components}, creating one if null.
     * @return {@link Components}
     */
    public static Components getInstance() {
        if(instance == null){
            instance = new Components();
        }
        return instance;
    }
    
}
