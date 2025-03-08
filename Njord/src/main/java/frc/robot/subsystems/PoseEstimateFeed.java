// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Optional;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;

import com.fasterxml.jackson.core.StreamReadCapability;

// import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.estimator.KalmanFilter;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.numbers.N3;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.generated.TunerConstants;

public class PoseEstimateFeed extends SubsystemBase {

  private static final edu.wpi.first.math.Vector<N3> stateStdDevs = Constants.statdev;
  private static final edu.wpi.first.math.Vector<N3> visionMeasurementStdDevs = Constants.visdev;

  private PoseStrategy m_poseStrategy = PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR;
  private AprilTagFieldLayout m_aprilTagFieldLayout = AprilTagFieldLayout.loadField(AprilTagFields.k2025Reefscape);

  private PhotonPoseEstimator m_limeLightPoseEstimator;
  private PhotonPoseEstimator m_fishEyePoseEstimator;

  private SwerveDrivePoseEstimator m_globalEstimator;

  private PhotonCamera m_limeLight;
  private PhotonCamera m_fishEye;

  // private PhotonPipelineResult m_limeLightPipeline;
  // private PhotonPipelineResult m_fishEyePipeline;

  public static final Transform3d m_robotToLimelightTransform3d = Constants.robotToLimeLight3d;
  public static final Transform3d m_robotToFishEyeTransform3d = Constants.robotToFishEye3d;

  private KalmanFilter m_kalmanFilter;

  private Field2d m_field2d = new Field2d();

  AprilTagFieldLayout aprilTagFieldLayout = AprilTagFieldLayout.loadField(AprilTagFields.k2025Reefscape);

  CommandSwerveDrivetrain m_swerveDrivetrain;

  /** Creates a new PoseEstimateFeed. */
  public PoseEstimateFeed() {
    m_limeLight = new PhotonCamera("Limelight3");
    m_fishEye = new PhotonCamera("FishEye1");

    m_swerveDrivetrain = TunerConstants.createDrivetrain();

    m_limeLightPoseEstimator = new PhotonPoseEstimator(m_aprilTagFieldLayout, m_poseStrategy,
        m_robotToLimelightTransform3d);
    m_fishEyePoseEstimator = new PhotonPoseEstimator(m_aprilTagFieldLayout, m_poseStrategy,
        m_robotToFishEyeTransform3d);

    // m_globalEstimator = new SwerveDrivePoseEstimator(
    //     m_swerveDrivetrain.getKinematics(),
    //     m_swerveDrivetrain.getState().RawHeading,
    //     m_swerveDrivetrain.getState().ModulePositions,
    //     null,
    //     //AutoBuilder.getCurrentPose(),
    //     stateStdDevs,
    //     visionMeasurementStdDevs);

    m_limeLightPoseEstimator.setMultiTagFallbackStrategy(PoseStrategy.LOWEST_AMBIGUITY);
    m_fishEyePoseEstimator.setMultiTagFallbackStrategy(PoseStrategy.LOWEST_AMBIGUITY);

    // m_kalmanFilter = new KalmanFilter<>(null, null, null, stateStdDevs, visionMeasurementStdDevs, getDistance());

    SmartDashboard.putData("GameField", m_field2d);
  }

  public  Optional<EstimatedRobotPose> UpdateVisionPose() {
    m_limeLightPoseEstimator.setRobotToCameraTransform(m_robotToLimelightTransform3d);
    m_fishEyePoseEstimator.setRobotToCameraTransform(m_robotToFishEyeTransform3d);

    Optional<EstimatedRobotPose> pose =  Optional.empty();
    boolean check = false;

    var limeLight = m_limeLight.getLatestResult();
    var fisheye = m_fishEye.getLatestResult();
    final Optional<EstimatedRobotPose> optionalEstimatedPoseLime = m_limeLightPoseEstimator.update(limeLight);
    m_limeLightPoseEstimator.setLastPose(m_swerveDrivetrain.getPose());

    if (optionalEstimatedPoseLime.isPresent()) {
      // m_field2d.setRobotPose(optionalEstimatedPoseLime.get().estimatedPose.toPose2d());
      pose = optionalEstimatedPoseLime;//.get().estimatedPose.toPose2d();
    }
    final Optional<EstimatedRobotPose> optionalEstimatedPoseFish = m_limeLightPoseEstimator.update(fisheye);
    m_fishEyePoseEstimator.setLastPose(m_swerveDrivetrain.getPose());

    if (optionalEstimatedPoseFish.isPresent()) {
      // m_field2d.setRobotPose(optionalEstimatedPoseFish.get().estimatedPose.toPose2d());
      pose = optionalEstimatedPoseFish;//.get().estimatedPose.toPose2d();
    }
    return pose;
  }

