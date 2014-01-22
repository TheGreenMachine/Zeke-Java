package com.edinarobotics.zeke.subsystems;

import com.edinarobotics.utils.subsystems.Subsystem1816;
import edu.wpi.first.wpilibj.command.Command;

public class DrivetrainStrafe extends Subsystem1816 {
    private double magnitude, direction;
    private Drivetrain drivetrain;
    
    public DrivetrainStrafe(Drivetrain drivetrain) {
        super("DrivetrainStrafe");
        this.drivetrain = drivetrain;
    }
    
    public void setMecanumPolarStrafe(double magnitude, double direction) {
        this.magnitude = magnitude;
        this.direction = direction;
        update();
    }
    
    public void update() {
        drivetrain.mecanumPolarStrafe(magnitude, direction);
    }
    
    public void setDefaultCommand(Command command){
        if(getDefaultCommand() != null){
            super.getDefaultCommand().cancel();
        }
        super.setDefaultCommand(command);
    }

}
