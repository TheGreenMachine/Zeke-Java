package com.edinarobotics.zeke;

import com.edinarobotics.zeke.subsystems.Collector;
import com.edinarobotics.zeke.subsystems.Drivetrain;
import com.edinarobotics.zeke.subsystems.DrivetrainRotation;
import com.edinarobotics.zeke.subsystems.DrivetrainStrafe;
import com.edinarobotics.zeke.subsystems.Shooter;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class Components {
    
    private static Components instance;
    
    // Subsystems
    public final Drivetrain drivetrain;
    public final DrivetrainStrafe drivetrainStrafe;
    public final DrivetrainRotation drivetrainRotation; 
    public final Shooter shooter;
    public final Collector collector;
    // END Subsystems
    
    // Compressor
    public final Compressor compressor;
    
    // Analog Inputs
        private static final int ULTRASONIC_SENSOR = 4;
        private static final int SHOOTER_POT_PORT = 3;
    // END Analog Inputs
    
    // Digital IO Constants
        // Drivetrain Encoders
        private static final DigitalInput FRONT_LEFT_A  = new DigitalInput(1, 1);
        private static final DigitalInput FRONT_LEFT_B  = new DigitalInput(2, 1);
        private static final DigitalInput REAR_LEFT_A   = new DigitalInput(1, 2);
        private static final DigitalInput REAR_LEFT_B   = new DigitalInput(2, 2);
        private static final DigitalInput FRONT_RIGHT_A = new DigitalInput(1, 3);
        private static final DigitalInput FRONT_RIGHT_B = new DigitalInput(2, 3);
        private static final DigitalInput REAR_RIGHT_A  = new DigitalInput(1, 4);
        private static final DigitalInput REAR_RIGHT_B  = new DigitalInput(2, 4);
        
        // Limit Switches
        private static final int SHOOTER_LOWER_LIMIT = 6;
        
        // Compressor Switch
        private static final int COMPRESSOR_PRESSURE_SWITCH = 5;
    // END Digital IO constants
    
    // PWM constants
        // Drivetrain
        private static final int FRONT_LEFT_DRIVE  = 4;
        private static final int REAR_LEFT_DRIVE   = 3;
        private static final int FRONT_RIGHT_DRIVE = 2;
        private static final int REAR_RIGHT_DRIVE  = 1;
        private static final int WINCH_TALON = 5;
        private static final int COLLECTOR_FRONT_TALON = 6;
        private static final int COLLECTOR_BACK_TALON = 7;
    // END PWM constants
        
    // Solenoid constants
        // Collector
        private static final int COLLECTOR_DOUBLESOLENOID_FORWARD = 2;
        private static final int COLLECTOR_DOUBLESOLENOID_REVERSE = 1;
        private static final int COLLECTOR_DOUBLESOLENOID_VALVE_OFF = 5;
        private static final int COLLECTOR_DOUBLESOLENOID_VALVE_ON = 6;
        // Shooter
        private static final int SHOOTER_DOUBLESOLENOID_FORWARD = 3;
        private static final int SHOOTER_DOUBLESOLENOID_REVERSE = 4;
        // Pusher
        private static final int PUSHER_DOUBLESOLENOID_FORWARD = 7;
        private static final int PUSHER_DOUBLESOLENOID_REVERSE = 8;
    // END Solenoid constants
        
    // Relay constats
        // Compressor
        private static final int COMPRESSOR_RELAY = 1;
    // END Relay constants
    
    
    /**
     * Private constructor for the Components singleton. This constructor
     * is only called once and handles creating all the robot subsystems.
     */
    private Components(){
        drivetrain = new Drivetrain(FRONT_LEFT_DRIVE, REAR_LEFT_DRIVE,
                FRONT_RIGHT_DRIVE, REAR_RIGHT_DRIVE, ULTRASONIC_SENSOR);
        drivetrainStrafe = new DrivetrainStrafe(drivetrain);
        drivetrainRotation = new DrivetrainRotation(drivetrain);
        shooter = new Shooter(WINCH_TALON, SHOOTER_DOUBLESOLENOID_FORWARD,
                SHOOTER_DOUBLESOLENOID_REVERSE, PUSHER_DOUBLESOLENOID_FORWARD,
                PUSHER_DOUBLESOLENOID_REVERSE, SHOOTER_POT_PORT, SHOOTER_LOWER_LIMIT);
        collector = new Collector(COLLECTOR_FRONT_TALON, COLLECTOR_BACK_TALON, 
                    COLLECTOR_DOUBLESOLENOID_FORWARD, COLLECTOR_DOUBLESOLENOID_REVERSE,
                    COLLECTOR_DOUBLESOLENOID_VALVE_ON, COLLECTOR_DOUBLESOLENOID_VALVE_OFF);
        compressor = new Compressor(COMPRESSOR_PRESSURE_SWITCH, COMPRESSOR_RELAY);
        compressor.start();
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
