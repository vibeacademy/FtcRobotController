#!/bin/bash
#
# sync_teamcode.sh - Sync TeamCode between FtcRobotController and virtual_robot
#
# Usage:
#   ./sync_teamcode.sh [direction] [options]
#
# Directions:
#   to-sim      Copy from FtcRobotController -> virtual_robot (default)
#   from-sim    Copy from virtual_robot -> FtcRobotController
#
# Options:
#   --dry-run   Show what would be copied without actually copying
#   --hardware  Also sync the hardware abstraction layer
#   --all       Sync everything (opmodes + hardware)
#   -h, --help  Show this help message

set -e

# Project paths
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
FTC_ROOT="$SCRIPT_DIR"
VIRTUAL_ROOT="$(dirname "$FTC_ROOT")/virtual_robot"

FTC_TEAMCODE="$FTC_ROOT/TeamCode/src/main/java/org/firstinspires/ftc/teamcode"
VIRTUAL_TEAMCODE="$VIRTUAL_ROOT/TeamCode/src/org/firstinspires/ftc/teamcode"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Default options
DIRECTION="to-sim"
DRY_RUN=false
SYNC_HARDWARE=false
SYNC_OPMODES=true

# Print colored output
print_info() { echo -e "${BLUE}[INFO]${NC} $1"; }
print_success() { echo -e "${GREEN}[SUCCESS]${NC} $1"; }
print_warning() { echo -e "${YELLOW}[WARNING]${NC} $1"; }
print_error() { echo -e "${RED}[ERROR]${NC} $1"; }

show_help() {
    cat << EOF
FTC TeamCode Sync Script
========================

Syncs code between FtcRobotController and virtual_robot projects.

Usage:
  ./sync_teamcode.sh [direction] [options]

Directions:
  to-sim      Copy from FtcRobotController -> virtual_robot (default)
  from-sim    Copy from virtual_robot -> FtcRobotController

Options:
  --dry-run   Show what would be copied without actually copying
  --hardware  Also sync the hardware abstraction layer
  --opmodes   Sync only opmodes (default behavior)
  --all       Sync everything (opmodes + hardware)
  -h, --help  Show this help message

Examples:
  ./sync_teamcode.sh to-sim              # Sync opmodes to simulator
  ./sync_teamcode.sh to-sim --all        # Sync everything to simulator
  ./sync_teamcode.sh from-sim --hardware # Sync hardware from simulator
  ./sync_teamcode.sh to-sim --dry-run    # Preview what would be synced

Project Paths:
  FtcRobotController: $FTC_TEAMCODE
  virtual_robot:      $VIRTUAL_TEAMCODE

EOF
}

# Parse arguments
while [[ $# -gt 0 ]]; do
    case $1 in
        to-sim)
            DIRECTION="to-sim"
            shift
            ;;
        from-sim)
            DIRECTION="from-sim"
            shift
            ;;
        --dry-run)
            DRY_RUN=true
            shift
            ;;
        --hardware)
            SYNC_HARDWARE=true
            shift
            ;;
        --opmodes)
            SYNC_OPMODES=true
            SYNC_HARDWARE=false
            shift
            ;;
        --all)
            SYNC_HARDWARE=true
            SYNC_OPMODES=true
            shift
            ;;
        -h|--help)
            show_help
            exit 0
            ;;
        *)
            print_error "Unknown option: $1"
            show_help
            exit 1
            ;;
    esac
done

# Verify projects exist
if [[ ! -d "$FTC_TEAMCODE" ]]; then
    print_error "FtcRobotController TeamCode not found at: $FTC_TEAMCODE"
    exit 1
fi

if [[ ! -d "$VIRTUAL_TEAMCODE" ]]; then
    print_error "virtual_robot TeamCode not found at: $VIRTUAL_TEAMCODE"
    exit 1
fi

# Set source and destination based on direction
if [[ "$DIRECTION" == "to-sim" ]]; then
    SRC="$FTC_TEAMCODE"
    DST="$VIRTUAL_TEAMCODE"
    print_info "Syncing: FtcRobotController -> virtual_robot"
else
    SRC="$VIRTUAL_TEAMCODE"
    DST="$FTC_TEAMCODE"
    print_info "Syncing: virtual_robot -> FtcRobotController"
fi

if [[ "$DRY_RUN" == true ]]; then
    print_warning "DRY RUN - No files will be modified"
fi

echo ""

# Sync function
sync_directory() {
    local src_dir="$1"
    local dst_dir="$2"
    local name="$3"

    if [[ ! -d "$src_dir" ]]; then
        print_warning "Source directory not found: $src_dir"
        return
    fi

    print_info "Syncing $name..."

    # Create destination if it doesn't exist
    if [[ "$DRY_RUN" == false ]]; then
        mkdir -p "$dst_dir"
    fi

    # Find and copy files
    local count=0
    while IFS= read -r -d '' file; do
        local rel_path="${file#$src_dir/}"
        local dst_file="$dst_dir/$rel_path"
        local dst_file_dir="$(dirname "$dst_file")"

        if [[ "$DRY_RUN" == true ]]; then
            echo "  Would copy: $rel_path"
        else
            mkdir -p "$dst_file_dir"
            cp "$file" "$dst_file"
            echo "  Copied: $rel_path"
        fi
        ((count++))
    done < <(find "$src_dir" -name "*.java" -type f -print0)

    if [[ $count -eq 0 ]]; then
        print_warning "No Java files found in $src_dir"
    else
        print_success "Processed $count files in $name"
    fi
}

# Sync opmodes
if [[ "$SYNC_OPMODES" == true ]]; then
    sync_directory "$SRC/opmodes" "$DST/opmodes" "opmodes"
    echo ""
fi

# Sync hardware abstraction layer
if [[ "$SYNC_HARDWARE" == true ]]; then
    # Sync interfaces (these should be identical)
    sync_directory "$SRC/hardware/interfaces" "$DST/hardware/interfaces" "hardware/interfaces"
    echo ""

    # Sync mock implementations (these should be identical)
    sync_directory "$SRC/hardware/mock" "$DST/hardware/mock" "hardware/mock"
    echo ""

    print_warning "Note: hardware/real/ and RobotHardware.java are NOT synced"
    print_warning "These files have platform-specific implementations"
    echo ""
fi

# Summary
echo "========================================"
if [[ "$DRY_RUN" == true ]]; then
    print_warning "DRY RUN COMPLETE - No files were modified"
else
    print_success "Sync complete!"
fi

echo ""
print_info "Next steps:"
if [[ "$DIRECTION" == "to-sim" ]]; then
    echo "  1. Open virtual_robot in IntelliJ IDEA"
    echo "  2. Run the simulator (Main.java)"
    echo "  3. Select your OpMode from the dropdown"
else
    echo "  1. Open FtcRobotController in Android Studio"
    echo "  2. Build and deploy to your robot"
fi
