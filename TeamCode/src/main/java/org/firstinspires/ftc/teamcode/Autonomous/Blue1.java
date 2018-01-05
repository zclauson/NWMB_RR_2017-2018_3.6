package org.firstinspires.ftc.teamcode.Autonomous;

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
        mo.color1.setI2cAddress(mo.color1.getI2cAddress());
    }

    @Override
    public void loop() {

        switch (mo.v_state){
            case 0:
                mo.resetEncoders();
                mo.shutdownAllMotors();
                mo.v_state++;
                break;

            case 1:
                mo.run_using_encoders();
                mo.motor7.setPower(.1);
                //this means whether the motor goes 200 impulses or blue is detected or red is detected
                if (mo.color1.blue() > 0 || mo.color1.red() > 0|| mo.motor7.getCurrentPosition() > 450){
                    mo.shutdownAllMotors();
                    mo.resetEncoders();
                    mo.v_state++;
                }
                break;
            case 2:
                    if (mo.color1.blue() > 0) {
                        mo.blueDetected=true;
                        mo.run_using_encoders();
                        mo.PowerForB(-.3, 150);
                    } else if (mo.color1.red() > 0) {
                        mo.blueDetected=false;
                        mo.run_using_encoders();
                        mo.PowerForB(.3,150);
                    } else if (mo.time > 400){
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
                if (time > 400){
                    mo.PowerForB(.3,3300);
                }else if (mo.blueDetected){
                    mo.PowerForB(.3,3400);
                }
                else if(!mo.blueDetected) {
                    mo.PowerForB(.3, 3200);
                }
                break;
            case 5:
                if (time >400){
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
                mo.PowerForB(.3,800);
                time=0;
                break;
            case 7:
                mo.run_using_encoders();
                if (time >120) {
                    mo.PowerForB(-.3, 200);
                }else{
                    time++;
                }
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
