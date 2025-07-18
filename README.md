# HandwrittenOCRSumCam

A powerful Android application for Optical Character Recognition (OCR) of handwritten text using camera input, with automatic calculation and summarization capabilities.

[![Build Status](https://github.com/USERNAME/HandwrittenOCRSumCam/actions/workflows/android-build.yml/badge.svg)](https://github.com/USERNAME/HandwrittenOCRSumCam/actions)
[![Release](https://img.shields.io/github/v/release/USERNAME/HandwrittenOCRSumCam)](https://github.com/USERNAME/HandwrittenOCRSumCam/releases)
[![Downloads](https://img.shields.io/github/downloads/USERNAME/HandwrittenOCRSumCam/total)](https://github.com/USERNAME/HandwrittenOCRSumCam/releases)

## 📱 Download APK

### 🚀 Quick Download
[![Download APK](https://img.shields.io/badge/Download-APK-brightgreen?style=for-the-badge&logo=android)](https://github.com/USERNAME/HandwrittenOCRSumCam/releases/latest)

### Download Options
- **[GitHub Releases](../../releases)** - Stable versions with full release notes
- **[GitHub Actions](../../actions)** - Latest builds from each commit
- **[Manual Build](../../actions/workflows/manual-build.yml)** - Trigger custom builds on demand

For detailed download and installation instructions, see **[APK Download Guide](APK_DOWNLOAD_GUIDE.md)**.

## ✨ Features

- 🔍 **Real-time OCR**: Extract text from handwritten documents using your camera
- 📱 **Camera Integration**: Use CameraX for smooth and reliable camera functionality
- 🤖 **ML Kit Integration**: Google's ML Kit for accurate text recognition
- 📊 **Text Processing**: Advanced text processing and summarization
- 🎯 **User-friendly UI**: Clean and intuitive Material Design interface
- 📋 **Multiple Formats**: Support for various document types and handwriting styles
- ⚡ **Fast Processing**: Optimized for real-time text recognition
- 🔧 **Customizable**: Configurable settings for different use cases

## 🛠️ Build Types

### Release APK
- ✅ **Production ready** - Optimized and signed for distribution
- 📦 **Smaller size** - ProGuard optimization reduces APK size
- 🚀 **Better performance** - Release optimizations enabled
- 🔐 **Signed** - Properly signed with release keystore

### Debug APK
- 🔧 **Development version** - Contains debug information
- 📊 **Debugging enabled** - Full logging and debugging capabilities
- 🎯 **Testing friendly** - Ideal for testing and development
- 📱 **Side-by-side install** - Can be installed alongside release version

## 🚀 Quick Start

### For Users
1. Download the latest APK from [Releases](../../releases)
2. Enable "Install unknown apps" in your Android settings
3. Install the APK and launch the app
4. Point your camera at handwritten text and start scanning!

### For Developers
1. Clone the repository
2. Open in Android Studio
3. Build and run:
   ```bash
   ./build-apk.sh -t debug
   ```

## 📋 Requirements

### Device Requirements
- **Android 7.0 (API 24)** or higher
- **Camera permission** for text scanning
- **50MB+ free storage** for app installation
- **Internet connection** for ML Kit models (first run)

### Build Requirements
- **JDK 17** or higher
- **Android SDK 34**
- **Gradle 8.0+**
- **2GB+ RAM** for building

## 🔧 Local Development

### Building APKs Locally
Use the provided build script for easy local builds:

```bash
# Build debug APK
./build-apk.sh

# Build release APK
./build-apk.sh -t release

# Build both with clean
./build-apk.sh -t both -c

# Show help
./build-apk.sh -h
```

### Manual Gradle Commands
```bash
# Debug build
./gradlew assembleDebug

# Release build (requires keystore setup)
./gradlew assembleRelease

# Clean build
./gradlew clean
```

## 🔐 Signing Configuration

For release builds, you'll need to set up signing:

1. Generate or obtain a keystore file
2. Set environment variables:
   ```bash
   export KEYSTORE_PASSWORD="your_keystore_password"
   export KEY_ALIAS="your_key_alias"
   export KEY_PASSWORD="your_key_password"
   ```
3. See [configure-secrets.md](configure-secrets.md) for detailed setup

## 🏗️ CI/CD Pipeline

This project includes automated build pipelines:

### Automatic Builds
- ✅ **Push to main** → Build + Release
- ✅ **Push to develop** → Build artifacts
- ✅ **Tagged releases** → GitHub releases
- ✅ **Pull requests** → Build verification

### Manual Builds
- 🎯 **On-demand builds** via GitHub Actions
- ⚙️ **Configurable options** (debug/release/both)
- 📈 **Version bumping** (patch/minor/major)
- 📦 **Automatic releases** optional

## 📊 Project Structure

```
├── app/                        # Main application module
│   ├── src/main/              # Source code
│   ├── build.gradle           # App-level Gradle config
│   ├── proguard-rules.pro     # ProGuard configuration
│   ├── keystore.jks           # Release keystore (not in repo)
│   └── debug.keystore         # Debug keystore
├── .github/workflows/         # GitHub Actions workflows
│   ├── android-build.yml      # Main build workflow
│   └── manual-build.yml       # Manual build workflow
├── gradle/                    # Gradle wrapper
├── build-apk.sh              # Local build script
├── APK_DOWNLOAD_GUIDE.md     # Download instructions
└── README.md                 # This file
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly (both debug and release builds)
5. Submit a pull request

### Development Workflow
- Use `debug` builds for development
- Test on multiple Android versions
- Ensure release builds work before submitting
- Follow Material Design guidelines

## 📜 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙋‍♂️ Support

- 📖 **Documentation**: [APK Download Guide](APK_DOWNLOAD_GUIDE.md)
- 🐛 **Issues**: [GitHub Issues](../../issues)
- 🔧 **Build Status**: [GitHub Actions](../../actions)
- 📦 **Releases**: [GitHub Releases](../../releases)

## 🔗 Links

- [Download Latest APK](../../releases/latest)
- [View Build Status](../../actions)
- [Report Issues](../../issues)
- [APK Download Guide](APK_DOWNLOAD_GUIDE.md)

---

**Made with ❤️ for handwritten text recognition**