  public double getDistance() {
    double distanceToTarget = 0;
    var limeLight = m_limeLight.getLatestResult();
    distanceToTarget = limeLight.getBestTarget().getBestCameraToTarget().getTranslation().getX();
    // var m_AprilTagTargetPose3d =
    // m_aprilTagFieldLayout.getTagPose(limeLight.getBestTarget().getFiducialId());
    // if(m_AprilTagTargetPose3d.isEmpty()){
    // distanceToTarget =
    // PhotonUtils.calculateDistanceToTargetMeters(m_robotToLimelightTransform3d.getZ(),
    // m_AprilTagTargetPose3d.get().getY(),
    // m_robotToLimelightTransform3d.getRotation().getY(),
    // m_AprilTagTargetPose3d.get().getRotation().getY());
    // }
    return Units.metersToInches(distanceToTarget);
  }

  public Pose2d UpdateGlobalPose() {
    var limeLight = m_limeLight.getLatestResult();
    var fisheye = m_fishEye.getLatestResult();
    final Optional<EstimatedRobotPose> optionalEstimatedPoseLime = m_limeLightPoseEstimator.update(limeLight);
    if (optionalEstimatedPoseLime.isPresent()) {
      m_globalEstimator.addVisionMeasurement(optionalEstimatedPoseLime.get().estimatedPose.toPose2d(), limeLight.getTimestampSeconds());
    }
    final Optional<EstimatedRobotPose> optionalEstimatedPoseFish = m_limeLightPoseEstimator.update(fisheye);
    if (optionalEstimatedPoseFish.isPresent()) {
      m_globalEstimator.addVisionMeasurement(optionalEstimatedPoseFish.get().estimatedPose.toPose2d(), fisheye.getTimestampSeconds());
    }
    // m_globalEstimator.update(m_swerveDrivetrain.getState().RawHeading, m_swerveDrivetrain.getState().ModulePositions);

    return m_globalEstimator.update(m_swerveDrivetrain.getState().RawHeading, m_swerveDrivetrain.getState().ModulePositions);
  }

  public void updateCTREpose(){

    Optional<EstimatedRobotPose> estimatePose = UpdateVisionPose();
    if(estimatePose.isPresent() && estimatePose != null){
    m_swerveDrivetrain.addVisionMeasurement(estimatePose.get().estimatedPose.toPose2d(),estimatePose.get().timestampSeconds, visionMeasurementStdDevs); }
  }

  @Override
  public void periodic() {
    m_field2d.setRobotPose(m_swerveDrivetrain.getPose());
    var limeLight = m_limeLight.getLatestResult();
    var fishEye = m_fishEye.getLatestResult();

    SmartDashboard.putNumber("Rotation", m_swerveDrivetrain.getPose().getRotation().getDegrees());

    if (limeLight.hasTargets() || fishEye.hasTargets()) {
      // SmartDashboard.putBoolean("EstimateReady", UpdateVisionPose());
      SmartDashboard.putNumber("apriltagcheck", m_aprilTagFieldLayout.getTagPose(19).get().getX());
      updateCTREpose();
      // SmartDashboard.putNumber("GetTagID", limeLight.getBestTarget().fiducialId);
      // m_field2d.setRobotPose(UpdateVisionPose().get().estimatedPose.toPose2d());
      // UpdateVisionPose(); // remove when using global
      // m_field2d.setRobotPose(UpdateGlobalPose());
      // SmartDashboard.putNumber("Distance Inches", getDistance());
    }
    // This method will be called once per scheduler run
  }
}