
package com.edinarobotics.zeke;

import com.edinarobotics.utils.gamepad.FilteredGamepad;
import com.edinarobotics.utils.gamepad.Gamepad;
import com.edinarobotics.utils.gamepad.gamepadfilters.*;
import com.edinarobotics.zeke.commands.SetShooterCommand;
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
        driveGamepadFilters.addElement(new GamepadDeadzoneFilter(0.1));
        driveGamepadFilters.addElement(new GamepadPowerFilter(2));
        GamepadFilterSet driveGamepadFilterSet = new GamepadFilterSet(driveGamepadFilters);
        gamepad1 = new FilteredGamepad(1, driveGamepadFilterSet);
        
        Vector shootGamepadFilters = new Vector();
        shootGamepadFilters.addElement(new GamepadDeadzoneFilter(0.1));
        shootGamepadFilters.addElement(new GamepadPowerFilter(2));
        GamepadFilterSet shootGamepadFilterSet = new GamepadFilterSet(shootGamepadFilters);
        gamepad2 = new FilteredGamepad(2, shootGamepadFilterSet);
        
        gamepad2.rightBumper().whenPressed(new SetShooterCommand(Shooter.WinchState.LOWERING));
        gamepad2.rightBumper().whenReleased(new SetShooterCommand(Shooter.WinchState.STOPPED));
        gamepad2.leftBumper().whenPressed(new SetShooterCommand(Shooter.WinchState.FREE));
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

