# FTC Development Plan: Offline Development Without Hardware

This document outlines a complete development plan for FTC Robot Controller coding **without access to a Control Hub or physical robot**. The strategy relies on hardware abstraction, third-party simulators, and unit testing to validate code before hardware integration.

> **Note:** Since GitHub issues are disabled in this repository, these items can be manually added to the project at https://github.com/users/tck517/projects/5/views/2 as project items.

---

## Epic 1: Environment Setup
**Description:** Set up the development environment for FTC Robot Controller coding.

### Issues:

- **Issue 1.1: Install and configure Android Studio**
  Install Android Studio Ladybug (2024.2) or later with Android SDK 30, NDK 21.3.6528147, and Java 8 support. Verify Gradle sync completes successfully.
  *Priority: High*

- **Issue 1.2: Clone and build the FTC SDK**
  Clone the FtcRobotController repository from GitHub. Build the project to verify the environment is configured correctly. Resolve any dependency or SDK version issues.
  *Priority: High*

- **Issue 1.3: Understand the project structure**
  Learn the SDK structure: `TeamCode/` is where you write code, `FtcRobotController/src/.../external/samples/` contains 65+ sample OpModes. Understand the difference between LinearOpMode and iterative OpMode patterns.
  *Priority: High*

---

## Epic 2: Hardware Abstraction Architecture
**Description:** Design an architecture that separates robot logic from physical hardware, enabling offline development and testing.

### Issues:

- **Issue 2.1: Define hardware interfaces**
  Create Java interfaces for all hardware components: drivetrain, arm/lift mechanisms, intake, sensors (IMU, distance, color), and vision. These interfaces define what the robot *can do* without specifying *how*.
  *Priority: High*

- **Issue 2.2: Implement mock hardware classes**
  Create mock/stub implementations of hardware interfaces that simulate behavior without real hardware. Mock motors can track position/power, mock sensors return configurable values. This enables logic testing offline.
  *Priority: High*

- **Issue 2.3: Implement real hardware classes**
  Create real implementations that wrap actual FTC SDK hardware classes (DcMotor, Servo, IMU, etc.). These will be used when deploying to the actual robot.
  *Priority: Medium*

- **Issue 2.4: Create hardware factory/dependency injection**
  Build a factory or configuration system that switches between mock and real hardware implementations. OpModes should not directly instantiate hardware—they receive it through abstraction.
  *Priority: Medium*

---

## Epic 3: Code Development
**Description:** Develop OpModes and robot logic using the abstraction layer.

### Issues:

- **Issue 3.1: Study sample OpModes**
  Review relevant samples in `FtcRobotController/external/samples/`: BasicOpMode_Linear, BasicOmniOpMode_Linear, RobotAutoDriveByEncoder, ConceptExternalHardwareClass (hardware abstraction pattern), and AprilTag samples for vision.
  *Priority: High*

- **Issue 3.2: Develop TeleOp OpModes**
  Write teleop OpModes for driver control. Implement gamepad input handling, drive control (tank/mecanum/holonomic), and manipulator control. Use the hardware abstraction layer.
  *Priority: Medium*

- **Issue 3.3: Develop Autonomous OpModes**
  Write autonomous routines using encoder-based driving, time-based actions, or sensor feedback. Design as state machines for testability. Consider path planning libraries like Road Runner.
  *Priority: Medium*

- **Issue 3.4: Implement vision processing**
  Use the FTC VisionPortal API with AprilTag detection for localization (Vuforia is deprecated). Design vision code to work with recorded images/video for offline testing.
  *Priority: Medium*

- **Issue 3.5: Create reusable subsystem classes**
  Encapsulate robot subsystems (drivetrain, arm, intake) in separate classes following the pattern in `RobotHardware.java` sample. This improves code organization and testability.
  *Priority: Medium*

---

## Epic 4: Offline Testing and Simulation
**Description:** Test robot code without physical hardware using simulators and unit tests.

### Issues:

