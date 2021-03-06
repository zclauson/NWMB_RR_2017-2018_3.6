package org.firstinspires.ftc.teamcode;

import android.graphics.Color;
import android.view.Display;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

/**
 * Created by Zachary Clauson on 1/3/2018.
 */
@TeleOp(name = "CSCalibrationWhite",group = "CSCalibrationWhite")
public class CSCalibrationWhite extends OpMode {
    MasterOp mo= new MasterOp();
    @Override
    public void init() {
        mo.init(hardwareMap);
    }

    @Override
    public void loop() {
        telemetry.addData("red: ",mo.color1.red());
        telemetry.addData("blue: ",mo.color1.blue());
        telemetry.addData("alpha: ",mo.color1.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER));
        telemetry.addData("argb: ",mo.color1.argb());
        telemetry.addData("I2C Address: ",mo.color1.getI2cAddress());
        mo.color1.writeCommand(ModernRoboticsI2cColorSensor.Command.CALIBRATE_WHITE);

        if (gamepad1.a){
            mo.color1.enableLed(true);
        }
        if (gamepad1.b){
            mo.color1.enableLed(false);
        }

    }
}
