package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.MasterOp;

/**
 * Created by Anneliese on 11/25/2017.
 */
@Autonomous(name = "Red1_Ken", group = "Red1_Ken")
class Red1_Ken extends OpMode {
		MasterOp mo = new MasterOp();
    @Override
    public void init() 
	{
        mo.init(hardwareMap);
        mo.color1.setI2cAddress(mo.color1.getI2cAddress());
    }

    @Override
    public void loop() 
		{
			switch (mo.v_state) 
			{
				case 0:
					mo.resetEncoders();
					mo.shutdownAllMotors();
					mo.v_state=1;
				break;
					//  Break statement
					//  Terminates the loop or switch statement and transfers execution to the statement immediately following the loop or switch.
				case 1:
					mo.servo2.setPosition(.40);
					if (mo.servo2.getPosition() < .41 && mo.servo2.getPosition() > .39) 
					{
						mo.v_state=2;
					}
				break;

				case 2:
					mo.run_using_encoders();
					mo.motor7.setPower(.2);
					//  This section of code lowers the color senor arm between the red and blue balls.
					//  The bar lowers and stops when it has traveled ## impulses, see either the red or blue ball. 
						if (mo.motor7.getCurrentPosition() > 200 || mo.color1.blue() > 0 || mo.color1.red() > 0) 
						{
						mo.resetEncoders();
						mo.shutdownAllMotors();
						mo.v_state=3;
						}
				break;
					
				case 3:
					//  This code determines which color the sensor detects.
					//  If the color red is detected, then the robot moves backwards at power of 1 for ## impulses.
					//  If the color blue is detected, then the robot moves forwards at power of 1 for ## impulses
					if (mo.color1.red() > 0) 
						{
						mo.redDetected = true;	 // Red Ball
						mo.run_using_encoders();
						mo.PowerF(-1, 100);
						} 
					else if (mo.color1.blue() > 0)
						{									
						mo.redDetected = false;	 // Blue Ball
						mo.run_using_encoders();
						mo.PowerF(1, 100);
						mo.v_state=4;
						}
				break;
				
				case 4:
					//  This code raises and returns the color sensor bar back to its original position
					mo.run_using_encoders();
					mo.motor7.setPower(-.2);					
					if (mo.motor7.getCurrentPosition() < 1) 
					{
						mo.shutdownAllMotors();
						mo.resetEncoders();
						mo.v_state=5;
					}
				break;
				
				case 5:
					// This code moves the robot backwards off of the balance plate.  This distant traveled is determined by which
					// color the sensor detected.  If red ball detected (true) then robot needs to move BACKWARDS a shorter distance to reach turn
					// around point.  If blue ball detected (false) then robot needs to move BACKWARDS a greater distance to reach
					// the turn around point.
					mo.run_using_encoders();
					if (mo.redDetected)       // Red Ball
					{
						mo.PowerF(-1,400);
					} else if (!mo.redDetected)  // Blue Ball
						{
							mo.PowerF(-1,500);
						}
				break;
					
				case 6:					
					// This code calls the 90 degree zero turn.  The robot needs to turn clock wise so the front of the robot is facing towards the triangle
					// in front of the glyph holder.			
					mo.run_using_encoders();
					mo.zeroTurnL(1,300);
				break;
				
				case 7:
					//  This code moves the robot forward to position in the triangle in front of the glyph holder.
					mo.run_using_encoders();
					mo.PowerF(1,600);
					break;
					
				case 8:
					// This code calls the 90 degree turn.  The robot needs to turn clock wise so the front of the robot is face the glyph
					// holder
					mo.run_using_encoders();
					mo.zeroTurnL(1,300);
				break;					
						
				case 9:
					// This code moves the robot and glyph towards the glyph holder with the intention of putting the glyph into the center column.
					mo.PowerF(1,300);
					
				break;
					
				default:

				break;
			}

			telemetry.addData("V-state: " ,mo.v_state);
			telemetry.addData("blue: ", mo.color1.blue());
			telemetry.addData("red: ",mo.color1.red());
			telemetry.addData("runtime: ", getRuntime());
			telemetry.addData("blueDetected: ",mo.blueDetected);
			telemetry.addData("motor1: ", mo.motor1.getCurrentPosition());
		}
    }

