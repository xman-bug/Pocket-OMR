# 📱 APK Download Guide

## Overview

This repository automatically builds downloadable APK files for the HandwrittenOCRSumCam Android application. You can download APKs in multiple ways depending on your needs.

## 🚀 Quick Download Methods

### Method 1: GitHub Releases (Recommended)
1. Go to the [Releases page](../../releases)
2. Find the latest release or the version you want
3. Download either:
   - `HandwrittenOCRSumCam-release-*.apk` (Production version)
   - `HandwrittenOCRSumCam-debug-*.apk` (Development version)

### Method 2: GitHub Actions Artifacts
1. Go to the [Actions tab](../../actions)
2. Click on the latest successful workflow run
3. Scroll down to the "Artifacts" section
4. Download the APK file you need

### Method 3: Manual Build Trigger
1. Go to [Actions → Manual APK Build](../../actions/workflows/manual-build.yml)
2. Click "Run workflow"
3. Choose your build options:
   - **Build Type**: Debug, Release, or Both
   - **Version Bump**: None, Patch, Minor, or Major
   - **Upload to Releases**: Whether to create a GitHub release
4. Click "Run workflow" and wait for completion
5. Download from artifacts or releases

## 📋 Build Types Explained

### Release APK
- **Purpose**: Production-ready version
- **Size**: Smaller (optimized with ProGuard)
- **Performance**: Faster, optimized
- **Debugging**: Limited debugging information
- **Signing**: Signed with release keystore
- **File naming**: `HandwrittenOCRSumCam-release-v1.0.0-1.apk`

### Debug APK
- **Purpose**: Development and testing
- **Size**: Larger (includes debug symbols)
- **Performance**: Slower (debug builds)
- **Debugging**: Full debugging information
- **Signing**: Signed with debug keystore
- **File naming**: `HandwrittenOCRSumCam-debug-v1.0.0-1.apk`

## 🔧 Installation Instructions

### Prerequisites
- Android device with API level 24 (Android 7.0) or higher
- Minimum 50MB free storage space

### Installation Steps
1. **Download the APK file** using one of the methods above
2. **Enable Unknown Sources**:
   - Go to Settings → Security (or Privacy)
   - Enable "Install unknown apps" or "Unknown sources"
   - Allow installation from your browser/file manager
3. **Install the APK**:
   - Open the downloaded APK file
   - Tap "Install" when prompted
   - Wait for installation to complete
4. **Launch the app** from your app drawer

### Security Note
⚠️ Only download APKs from this official repository. Third-party APKs may contain malware.

## 🔄 Automatic Builds

### When APKs are Built
- **Push to main branch**: Automatic build and release
- **Push to develop branch**: Automatic build (artifacts only)
- **Tagged releases**: Automatic build with GitHub release
- **Pull requests**: Build for testing (artifacts only)
- **Manual trigger**: On-demand builds

### Build Triggers
```yaml
# Automatic triggers
- main branch push
- develop branch push  
- Version tags (v*)
- Pull requests

# Manual triggers
- GitHub Actions UI
- Workflow dispatch API
```

## 📊 Build Information

### Version Naming
- **Version Name**: Semantic versioning (e.g., 1.0.0)
- **Version Code**: Incremental number for Play Store
- **Build Number**: GitHub Actions run number

### APK Naming Convention
```
HandwrittenOCRSumCam-{type}-v{version}-{code}-{timestamp}.apk
```

Examples:
- `HandwrittenOCRSumCam-release-v1.0.0-1-20240115_143022.apk`
- `HandwrittenOCRSumCam-debug-v1.0.0-1-20240115_143022.apk`

## 🛠️ Advanced Options

### Build Configuration
The project supports various build configurations:

```gradle
buildTypes {
    debug {
        applicationIdSuffix ".debug"
        versionNameSuffix "-debug"
        buildConfigField "boolean", "DEBUG_MODE", "true"
    }
    release {
        minifyEnabled true
        shrinkResources true
        buildConfigField "boolean", "DEBUG_MODE", "false"
    }
}
```

### APK Optimization
Release APKs include:
- ProGuard code obfuscation
- Resource shrinking
- APK size optimization
- Unused code removal

## 🐛 Troubleshooting

### Common Issues

#### "App not installed" error
- Check available storage space
- Ensure Android version compatibility (API 24+)
- Try uninstalling previous version first

#### "Unknown sources" warning
- This is normal for APKs not from Play Store
- Follow the installation instructions above

#### APK corruption
- Re-download the APK file
- Check file size matches the expected size
- Try a different download method

### Getting Help
If you encounter issues:
1. Check the [Issues page](../../issues) for similar problems
2. Create a new issue with:
   - Your Android version
   - Device model
   - APK version tried
   - Error message/screenshot

## 📈 Build Status

Current build status: ![Build Status](../../actions/workflows/android-build.yml/badge.svg)

### Build History
You can view all build history in the [Actions tab](../../actions), including:
- Build success/failure status
- Build duration
- APK sizes
- Test results

## 🔐 Security & Signing

### Release Builds
- Signed with production keystore
- SHA-256 fingerprint available in releases
- Verified by GitHub Actions

### Debug Builds  
- Signed with debug keystore
- For development use only
- Not suitable for production

---

*Last updated: $(date)*
*For more information, see the main [README.md](README.md)*