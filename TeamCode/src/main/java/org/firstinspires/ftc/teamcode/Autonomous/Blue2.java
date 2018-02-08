package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.MasterOp;

/**
 * Created by Zachary Clauson on 11/11/2017.
 */
@Autonomous(name = "Blue2", group ="Blue2")
public class Blue2 extends OpMode {
    MasterOp mo= new MasterOp();

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
//                mo.v_state++;
                mo.motor7.setPower(.1);
                if ( mo.color1.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER) == 3 ||
                        mo.color1.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER) == 10 ||mo.motor7.getCurrentPosition() > 450){
                    mo.shutdownAllMotors();
                    mo.resetEncoders();
                    mo.v_state++;
                }
                break;
            case 2:

//                mo.PowerF(.3, 100);
                if (mo.color1.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER)==2 ||
                        mo.color1.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER)==3||
                        mo.color1.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER)==4 ) {
                    mo.blueDetected=true;
                    mo.run_using_encoders();
                    mo.PowerF(-.3, 100);
                } else if (mo.color1.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER) ==9||
                        mo.color1.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER)==10||
                        mo.color1.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER)==11||
                        mo.color1.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER)==12) {
                    mo.blueDetected=false;
                    mo.run_using_encoders();
                    mo.PowerF(.3,100);
                }
                else if (mo.time > 100){
                    mo.v_state++;
                }
                else{
                    mo.time++;
                }

                break;
            case 3:
                mo.run_using_encoders();
//                mo.v_state++;
                mo.motor7.setPower(-.2);
                if (mo.motor7.getCurrentPosition() < -300){
                    mo.shutdownAllMotors();
                    mo.resetEncoders();
                    mo.v_state++;
                }
                break;
            case 4:
                mo.run_using_encoders();
//                mo.PowerF(.3,1800);

                if (mo.blueDetected){
                    mo.PowerF(.3,2200);
                }
                else if(!mo.blueDetected) {
                    mo.PowerF(.3, 1800);
                }


                break;
            case 5:
                mo.run_using_encoders();
                mo.PowerR(.3,1050);
                break;
            case 6:
                mo.run_using_encoders();
                mo.PowerF(.3,500);
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
