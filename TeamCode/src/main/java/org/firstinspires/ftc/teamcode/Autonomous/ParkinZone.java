package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.Hardware;

import org.firstinspires.ftc.teamcode.MasterOp;

/**
 * Created by Zachary Clauson on 10/21/2017.
 */
@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "ParkinZone", group = "ParkinZone")
@Disabled
public class ParkinZone extends OpMode {
    MasterOp mo = new MasterOp();
    @Override
    public void init(){
        mo.init(hardwareMap);
    }

    @Override
    public void loop(){
        telemetry.update();
        telemetry.addData("V-state: " ,mo.v_state);
        telemetry.addData("blue: ", mo.color1.blue());
        telemetry.addData("red: ",mo.color1.red());
        telemetry.addData("runtime: ", getRuntime());
        switch (mo.v_state){
         case 0:
             mo.resetEncoders();
             mo.shutdownAllMotors();
             mo.v_state++;
             break;
         case 1:
             mo.servo1.setPosition(.5);
             if(mo.servo1.getPosition()==.5){
                 mo.v_state++;
             }
             break;
         case 2:
             mo.PowerForB(1,200);
             break;
         case 3:
             mo.run_using_encoders();
             mo.motor5.setPower(-.2);
             if (mo.motor5.getCurrentPosition() > 200){
                 mo.shutdownAllMotors();
                 mo.resetEncoders();
                 mo.v_state++;
             }

             break;
         case 4:
             mo.run_using_encoders();
             mo.motor1.setPower(.5);
             mo.motor2.setPower(-.5);
             mo.motor3.setPower(.5);
             mo.motor4.setPower(-.5);
             if( mo.motor3.getCurrentPosition() > 200){
                mo.shutdownAllMotors();
                 mo.resetEncoders();
                 mo.v_state++;
             }
             break;
         case 5:
             mo.run_using_encoders();
             mo.motor5.setPower(-.3);
             if (Math.abs(mo.motor5.getCurrentPosition()) < 20){
                 mo.servo1.setPosition(1);
                 mo.shutdownAllMotors();
                 mo.resetEncoders();
             }

             break;

         default:

            break;

         }
    }
}
