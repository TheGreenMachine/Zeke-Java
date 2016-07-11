package com.edinarobotics.zeke;

import java.util.ArrayList;
import java.util.List;

import com.edinarobotics.utils.gamepad.FilteredGamepad;
import com.edinarobotics.utils.gamepad.Gamepad;
import com.edinarobotics.utils.gamepad.gamepadfilters.DeadzoneFilter;
import com.edinarobotics.utils.gamepad.gamepadfilters.GamepadFilter;
import com.edinarobotics.utils.gamepad.gamepadfilters.GamepadFilterSet;
import com.edinarobotics.utils.gamepad.gamepadfilters.PowerFilter;
import com.edinarobotics.zeke.commands.FireShooterCommand;
import com.edinarobotics.zeke.commands.LowerShooterCommand;
import com.edinarobotics.zeke.commands.RunCollectorCommand;

public class Controls {
    
	private static Controls instance;
	public Gamepad gamepad;
	
	private Controls() {
		List<GamepadFilter> gamepadFilters = new ArrayList<GamepadFilter>();
		gamepadFilters.add(new DeadzoneFilter(0.05));
		gamepadFilters.add(new PowerFilter(1));
		GamepadFilterSet driveGamepadFilterSet = new GamepadFilterSet(gamepadFilters);
		gamepad = new FilteredGamepad(0,driveGamepadFilterSet);
			
		gamepad.diamondDown().whenPressed(new LowerShooterCommand());
		gamepad.rightTrigger().whenPressed(new FireShooterCommand());
		
		gamepad.diamondUp().whenPressed(new RunCollectorCommand(true, true));
		gamepad.dPadUp().whenPressed(new RunCollectorCommand(true, false));
		gamepad.dPadRight().whenPressed(new RunCollectorCommand(false, false));
	}
	
	public static Controls getInstance() {
		if (instance == null) {
			instance = new Controls();
		}
		
		return instance;
	}
	
}

