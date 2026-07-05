#!/bin/bash

# Create epic label if not exists
gh label create "epic" --description "Parent issue for epics" --color "f29513" 2>/dev/null || echo "Label 'epic' already exists or failed to create."

# Create Epic 1: Setup Environment
EPIC1=$(gh issue create --title "Epic: Setup Environment" --body "Set up the development environment for FTC Robot Controller coding without physical robot access. Includes installing Android Studio, downloading the FTC SDK, and creating a new project." --label "epic" | grep -o '#[0-9]*' | head -1)

# Sub-issues for Epic 1
gh issue create --title "Install and configure Android Studio" --body "Install and configure Android Studio with required SDKs and plugins for FTC development. Priority: High" --parent "$EPIC1"
gh issue create --title "Download and set up the FTC SDK" --body "Download and set up the FTC Robot Controller SDK from GitHub. Priority: High" --parent "$EPIC1"
gh issue create --title "Create a new FTC project" --body "Create a new FTC project in Android Studio with team-specific modules. Priority: High" --parent "$EPIC1"

# Create Epic 2: Device Configuration
EPIC2=$(gh issue create --title "Epic: Device Configuration" --body "Configure the Android phone for testing FTC code." --label "epic" | grep -o '#[0-9]*' | head -1)

# Sub-issue for Epic 2
gh issue create --title "Set up Android phone as a test device" --body "Set up Android phone as a test device (enable developer options, USB debugging, Wi-Fi debugging). Priority: High" --parent "$EPIC2"

# Create Epic 3: Code Development
EPIC3=$(gh issue create --title "Epic: Code Development" --body "Develop the core FTC code, including OpModes and hardware abstractions." --label "epic" | grep -o '#[0-9]*' | head -1)

# Sub-issues for Epic 3
gh issue create --title "Write and develop OpModes" --body "Write and develop OpModes (autonomous and tele-op) using FTC SDK APIs. Priority: Medium" --parent "$EPIC3"
gh issue create --title "Implement hardware abstraction" --body "Create hardware mapping and abstraction layers for motors, servos, sensors without physical access. Priority: Medium" --parent "$EPIC3"

# Create Epic 4: Testing and Simulation
EPIC4=$(gh issue create --title "Epic: Testing and Simulation" --body "Integrate simulation tools and test the code virtually." --label "epic" | grep -o '#[0-9]*' | head -1)

# Sub-issues for Epic 4
gh issue create --title "Integrate simulation tools" --body "Integrate FTC Simulator or third-party tools (e.g., Vuforia for vision) for virtual testing. Priority: Medium" --parent "$EPIC4"
gh issue create --title "Implement debugging and logging" --body "Implement logging, telemetry, and debugging techniques for code validation on phone. Priority: Medium" --parent "$EPIC4"
gh issue create --title "Test on phone" --body "Deploy code to phone, run OpModes, and simulate robot behavior using phone sensors/inputs. Priority: Low" --parent "$EPIC4"

# Create Epic 5: Project Management
EPIC5=$(gh issue create --title "Epic: Project Management" --body "Set up version control and collaboration tools." --label "epic" | grep -o '#[0-9]*' | head -1)

# Sub-issue for Epic 5
gh issue create --title "Set up version control" --body "Set up Git for version control and collaboration with team. Priority: Low" --parent "$EPIC5"

# Add issues to project (assuming project number 5)
# Note: This may require manual adjustment; replace with actual issue numbers if needed
# gh project item-create --owner tck517 --number 5 --url "https://github.com/tck517/FtcRobotController/issues/$EPIC1"
# Repeat for each issue. For simplicity, add epics and mention sub-issues are linked.

echo "Issues created. Check https://github.com/tck517/FtcRobotController/issues"
echo "To add to project: Use 'gh project item-create --owner tck517 --number 5 --url <issue_url>' for each issue."