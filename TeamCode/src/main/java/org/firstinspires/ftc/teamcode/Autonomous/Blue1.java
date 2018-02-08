package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.*;

import org.firstinspires.ftc.teamcode.MasterOp;

/**
 * Created by Zachary Clauson on 10/28/2017.
 */
@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Blue1", group = "Blue1")

public class Blue1 extends OpMode{
    MasterOp mo = new MasterOp();
    @Override
    public void init() {
        mo.init(hardwareMap);

    }

    @Override
    public void loop() {

        switch (mo.v_state){
            case 0:
                mo.resetEncoders();
                mo.shutdownAllMotors();
                mo.servo1.setPosition(.25);
                mo.servo2.setPosition(.50);
                mo.motor5.setPower(-1);
                if (mo.motor5.getCurrentPosition()> 100){
                    mo.v_state++;
                    mo.shutdownAllMotors();
                    mo.resetEncoders();
                }

                break;

            case 1:
                mo.run_using_encoders();
                mo.motor7.setPower(.1);
                //this means whether the motor goes 200 impulses or blue is detected or red is detected
                // red is 10 in the COLOR_NUMBER  index and blue is 3
                if (mo.color1.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER) ==10 ||
                        mo.color1.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER) ==3|| mo.motor7.getCurrentPosition() > 450){
                    mo.shutdownAllMotors();
                    mo.resetEncoders();
                    mo.v_state++;
                }
                break;
            case 2:
                    if (mo.color1.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER)==2 ||
                            mo.color1.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER)==3||
                            mo.color1.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER)==4 ) {
                        mo.blueDetected=true;
                        mo.run_using_encoders();
                        mo.PowerF(-.3, 150);
                    } else if (mo.color1.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER) ==9||
                            mo.color1.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER)==10||
                            mo.color1.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER)==11||
                            mo.color1.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER)==12) {
                        mo.blueDetected=false;
                        mo.run_using_encoders();
                        mo.PowerF(.3,150);
                    } else if (mo.time > 100){
                        mo.v_state++;
                    } else{
                        mo.time++;
                    }

                break;
            case 3:
                mo.run_using_encoders();
                mo.motor7.setPower(-.2);
                if (mo.motor7.getCurrentPosition() < -300){
                    mo.shutdownAllMotors();
                    mo.resetEncoders();
                    mo.v_state++;
                }
                break;
            case 4:
                mo.run_using_encoders();
                if (mo.time > 400){
                    mo.PowerF(.3,3300);
                }else if (mo.blueDetected){
                    mo.PowerF(.3,3400);
                }
                else if(!mo.blueDetected) {
                    mo.PowerF(.3, 3200);
                }
                break;
            case 5:
                if (mo.time >400){
                    mo.zeroTurnL(.3,1900);
                }
                else if (mo.blueDetected){
                    mo.run_using_encoders();
                    mo.zeroTurnL(.3,1700);
                }
                else if (!mo.blueDetected){
                    mo.run_using_encoders();
                    mo.zeroTurnL(.3,1600);
                }

                break;
            case 6:
                mo.run_using_encoders();
                mo.PowerF(.3,800);
                time=0;
                break;
            case 7:
                mo.run_using_encoders();
                mo.servo1.setPosition(.9);
                mo.servo2.setPosition(-.9);
                if (time > 12){
                    mo.PowerF(-.3,100);
                }
                else{
                    time++;
                }
                break;




            default:

                break;
        }
        telemetry.addData("V-state: " ,mo.v_state);
        telemetry.addData("time", mo.time);
        telemetry.addData("color: ","red = 3 blue = 10");
        telemetry.addData(" color: ",mo.color1.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER));
        telemetry.addData("runtime: ", getRuntime());
        telemetry.addData("blueDetected: ",mo.blueDetected);
        telemetry.addData("motor1: ", mo.motor1.getCurrentPosition());

    }
}
