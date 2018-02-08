package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.MasterOp;

/**
 * Created by Anneliese on 1/3/2018.
 */
@Autonomous(name = "DriveBack", group ="DriveBack")
public class DriveBack extends OpMode {
    MasterOp mo= new MasterOp();
    @Override
    public void init() {
    }

    @Override
    public void loop()

    {switch (mo.v_state){
        case 0:
            mo.resetEncoders();
            mo.run_using_encoders();
            mo.shutdownAllMotors();
            mo.v_state++;

        case 1:
            mo.PowerF(-.3,200);
            mo.v_state++;

        default:

            break;



    }
    }
}
