package org.firstinspires.ftc.teamcode.Autonomous;

import android.util.Log;

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
                mo.servo1.setPosition(.25);
                mo.servo2.setPosition(.50);
                mo.v_state++;
                break;
            //  Break statement
            //  Terminates the loop or switch statement and transfers execution to the statement immediately following the loop or switch.
            case 1:
                mo.run_using_encoders();
                mo.motor5.setPower(1);
                if (mo.motor5.getCurrentPosition()> 100){
                    mo.v_state++;
                    mo.shutdownAllMotors();
                    mo.resetEncoders();
                }
                break;
            case 2:
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

            case 3:
                mo.run_using_encoders();
                //  This code determines which color the sensor detects.
                //  If the color red is detected, then the robot moves backwards at power of 1 for 100 impulses.
                //  If the color blue is detected, then the robot moves forwards at power of 1 for 100 impulse
//                mo.PowerF(.3,100);
                 if (mo.color1.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER) ==10){
                    mo.redDetected = true;
                    mo.PowerF(.3, 100);
                }
                else if (mo.color1.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER)==3) {
                    mo.redDetected = false;
                    mo.PowerB(-.3, -100);
                } else if(mo.time > 100){
                     mo.v_state++;
                } else{
                     mo.time++;
                 }
                break;

            case 4:
                //  This code raises and returns the color sensor bar back to its original position
                mo.run_using_encoders();
                mo.color1.enableLed(false);
                mo.v_state++;
                mo.motor7.setPower(-.2);
                if (mo.motor7.getCurrentPosition() < -300) {
                    mo.shutdownAllMotors();
                    mo.resetEncoders();
                    mo.v_state++;
                }
                break;

            case 5:
                // This code moves the robot backwards off of the balance plate.  This distant traveled is determined by which
                // color the sensor detected.  If red ball detected (true) then robot needs to move BACKWARDS a shorter distance to reach turn
                // around point.  If blue ball detected (false) then robot needs to move BACKWARDS a greater distance to reach
                // the turn around point.
                mo.run_using_encoders();
//                mo.PowerF(-.3,2200);
                if (mo.time > 400) {
                    mo.PowerB(-.3,-1800);
                } else if (mo.redDetected){       // Red Ball{
                    mo.PowerB(-.3,-1800);
                }
                else if (!mo.redDetected){ // Blue Ball

                    mo.PowerB(-.3,-2200);
                }

                break;
            case 6:
                mo.run_using_encoders();
                mo.PowerR(.3,1200);

                break;
            case 7:
                // This code calls the 90 degree turn.  The robot needs to turn counter clock wise so the front of the robot is face the glyph
                // holder
                mo.run_using_encoders();
                mo.zeroTurnR(-.3,3400);
                break;

            case 8:
                // This code moves the robot and glyph towards the glyph holder with the intention of putting the glyph into the center column.
                mo.run_using_encoders();
//                mo.PowerF(.3,400);
                if (mo.redDetected){
                    mo.PowerF(.3,600);
                }
                else if (!mo.redDetected){
                    mo.PowerF(.3,800);
                }
            case 9:
                mo.run_using_encoders();
                mo.servo1.setPosition(.9);
                mo.servo2.setPosition(-.9);
                if (time > 12){
                    mo.PowerB(-.3,-100);
                }
                else{
                    time++;
                }
                break;

            default:

                break;
        }
        Log.d("zach","motor7: "+ mo.motor7.getCurrentPosition());
        Log.d("zach","time: "+ mo.time);
        Log.d("zach","v_state: "+ mo.v_state);
        Log.d("zach","color: "+ mo.color1.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER));
        telemetry.addData("V-state: " ,mo.v_state);
        telemetry.addData("color: ","red = 3 blue = 10");
        telemetry.addData(" color: ",mo.color1.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER));
        telemetry.addData("runtime: ", getRuntime());
        telemetry.addData("redDetected: ",mo.redDetected);
        telemetry.addData("motor1: ", mo.motor1.getCurrentPosition());
    }




}
