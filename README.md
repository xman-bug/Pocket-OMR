# HandwrittenOCRSumCam 📱

A powerful Android application for Optical Character Recognition (OCR) of handwritten text using camera input, with automatic calculation and summarization capabilities.

[![Build Status](https://github.com/USERNAME/HandwrittenOCRSumCam/actions/workflows/android-build.yml/badge.svg)](https://github.com/USERNAME/HandwrittenOCRSumCam/actions)
[![Release](https://img.shields.io/github/v/release/USERNAME/HandwrittenOCRSumCam)](https://github.com/USERNAME/HandwrittenOCRSumCam/releases)
[![Downloads](https://img.shields.io/github/downloads/USERNAME/HandwrittenOCRSumCam/total)](https://github.com/USERNAME/HandwrittenOCRSumCam/releases)

## 🚀 Quick Start

### Download APK
- **[Latest Release](../../releases/latest)** - Stable production version
- **[All Releases](../../releases)** - Complete version history
- **[Build Artifacts](../../actions)** - Latest builds from each commit

### Installation
1. Download the APK from [Releases](../../releases/latest)
2. Enable "Install unknown apps" in Android Settings → Security
3. Install the APK and launch the app
4. Point your camera at handwritten text and start scanning!

## ✨ Features

- 🔍 **Real-time OCR**: Extract text from handwritten documents using your camera
- 📱 **Camera Integration**: Use CameraX for smooth and reliable camera functionality
- 🤖 **ML Kit Integration**: Google's ML Kit for accurate text recognition
- 📊 **Text Processing**: Advanced text processing and summarization
- 🎯 **User-friendly UI**: Clean and intuitive Material Design interface
- 📋 **Multiple Formats**: Support for various document types and handwriting styles
- ⚡ **Fast Processing**: Optimized for real-time text recognition
- 🔧 **Customizable**: Configurable settings for different use cases

## 🛠️ Development

### Prerequisites
- **JDK 17** or higher
- **Android SDK 34**
- **Gradle 8.0+**
- **2GB+ RAM** for building

### Local Development Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/USERNAME/HandwrittenOCRSumCam.git
   cd HandwrittenOCRSumCam
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned directory

3. **Build the project**
   ```bash
   # Build debug APK
   ./gradlew assembleDebug
   
   # Build release APK (requires keystore setup)
   ./gradlew assembleRelease
   
   # Clean build
   ./gradlew clean
   ```

### Using the Build Script
For easier local builds, use the provided script:

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

## 🔐 Release Signing

For release builds, configure signing in `app/build.gradle`:

```gradle
android {
    signingConfigs {
        release {
            storeFile file("keystore.jks")
            storePassword System.getenv("KEYSTORE_PASSWORD")
            keyAlias System.getenv("KEY_ALIAS")
            keyPassword System.getenv("KEY_PASSWORD")
        }
    }
}
```

Set environment variables:
```bash
export KEYSTORE_PASSWORD="your_keystore_password"
export KEY_ALIAS="your_key_alias"
export KEY_PASSWORD="your_key_password"
```

## 🏗️ CI/CD Pipeline

This project includes automated build pipelines using GitHub Actions:

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

### Required GitHub Secrets
Configure these secrets in your repository settings:

1. **`KEYSTORE_BASE64`** - Base64 encoded keystore file
2. **`KEYSTORE_PASSWORD`** - Keystore password
3. **`KEY_ALIAS`** - Signing key alias
4. **`KEY_PASSWORD`** - Signing key password

## 📊 Project Structure

```
├── app/                        # Main application module
│   ├── src/main/              # Source code
│   │   ├── java/              # Java/Kotlin source files
│   │   ├── res/               # Resources (layouts, drawables, etc.)
│   │   └── AndroidManifest.xml # App manifest
│   ├── build.gradle           # App-level Gradle config
│   ├── proguard-rules.pro     # ProGuard configuration
│   └── debug.keystore         # Debug keystore
├── .github/workflows/         # GitHub Actions workflows
│   ├── android-build.yml      # Main build workflow
│   └── manual-build.yml       # Manual build workflow
├── gradle/                    # Gradle wrapper
├── build-apk.sh              # Local build script
├── build.gradle              # Root project Gradle config
├── settings.gradle           # Project settings
└── README.md                 # This file
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

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Make your changes
4. Test thoroughly (both debug and release builds)
5. Commit your changes (`git commit -m 'Add amazing feature'`)
6. Push to the branch (`git push origin feature/amazing-feature`)
7. Open a Pull Request

### Development Guidelines
- Use `debug` builds for development
- Test on multiple Android versions
- Ensure release builds work before submitting
- Follow Material Design guidelines
- Add appropriate tests for new features

## 🐛 Troubleshooting

### Common Issues

#### Build Failures
- Ensure JDK 17+ is installed and set as JAVA_HOME
- Clean and rebuild: `./gradlew clean build`
- Check Android SDK installation

#### Installation Issues
- Enable "Install unknown apps" in Android settings
- Ensure sufficient storage space (50MB+)
- Check Android version compatibility (API 24+)

#### Camera Issues
- Grant camera permissions when prompted
- Ensure camera is not being used by another app
- Check device camera functionality

### Getting Help
- 📖 **Documentation**: Check this README and [APK Download Guide](APK_DOWNLOAD_GUIDE.md)
- 🐛 **Issues**: [GitHub Issues](../../issues)
- 🔧 **Build Status**: [GitHub Actions](../../actions)
- 📦 **Releases**: [GitHub Releases](../../releases)

## 📜 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙋‍♂️ Support

- 📖 **Documentation**: [APK Download Guide](APK_DOWNLOAD_GUIDE.md)
- 🐛 **Issues**: [GitHub Issues](../../issues)
- 🔧 **Build Status**: [GitHub Actions](../../actions)
- 📦 **Releases**: [GitHub Releases](../../releases)

## 🔗 Quick Links

- [Download Latest APK](../../releases/latest)
- [View Build Status](../../actions)
- [Report Issues](../../issues)
- [APK Download Guide](APK_DOWNLOAD_GUIDE.md)

---

**Made with ❤️ for handwritten text recognition**

*Last updated: January 2024*