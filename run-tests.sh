#!/bin/bash

# Test Runner Script for Handwritten OCR SumCam Android App
# This script runs comprehensive tests including unit tests and instrumentation tests

set -e  # Exit on any error

echo "=========================================="
echo "Handwritten OCR SumCam - Test Suite Runner"
echo "=========================================="

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    local color=$1
    local message=$2
    echo -e "${color}${message}${NC}"
}

# Function to check if command exists
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Check prerequisites
print_status $BLUE "Checking prerequisites..."

if ! command_exists java; then
    print_status $RED "Error: Java is not installed or not in PATH"
    exit 1
fi

if ! command_exists adb; then
    print_status $YELLOW "Warning: ADB not found. Instrumentation tests may not work."
fi

# Check if we're in the correct directory
if [ ! -f "build.gradle" ]; then
    print_status $RED "Error: build.gradle not found. Please run this script from the project root."
    exit 1
fi

print_status $GREEN "Prerequisites check passed!"

# Clean previous builds
print_status $BLUE "Cleaning previous builds..."
./gradlew clean

# Run unit tests
print_status $BLUE "Running unit tests..."
if ./gradlew test; then
    print_status $GREEN "Unit tests passed!"
else
    print_status $RED "Unit tests failed!"
    exit 1
fi

# Check if device/emulator is available for instrumentation tests
print_status $BLUE "Checking for Android device/emulator..."
if command_exists adb; then
    if adb devices | grep -q "device$"; then
        print_status $GREEN "Android device found. Running instrumentation tests..."
        
        # Install the app
        print_status $BLUE "Installing app on device..."
        ./gradlew installDebug
        
        # Run instrumentation tests
        if ./gradlew connectedAndroidTest; then
            print_status $GREEN "Instrumentation tests passed!"
        else
            print_status $RED "Instrumentation tests failed!"
            exit 1
        fi
    else
        print_status $YELLOW "No Android device/emulator found. Skipping instrumentation tests."
        print_status $YELLOW "To run instrumentation tests, connect a device or start an emulator."
    fi
else
    print_status $YELLOW "ADB not available. Skipping instrumentation tests."
fi

# Run comprehensive test suite using TestUtils
print_status $BLUE "Running comprehensive test suite..."
./gradlew test --tests "com.example.handwrittenocrsumcam.TestUtilsTest"

# Generate test report
print_status $BLUE "Generating test report..."
./gradlew testDebugUnitTest
./gradlew createDebugCoverageReport

# Check test results
if [ -d "app/build/reports/tests" ]; then
    print_status $GREEN "Test reports generated in app/build/reports/tests/"
else
    print_status $YELLOW "Test reports directory not found."
fi

# Run lint checks
print_status $BLUE "Running lint checks..."
if ./gradlew lint; then
    print_status $GREEN "Lint checks passed!"
else
    print_status $YELLOW "Lint checks found issues. Check app/build/reports/lint-results.html"
fi

# Run build verification
print_status $BLUE "Running build verification..."
if ./gradlew assembleDebug; then
    print_status $GREEN "Build verification passed!"
else
    print_status $RED "Build verification failed!"
    exit 1
fi

# Summary
print_status $GREEN "=========================================="
print_status $GREEN "Test Suite Summary:"
print_status $GREEN "✓ Unit tests completed"
print_status $GREEN "✓ Build verification completed"
print_status $GREEN "✓ Lint checks completed"
if command_exists adb && adb devices | grep -q "device$"; then
    print_status $GREEN "✓ Instrumentation tests completed"
else
    print_status $YELLOW "⚠ Instrumentation tests skipped (no device)"
fi
print_status $GREEN "=========================================="

print_status $GREEN "All tests completed successfully!"
print_status $BLUE "Test reports available in:"
print_status $BLUE "  - Unit tests: app/build/reports/tests/"
print_status $BLUE "  - Coverage: app/build/reports/coverage/"
print_status $BLUE "  - Lint: app/build/reports/lint-results.html"

echo ""
print_status $BLUE "To run specific test categories:"
print_status $BLUE "  Unit tests only: ./gradlew test"
print_status $BLUE "  Instrumentation tests only: ./gradlew connectedAndroidTest"
print_status $BLUE "  Lint only: ./gradlew lint"
print_status $BLUE "  Build only: ./gradlew assembleDebug"