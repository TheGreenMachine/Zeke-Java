
package com.edinarobotics.zeke;

import com.edinarobotics.utils.gamepad.FilteredGamepad;
import com.edinarobotics.utils.gamepad.Gamepad;
import com.edinarobotics.utils.gamepad.gamepadfilters.*;
import com.edinarobotics.zeke.commands.LowerShooterToHeightCommand;
import com.edinarobotics.zeke.commands.SetCollectorCommand;
import com.edinarobotics.zeke.commands.SetPusherCommand;
import com.edinarobotics.zeke.commands.SetShooterCommand;
import com.edinarobotics.zeke.commands.ShootingSequenceCommand;
import com.edinarobotics.zeke.subsystems.Collector.CollectorState;
import com.edinarobotics.zeke.subsystems.Collector.CollectorWheelState;
import com.edinarobotics.zeke.subsystems.Shooter;
import java.util.Vector;
/**
 * Controls handles creating the {@link Gamepad} objects used to control the
 * robot as well as binding the proper Commands to button actions.
 */
public class Controls {

    private static Controls instance;
    public final Gamepad gamepad1;
    public final Gamepad gamepad2;
    
    private Controls() {
        Vector driveGamepadFilters = new Vector();
        driveGamepadFilters.addElement(new DeadzoneFilter(0.1));
        driveGamepadFilters.addElement(new PowerFilter(2));
        GamepadFilterSet driveGamepadFilterSet = new GamepadFilterSet(driveGamepadFilters);
        gamepad1 = new FilteredGamepad(1, driveGamepadFilterSet);
        
        Vector shootGamepadFilters = new Vector();
        driveGamepadFilters.addElement(new DeadzoneFilter(0.1));
        driveGamepadFilters.addElement(new PowerFilter(2));
        GamepadFilterSet shootGamepadFilterSet = new GamepadFilterSet(shootGamepadFilters);
        gamepad2 = new FilteredGamepad(2, shootGamepadFilterSet);
        
        // Collector controls
        gamepad2.leftTrigger().whenPressed(new SetCollectorCommand(CollectorState.DEPLOYED, CollectorWheelState.STOPPED));
        gamepad2.leftBumper().whenPressed(new SetCollectorCommand(CollectorState.RETRACTED, CollectorWheelState.STOPPED));
        gamepad2.dPadUp().whenPressed(new SetCollectorCommand(CollectorState.DEPLOYED, CollectorWheelState.REVERSING));
        gamepad2.dPadUp().whenReleased(new SetCollectorCommand(CollectorState.DEPLOYED, CollectorWheelState.STOPPED));
        gamepad2.dPadDown().whenPressed(new SetCollectorCommand(CollectorState.DEPLOYED, CollectorWheelState.COLLECTING));
        gamepad2.dPadDown().whenReleased(new SetCollectorCommand(CollectorState.DEPLOYED, CollectorWheelState.STOPPED));
        gamepad2.dPadRight().whenPressed(new SetCollectorCommand(CollectorState.DEPLOYED, CollectorWheelState.HOLDING));
        gamepad2.dPadRight().whenReleased(new SetCollectorCommand(CollectorState.DEPLOYED, CollectorWheelState.STOPPED));
        gamepad2.dPadLeft().whenPressed(new SetCollectorCommand(CollectorState.DEPLOYED, CollectorWheelState.HOLDING));
        gamepad2.dPadLeft().whenReleased(new SetCollectorCommand(CollectorState.DEPLOYED, CollectorWheelState.STOPPED));
        
        // Shooter controls
        gamepad2.rightTrigger().whenPressed(new SetShooterCommand(Shooter.WinchState.FREE));
        gamepad2.rightBumper().whenPressed(new LowerShooterToHeightCommand(Shooter.FIRING_HEIGHT));
        gamepad2.middleRight().whenPressed(new ShootingSequenceCommand(true));
        
    }
    
    /**
     * Returns the proper instance of Controls. This method creates a new
     * Controls object the first time it is called and returns that object for
     * each subsequent call.
     *
     * @return The current instance of Controls.
     */
    public static Controls getInstance() {
        if (instance == null) {
            instance = new Controls();
        }
        return instance;
    }
}

