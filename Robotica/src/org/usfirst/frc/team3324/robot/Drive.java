/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.usfirst.frc.team3324.robot;

import edu.wpi.first.wpilibj.*;

/**
 *
 * @author Cameron
 */
public class Drive {
    
    static SpeedController flMotor;
    static SpeedController blMotor;
    static SpeedController frMotor;
    static SpeedController brMotor;
    
    public void tankDrive(int left, int right) {
        
        flMotor.set(left);
        blMotor.set(left);
        frMotor.set(right);
        brMotor.set(right);
    }
    
    public Drive(SpeedController flMotor_, SpeedController blMotor_, SpeedController frMotor_, SpeedController brMotor_) {
        flMotor = flMotor_;
        blMotor = blMotor_;
        frMotor = frMotor_;
        brMotor = brMotor_;
    }
}
