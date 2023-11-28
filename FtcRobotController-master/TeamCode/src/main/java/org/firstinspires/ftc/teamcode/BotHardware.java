package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

public class BotHardware {


    private static BotHardware instance;
    private LinearOpMode myOpMode = null;   // gain access to methods in the calling OpMode.

    // Define Motor and Servo objects  (Make them private so they can't be accessed externally)
    private DcMotor leftRearDrive = null;
    private DcMotor rightRearDrive  = null;
    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor armMotor = null;

    // Define Sensors. Notice they are REVROBOTICS sensors.
    private Rev2mDistanceSensor mDistanceSensor = null;
    private RevColorSensorV3 colorSensor = null;
    private RevTouchSensor touchSensor =null;
    private BNO055IMU imu = null;

    private boolean isInitCalled = false;


    // Define a constructor that allows the OpMode to pass a reference to itself.
    private BotHardware() {
    }

    // Public static method to get the single instance of the class
    public static BotHardware getInstance() {
        // create the instance only if it's null
        if (instance == null) {
            instance = new BotHardware();
        }
        return instance;
    }

    /**
     * Initialize all the robot's hardware.
     * This method must be called ONCE when the OpMode is initialized.
     * <p>
     * All of the hardware devices are accessed via the hardware map, and initialized.
     */
    public void init(LinearOpMode myOpMode)    {
        if (!isInitCalled) {
            // Define and Initialize Motors (note: need to use reference to actual OpMode).
            leftRearDrive = myOpMode.hardwareMap.get(DcMotor.class, "left_drive_rear");
            rightRearDrive = myOpMode.hardwareMap.get(DcMotor.class, "right_drive_rear");
            leftFrontDrive = myOpMode.hardwareMap.get(DcMotor.class, "left_drive_front");
            rightFrontDrive = myOpMode.hardwareMap.get(DcMotor.class, "right_drive_front");
            //define and Initialize Sensors
            mDistanceSensor = myOpMode.hardwareMap.get(Rev2mDistanceSensor.class, "distance_sensor");
            colorSensor = myOpMode.hardwareMap.get(RevColorSensorV3.class, "color_sensor");
            touchSensor = myOpMode.hardwareMap.get(RevTouchSensor.class, "touch_sensor");
            imu = myOpMode.hardwareMap.get(BNO055IMU.class, "imu");

            // setting of the units of measurements for angles
            BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
            parameters.angleUnit =  BNO055IMU.AngleUnit.DEGREES;
            parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
            parameters.calibrationDataFile = "BNO055IMUCalibration.json";
            parameters.loggingEnabled = true;
            parameters.loggingTag = "IMU";
            parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

            //initializing the imu with parameters
            imu.initialize(parameters);
            // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
            // Pushing the left stick forward MUST make robot go forward. So adjust these two lines based on your first test drive.
            // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
            leftRearDrive.setDirection(DcMotor.Direction.REVERSE);
            leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);

            // If there are encoders connected, switch to RUN_USING_ENCODER mode for greater accuracy
            // leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            // rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            // Define and initialize ALL installed servos.

            myOpMode.telemetry.addData(">", "Hardware Initialized");
            myOpMode.telemetry.update();
            isInitCalled = true;
        }
    }

    /**
     *  Pass the requested wheel motor powers to the appropriate hardware drive motors.
     * @param leftRearWheel left rear wheel power
     * @param rightRearWheel right rear wheel power
     * @param rightFrontWheel right front wheel power
     * @param leftFrontWheel left front wheel power
     */
    public void setDrivePower(double leftRearWheel, double rightRearWheel, double rightFrontWheel, double leftFrontWheel) {
        // Output the values to the motor drives.
        leftRearDrive.setPower(leftRearWheel);
        rightRearDrive.setPower(rightRearWheel);
        leftFrontDrive.setPower(leftFrontWheel);
        rightFrontDrive.setPower(rightFrontWheel);
    }

    public void setArmPower(double armPower)
    {
        armMotor.setPower(armPower);
    }
    //Getter methods for sensors
    public Rev2mDistanceSensor get2mDistanceSensor(){return mDistanceSensor;}
    public RevColorSensorV3 getColorSensor(){return colorSensor;}
    public RevTouchSensor getTouchSensor() {return touchSensor;}
    public BNO055IMU getIMU(){return imu;}
}