- **Issue 4.1: Set up virtual robot simulator**
  Install and configure [Beta8397/FTC_Simulator](https://github.com/Beta8397/virtual_robot) (virtual_robot). This provides a 2D simulation environment that runs actual OpMode code against simulated hardware.
  *Priority: High*

- **Issue 4.2: Set up MeepMeep for path visualization**
  Install [MeepMeep](https://github.com/NoahBres/MeepMeep) for visualizing and testing autonomous paths. Essential if using Road Runner for trajectory planning.
  *Priority: Medium*

- **Issue 4.3: Write unit tests for algorithms**
  Create JUnit tests for non-hardware logic: path calculations, state machines, PID controllers, coordinate transforms, and game strategy logic. These run instantly without any simulation.
  *Priority: Medium*

- **Issue 4.4: Test with mock hardware**
  Use mock hardware implementations (from Epic 2) to run OpModes in a test harness. Verify state transitions, command sequences, and logic flow without simulation overhead.
  *Priority: Medium*

- **Issue 4.5: Implement telemetry and logging**
  Add comprehensive telemetry output to OpModes for debugging. Create a logging system that works both on-robot and in simulation. Use FTC Dashboard for real-time visualization when hardware is available.
  *Priority: Low*

---

## Epic 5: Version Control and Collaboration
**Description:** Establish team workflow for collaborative development.

### Issues:

- **Issue 5.1: Configure Git workflow**
  Set up branching strategy (e.g., feature branches, main for stable code). Define commit message conventions and code review process.
  *Priority: High*

- **Issue 5.2: Document code and architecture**
  Write documentation for hardware abstraction interfaces, subsystem APIs, and OpMode usage. Ensure team members can understand and extend the codebase.
  *Priority: Medium*

- **Issue 5.3: Create configuration management**
  Store robot configuration (motor directions, servo positions, PID constants) in a central location. Support multiple robot configurations if the team has multiple robots.
  *Priority: Low*

---

## Epic 6: Hardware Integration Preparation
**Description:** Prepare for the transition from offline development to real hardware.

### Issues:

- **Issue 6.1: Document hardware requirements**
  List all required hardware: motor types, servo positions, sensor placements, and wiring. Create a hardware configuration checklist for robot setup.
  *Priority: Medium*

- **Issue 6.2: Create hardware configuration mapping**
  Define the exact device names that must be configured in the Driver Station app (e.g., `left_drive`, `right_drive`, `arm_motor`). These must match the names used in code.
  *Priority: Medium*

- **Issue 6.3: Prepare deployment checklist**
  Document steps to deploy code to robot: connect to Control Hub, configure hardware in Driver Station, deploy via Android Studio, and verify OpModes appear. Include troubleshooting steps.
  *Priority: Low*

- **Issue 6.4: Plan hardware testing sequence**
  Define a safe sequence for first hardware tests: verify motor directions, test sensor readings, calibrate servos, then run simple movements before full OpModes.
  *Priority: Low*

---

## Summary: Offline Development Strategy

| Phase | What You Can Do Offline | Tools |
|-------|------------------------|-------|
| **Design** | Architecture, interfaces, algorithms | Android Studio, pen & paper |
| **Code** | OpModes, subsystems, state machines | Android Studio, FTC SDK |
| **Unit Test** | Algorithms, math, logic | JUnit |
| **Simulate** | Full robot behavior | virtual_robot, MeepMeep |
| **Mock Test** | OpMode logic flow | Custom mock classes |

### Key Principles

1. **Abstraction is essential** — Never call FTC hardware directly in OpModes; use interfaces
2. **Test early, test often** — Unit tests catch bugs faster than simulation
3. **Simulate before hardware** — virtual_robot validates OpModes before deployment
4. **Document everything** — Hardware configuration must match code exactly

---

## How to Add to GitHub Project

1. Go to https://github.com/users/tck517/projects/5/views/2
2. Click "Add item" and paste issue titles/descriptions as notes
3. Organize into groups (epics) using the project's grouping features
4. Use labels or custom fields to track priority (High/Medium/Low)
