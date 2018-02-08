package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.MasterOp;

/**
 * Created by Anneliese on 1/1/2018.
 */
@Autonomous(name = "BlueForward", group = "BlueForward")
public class AC_Blue1 extends OpMode {
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
            case 1:
                mo.run_using_encoders();
                if (mo.color1.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER) == 10) {
                    mo.redDetected = true;
                    mo.run_using_encoders();
                    mo.PowerF(.3,150);
                }






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
