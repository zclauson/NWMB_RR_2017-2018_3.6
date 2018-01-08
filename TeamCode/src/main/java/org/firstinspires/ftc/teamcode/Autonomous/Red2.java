package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.MasterOp;

/**
 * Created by Anneliese on 12/10/2017.
 */
@Autonomous(name = "Red2", group = "Red2")
public class Red2 extends OpMode {

    MasterOp mo = new MasterOp();
    @Override
    public void init()
    {
        mo.init(hardwareMap);
//        mo.color1.setI2cAddress(mo.color1.getI2cAddress());
    }

    @Override
    public void loop()
    {
        switch (mo.v_state)
        {
            case 0:
                mo.resetEncoders();
                mo.shutdownAllMotors();
                mo.v_state++;
                break;
            //  Break statement
            //  Terminates the loop or switch statement and transfers execution to the statement immediately following the loop or switch.

            case 1:
                //  This section of code lowers the color senor arm between the red and blue balls.
                //  The bar lowers and stops when it has traveled ## impulses or sees either the red or blue ball.
                mo.run_using_encoders();
//                mo.v_state++;
                mo.motor7.setPower(.1);
                if ( mo.color1.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER) == 3
                        || mo.color1.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER) ==10 ||mo.motor7.getCurrentPosition()> 450) {
                    mo.resetEncoders();
                    mo.shutdownAllMotors();
                    mo.v_state++;
                }
                break;

            case 2:
                //  This code determines which color the sensor detects.
                //  If the color red is detected, then the robot moves backwards at power of 1 for 100 impulses.
                //  If the color blue is detected, then the robot moves forwards at power of 1 for 100 impulse
//                mo.PowerForB(.3,100);
                 if (mo.color1.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER) ==10){
                    mo.redDetected = true;
                    mo.PowerForB(-.3, 100);
                }
                else if (mo.color1.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER) ==3) {
                    mo.redDetected = false;
                    mo.PowerForB(.3, 100);
                } else if(mo.time > 400){
                     mo.v_state++;
                } else{
                     mo.time++;
                 }
                break;

            case 3:
                //  This code raises and returns the color sensor bar back to its original position
                mo.run_using_encoders();
                mo.v_state++;
                mo.motor7.setPower(-.2);
                if (mo.motor7.getCurrentPosition() < -300) {
                    mo.shutdownAllMotors();
                    mo.resetEncoders();
                    mo.v_state++;
                }
                break;

            case 4:
                // This code moves the robot backwards off of the balance plate.  This distant traveled is determined by which
                // color the sensor detected.  If red ball detected (true) then robot needs to move BACKWARDS a shorter distance to reach turn
                // around point.  If blue ball detected (false) then robot needs to move BACKWARDS a greater distance to reach
                // the turn around point.
                mo.run_using_encoders();
//                mo.PowerForB(-.3,2200);
                if (mo.time > 400) {
                    mo.PowerForB(-.3,1800);
                } else if (mo.redDetected){       // Red Ball{
                    mo.PowerForB(-.3,1800);
                }
                else if (!mo.redDetected){ // Blue Ball

                    mo.PowerForB(-.3,2200);
                }

                break;
            case 5:
                mo.run_using_encoders();
                mo.PowerR(.3,1200);

                break;
            case 6:
                // This code calls the 90 degree turn.  The robot needs to turn counter clock wise so the front of the robot is face the glyph
                // holder
                mo.run_using_encoders();
                mo.zeroTurnR(-.3,3400);
                break;

            case 7:
                // This code moves the robot and glyph towards the glyph holder with the intention of putting the glyph into the center column.
                mo.run_using_encoders();
//                mo.PowerForB(.3,400);
                if (mo.redDetected){
                    mo.PowerForB(.3,400);
                }
                else if (!mo.redDetected){
                    mo.PowerForB(.3,600);
                }

                break;

            default:

                break;
        }

        telemetry.addData("V-state: " ,mo.v_state);
        telemetry.addData("blue: ", mo.color1.blue());
        telemetry.addData("red: ",mo.color1.red());
        telemetry.addData("runtime: ", getRuntime());
        telemetry.addData("redDetected: ",mo.redDetected);
        telemetry.addData("motor1: ", mo.motor1.getCurrentPosition());
    }




}
