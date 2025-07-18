#!/bin/bash

# HandwrittenOCRSumCam APK Build Script
# This script helps you build APKs locally

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print colored output
print_info() {
    echo -e "${BLUE}ℹ️  $1${NC}"
}

print_success() {
    echo -e "${GREEN}✅ $1${NC}"
}

print_warning() {
    echo -e "${YELLOW}⚠️  $1${NC}"
}

print_error() {
    echo -e "${RED}❌ $1${NC}"
}

# Function to show usage
show_usage() {
    echo "Usage: $0 [OPTIONS]"
    echo ""
    echo "Options:"
    echo "  -t, --type <debug|release|both>   Build type (default: debug)"
    echo "  -c, --clean                       Clean build before building"
    echo "  -h, --help                        Show this help message"
    echo ""
    echo "Examples:"
    echo "  $0                                # Build debug APK"
    echo "  $0 -t release                     # Build release APK"
    echo "  $0 -t both -c                     # Clean build both APKs"
}

# Default values
BUILD_TYPE="debug"
CLEAN_BUILD=false

# Parse command line arguments
while [[ $# -gt 0 ]]; do
    case $1 in
        -t|--type)
            BUILD_TYPE="$2"
            shift 2
            ;;
        -c|--clean)
            CLEAN_BUILD=true
            shift
            ;;
        -h|--help)
            show_usage
            exit 0
            ;;
        *)
            print_error "Unknown option: $1"
            show_usage
            exit 1
            ;;
    esac
done

# Validate build type
if [[ "$BUILD_TYPE" != "debug" && "$BUILD_TYPE" != "release" && "$BUILD_TYPE" != "both" ]]; then
    print_error "Invalid build type: $BUILD_TYPE. Must be 'debug', 'release', or 'both'"
    exit 1
fi

print_info "HandwrittenOCRSumCam APK Build Script"
print_info "======================================"

# Check if gradlew exists
if [ ! -f "gradlew" ]; then
    print_error "gradlew not found. Make sure you're in the project root directory."
    exit 1
fi

# Make gradlew executable
chmod +x gradlew

# Check for keystore (release builds)
if [[ "$BUILD_TYPE" == "release" || "$BUILD_TYPE" == "both" ]]; then
    if [ ! -f "app/keystore.jks" ]; then
        print_warning "Release keystore not found at app/keystore.jks"
        print_warning "You may need to set up signing configuration for release builds"
        print_warning "Check configure-secrets.md for setup instructions"
    fi
    
    if [ -z "$KEYSTORE_PASSWORD" ] || [ -z "$KEY_ALIAS" ] || [ -z "$KEY_PASSWORD" ]; then
        print_warning "Release signing environment variables not set:"
        print_warning "  KEYSTORE_PASSWORD, KEY_ALIAS, KEY_PASSWORD"
        print_warning "Release build may fail without proper signing configuration"
    fi
fi

# Clean build if requested
if [ "$CLEAN_BUILD" = true ]; then
    print_info "Cleaning previous builds..."
    ./gradlew clean
    print_success "Clean completed"
fi

# Build APKs based on type
case $BUILD_TYPE in
    "debug")
        print_info "Building debug APK..."
        ./gradlew assembleDebug
        ;;
    "release")
        print_info "Building release APK..."
        ./gradlew assembleRelease
        ;;
    "both")
        print_info "Building both debug and release APKs..."
        ./gradlew assembleDebug assembleRelease
        ;;
esac

print_success "Build completed!"

# Show output information
print_info "APK Output Locations:"
echo ""

if [[ "$BUILD_TYPE" == "debug" || "$BUILD_TYPE" == "both" ]]; then
    DEBUG_APK="app/build/outputs/apk/debug/app-debug.apk"
    if [ -f "$DEBUG_APK" ]; then
        DEBUG_SIZE=$(stat -c%s "$DEBUG_APK" 2>/dev/null || stat -f%z "$DEBUG_APK" 2>/dev/null || echo "Unknown")
        if [ "$DEBUG_SIZE" != "Unknown" ]; then
            DEBUG_SIZE_MB=$(echo "scale=2; $DEBUG_SIZE / 1024 / 1024" | bc 2>/dev/null || echo "$(($DEBUG_SIZE / 1024 / 1024))")
        else
            DEBUG_SIZE_MB="Unknown"
        fi
        print_success "Debug APK: $DEBUG_APK (${DEBUG_SIZE_MB} MB)"
    else
        print_error "Debug APK not found at expected location"
    fi
fi

if [[ "$BUILD_TYPE" == "release" || "$BUILD_TYPE" == "both" ]]; then
    RELEASE_APK="app/build/outputs/apk/release/app-release.apk"
    if [ -f "$RELEASE_APK" ]; then
        RELEASE_SIZE=$(stat -c%s "$RELEASE_APK" 2>/dev/null || stat -f%z "$RELEASE_APK" 2>/dev/null || echo "Unknown")
        if [ "$RELEASE_SIZE" != "Unknown" ]; then
            RELEASE_SIZE_MB=$(echo "scale=2; $RELEASE_SIZE / 1024 / 1024" | bc 2>/dev/null || echo "$(($RELEASE_SIZE / 1024 / 1024))")
        else
            RELEASE_SIZE_MB="Unknown"
        fi
        print_success "Release APK: $RELEASE_APK (${RELEASE_SIZE_MB} MB)"
    else
        print_error "Release APK not found at expected location"
    fi
fi

print_info ""
print_info "Installation Instructions:"
print_info "1. Transfer the APK to your Android device"
print_info "2. Enable 'Install unknown apps' in Settings"
print_info "3. Install the APK file"
print_info ""
print_info "For more information, see APK_DOWNLOAD_GUIDE.md"