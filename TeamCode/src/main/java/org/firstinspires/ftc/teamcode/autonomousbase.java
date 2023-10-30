package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "autobase")
public class autonomousbase extends LinearOpMode {

    private DcMotor motorBackRight;
    private DcMotor motorBackLeft;
    private DcMotor motorFrontRight;
    private DcMotor motorFrontLeft;
    private Servo claw;
    private DcMotor lyftL;
    private DcMotor lyftR;


    // Reverse the right side motors
    // Reverse left motors if you are using NeveRests




    //Convert from the counts per revolution of the encoder to counts per inch
    static final double HD_COUNTS_PER_REV = 28;
    static final double DRIVE_GEAR_REDUCTION = 20.15293;
    static final double WHEEL_CIRCUMFERENCE_MM = 90 * Math.PI;
    static final double DRIVE_COUNTS_PER_MM = (HD_COUNTS_PER_REV * DRIVE_GEAR_REDUCTION) / WHEEL_CIRCUMFERENCE_MM;
    static final double DRIVE_COUNTS_PER_IN = DRIVE_COUNTS_PER_MM * 25.4;

    //Create elapsed time variable and an instance of elapsed time
    private ElapsedTime runtime = new ElapsedTime();

    // Drive function with 6 parameters
    private void drive(double power, double topLeftInches, double topRightInches, double botLeftInches, double botRightInches, double lyftLInches, double lyftRInches) {
        int botRightTarget;
        int botLeftTarget;
        int topRightTarget;
        int topLeftTarget;
        int lyftLTarget;
        int lyftRTarget;


        if (opModeIsActive()) {


            DcMotor motorFrontLeft = hardwareMap.dcMotor.get("frontLeftMotor");
            DcMotor motorBackLeft = hardwareMap.dcMotor.get("backLeftMotor");
            DcMotor motorFrontRight = hardwareMap.dcMotor.get("frontRightMotor");
            DcMotor motorBackRight = hardwareMap.dcMotor.get("backRightMotor");
            DcMotor lyftL = hardwareMap.get(DcMotor.class, "slideL");
            DcMotor lyftR = hardwareMap.get(DcMotor.class, "slideR");

            // Create target positions
            topLeftTarget = motorFrontLeft.getCurrentPosition() + (int) (topLeftInches * DRIVE_COUNTS_PER_IN);
            topRightTarget = motorFrontRight.getCurrentPosition() + (int) (topRightInches * DRIVE_COUNTS_PER_IN);
            botRightTarget = motorBackRight.getCurrentPosition() + (int) (botRightInches * DRIVE_COUNTS_PER_IN);
            botLeftTarget = motorBackLeft.getCurrentPosition() + (int) (botLeftInches * DRIVE_COUNTS_PER_IN);
            lyftLTarget = lyftL.getCurrentPosition() + (int) (lyftLInches * DRIVE_COUNTS_PER_IN);
            lyftRTarget = lyftR.getCurrentPosition() + (int) (lyftRInches * DRIVE_COUNTS_PER_IN);


            // set target position
            motorFrontLeft.setTargetPosition(topLeftTarget);
            motorFrontRight.setTargetPosition(topRightTarget);
            motorBackLeft.setTargetPosition(botLeftTarget);
            motorBackRight.setTargetPosition(botRightTarget);
            lyftL.setTargetPosition(lyftLTarget);
            lyftR.setTargetPosition(lyftRTarget);




            //switch to run to position mode
            motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lyftL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lyftR.setMode(DcMotor.RunMode.RUN_TO_POSITION);



            //run to position at the designated power
            motorFrontRight.setPower(power);
            motorFrontLeft.setPower(power);
            motorBackRight.setPower(power);
            motorBackLeft.setPower(power);
            lyftR.setPower(power);
            lyftL.setPower(power);


            // wait until both motors are no longer busy running to position
            while (opModeIsActive() && (motorBackLeft.isBusy() || motorBackRight.isBusy() || motorBackLeft.isBusy() || motorBackRight.isBusy() || lyftL.isBusy() || lyftR.isBusy())) {
            }

            // Stop all motion;
            motorBackLeft.setPower(0);
            motorBackRight.setPower(0);
            motorFrontLeft.setPower(0);
            motorFrontRight.setPower(0);
            lyftL.setPower(0);
            lyftR.setPower(0);


            // Turn off RUN_TO_POSITION
            motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lyftR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            lyftL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }


    @Override
    public void runOpMode() {

        DcMotor motorFrontLeft = hardwareMap.dcMotor.get("frontLeftMotor");
        DcMotor motorBackLeft = hardwareMap.dcMotor.get("backLeftMotor");
        DcMotor motorFrontRight = hardwareMap.dcMotor.get("frontRightMotor");
        DcMotor motorBackRight = hardwareMap.dcMotor.get("backRightMotor");
        DcMotor lyftL = hardwareMap.get(DcMotor.class, "slideL");
        DcMotor lyftR = hardwareMap.get(DcMotor.class, "slideR");


        motorFrontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        motorBackLeft.setDirection(DcMotorSimple.Direction.REVERSE);//
        motorBackRight.setDirection(DcMotorSimple.Direction.FORWARD);
        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);//
        lyftR.setDirection(DcMotorSimple.Direction.FORWARD);
        lyftL.setDirection(DcMotorSimple.Direction.FORWARD);


        waitForStart();
        if (opModeIsActive()) {
            //topleft topright botleft botright
            drive(0.5, 3, 3, 3, 3, 0, 0);
            sleep(5000);
            drive(0.5,0,0,0,0,4,4);


        }
    }
}