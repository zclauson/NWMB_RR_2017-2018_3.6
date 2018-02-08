package org.firstinspires.ftc.teamcode.Autonomous;

import android.util.Log;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.MasterOp;

/**
 * Created by Anneliese on 11/25/2017.
 */
@Autonomous(name = "Red1", group = "Red1_Ken")
public class Red1 extends OpMode {
    MasterOp mo = new MasterOp();

    @Override
    public void init() {
        mo.init(hardwareMap);

    }

    @Override
    public void loop() {

        switch (mo.v_state) {
            case 0:
                mo.resetEncoders();
                mo.shutdownAllMotors();
                mo.servo1.setPosition(.25);
                mo.servo2.setPosition(.50);
                mo.v_state++;
                break;
            case 1:
                mo.run_using_encoders();
                mo.motor5.setPower(1);
                if (mo.motor5.getCurrentPosition() > 100){
                    mo.v_state++;
                    mo.shutdownAllMotors();
                    mo.resetEncoders();
                }
                break;
            case 2:
                mo.run_using_encoders();
                mo.motor7.setPower(.1);
                if ( mo.color1.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER)==3 ||
                        mo.color1.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER) ==10||mo.motor7.getCurrentPosition() > 450) {
                    mo.resetEncoders();
                    mo.shutdownAllMotors();
                    mo.v_state++;
                }
                break;

            case 3:
                mo.run_using_encoders();
                if (mo.color1.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER) == 10) {
                    mo.redDetected = true;
                    mo.run_using_encoders();
                    mo.PowerF(.3,150);
                }else if (mo.color1.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER)== 3) {
                    mo.redDetected = false;
                    mo.run_using_encoders();
                    mo.PowerB(-.3, -150);
                }
                if(mo.time > 100){
                    mo.v_state++;
                }else{
                    mo.time++;
                }
                break;

            case 4:
                mo.run_using_encoders();
                mo.motor7.setPower(-.2);
                if (mo.motor7.getCurrentPosition() < -400) {
                    mo.shutdownAllMotors();
                    mo.resetEncoders();
                    mo.v_state++;
                }
                break;
            case 5:
                mo.run_using_encoders();
                if (time > 400){
                  mo.PowerB(-.3,-3200);
                } else if (mo.redDetected==true) {
                    mo.PowerB(-.3, -2900);
                } else if (mo.redDetected==false) {
                    mo.PowerB(-.3,-3300);
                }
                break;
            case 6:
                if (!mo.redDetected) {
                    mo.run_using_encoders();
                    mo.zeroTurnL(.3, 1600);
                }
                else if (mo.redDetected){
                    mo.run_using_encoders();
                    mo.zeroTurnL(.3,1600);
                }
                break;
            case 7:
                mo.run_using_encoders();
                mo.PowerF(.3,800);
                time=0;
                break;
            case 8:
                mo.run_using_encoders();
                mo.servo1.setPosition(.9);
                mo.servo2.setPosition(-.9);
                if (time > 12){
                    mo.PowerF(-.3,-100);
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
            Log.d("zach","servo1:"+ mo.servo1.getPosition()+ "  servo2: "+mo.servo2.getPosition());
            Log.d("zach","motor3: "+mo.motor3.getCurrentPosition());
            telemetry.addData("v_state: " , mo.v_state);
            telemetry.addData("color: ","red = 3 blue = 10");
            telemetry.addData(" color: ",mo.color1.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER));
            telemetry.addData("runtime: ", getRuntime());
            telemetry.addData("blueDetected: ", mo.redDetected);
            telemetry.addData("time: " , mo.time);
        }
    }

