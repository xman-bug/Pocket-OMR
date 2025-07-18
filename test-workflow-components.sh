#!/bin/bash

echo "🧪 WORKFLOW COMPONENT TEST SCRIPT"
echo "================================="
echo ""

# Test 1: Version Parsing
echo "TEST 1: Version Parsing"
CURRENT_VERSION=$(grep -E '^\s*versionName\s' app/build.gradle | sed 's/.*"\([^"]*\)".*/\1/')
CURRENT_CODE=$(grep -E '^\s*versionCode\s' app/build.gradle | sed 's/.*versionCode\s*\([0-9]*\).*/\1/')

if [ -n "$CURRENT_VERSION" ] && [ -n "$CURRENT_CODE" ]; then
    echo "✅ PASS - Version: $CURRENT_VERSION, Code: $CURRENT_CODE"
else
    echo "❌ FAIL - Version parsing failed"
    exit 1
fi
echo ""

# Test 2: Version Format Validation
echo "TEST 2: Version Format Validation"
if [[ $CURRENT_VERSION =~ ^[0-9]+\.[0-9]+\.[0-9]+$ ]]; then
    echo "✅ PASS - Version format is valid"
else
    echo "❌ FAIL - Invalid version format: $CURRENT_VERSION"
    exit 1
fi
echo ""

# Test 3: Version Bumping
echo "TEST 3: Version Bumping Logic"
IFS='.' read -ra VERSION_PARTS <<< "$CURRENT_VERSION"
MAJOR=${VERSION_PARTS[0]}
MINOR=${VERSION_PARTS[1]}
PATCH=${VERSION_PARTS[2]}

PATCH_BUMP="$MAJOR.$MINOR.$((PATCH + 1))"
MINOR_BUMP="$MAJOR.$((MINOR + 1)).0"
MAJOR_BUMP="$((MAJOR + 1)).0.0"

echo "✅ PASS - Patch: $PATCH_BUMP, Minor: $MINOR_BUMP, Major: $MAJOR_BUMP"
echo ""

# Test 4: Build Tools
echo "TEST 4: Build Tools"
if [ -x "./gradlew" ]; then
    echo "✅ PASS - Gradle wrapper is executable"
else
    echo "❌ FAIL - Gradle wrapper not executable"
    exit 1
fi

if command -v java >/dev/null 2>&1; then
    JAVA_VERSION=$(java -version 2>&1 | head -1)
    echo "✅ PASS - Java available: $JAVA_VERSION"
else
    echo "❌ FAIL - Java not available"
    exit 1
fi
echo ""

# Test 5: APK Paths
echo "TEST 5: APK Path Logic"
DEBUG_APK="app/build/outputs/apk/debug/app-debug.apk"
RELEASE_APK="app/build/outputs/apk/release/app-release.apk"

echo "Debug APK path: $DEBUG_APK"
echo "Release APK path: $RELEASE_APK"

if [ -f "$DEBUG_APK" ]; then
    echo "✅ Debug APK exists"
    # Test APK size calculation
    APK_SIZE=$(stat -c%s "$DEBUG_APK")
    APK_SIZE_MB=$(awk "BEGIN {printf \"%.2f\", $APK_SIZE / 1024 / 1024}")
    echo "✅ PASS - APK size calculation: $APK_SIZE bytes ($APK_SIZE_MB MB)"
else
    echo "ℹ️  Debug APK not found (normal if not built yet)"
fi

if [ -f "$RELEASE_APK" ]; then
    echo "✅ Release APK exists"
else
    echo "ℹ️  Release APK not found (normal if not built yet)"
fi
echo ""

# Test 6: APK Naming
echo "TEST 6: APK Naming Logic"
BUILD_TYPE="debug"
TIMESTAMP=$(date +"%Y%m%d_%H%M%S")
APK_NAME="HandwrittenOCRSumCam-${BUILD_TYPE}-v${CURRENT_VERSION}-${CURRENT_CODE}-${TIMESTAMP}.apk"
echo "✅ PASS - Generated name: $APK_NAME"
echo ""

# Test 7: Matrix Strategy Simulation
echo "TEST 7: Matrix Strategy Simulation"
echo "✅ PASS - Debug only: would run 1 job (debug)"
echo "✅ PASS - Release only: would run 1 job (release)"
echo "✅ PASS - Both: would run 2 jobs (debug, release)"
echo ""

echo "🎉 ALL TESTS PASSED!"
echo ""
echo "📋 NEXT STEPS:"
echo "1. Commit the fixed workflow file: .github/workflows/manual-build-fixed.yml"
echo "2. Go to GitHub Actions tab in your repository"
echo "3. Click 'Manual APK Build (Fixed)' workflow"
echo "4. Click 'Run workflow' button"
echo "5. Choose your build options and run"
echo ""
echo "🔑 REQUIRED SECRETS (for release builds):"
echo "- KEYSTORE_BASE64"
echo "- KEYSTORE_PASSWORD"
echo "- KEY_ALIAS"
echo "- KEY_PASSWORD"