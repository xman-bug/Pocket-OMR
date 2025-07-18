# Android Build Workflow Test Plan

## ✅ Setup Complete

### Files Created & Committed:
- ✅ `.github/workflows/android-build.yml` - Enhanced workflow
- ✅ `gradlew` & `gradlew.bat` - Gradle wrapper scripts
- ✅ `gradle/wrapper/gradle-wrapper.properties` - Wrapper config
- ✅ `settings.gradle` - Root project settings
- ✅ `configure-secrets.md` - Secret values for testing
- ✅ `setup-github-secrets.sh` - Automated setup script

### Repository Status:
- **Repository**: `xman-bug/Pocket-OMR`
- **Branch**: `cursor/automate-android-app-release-dbf3`
- **Status**: All files committed and pushed ✅

## 🔑 Required: Configure GitHub Secrets

**IMPORTANT**: Before testing, configure these secrets in GitHub:

1. **Go to**: https://github.com/xman-bug/Pocket-OMR/settings/secrets/actions
2. **Add 4 secrets** with values from `configure-secrets.md`:
   - `KEYSTORE_BASE64`
   - `KEYSTORE_PASSWORD` 
   - `KEY_ALIAS`
   - `KEY_PASSWORD`

## 🧪 Test Scenarios

### Test 1: Pull Request Build (Build Only)
```bash
# Create a test commit
echo "Test change" >> README.md
git add README.md
git commit -m "test: trigger PR workflow"
git push origin cursor/automate-android-app-release-dbf3

# Then create PR to main branch in GitHub UI
```

**Expected Results:**
- ✅ Workflow triggers on PR creation
- ✅ Java 17 setup
- ✅ Gradle caching works
- ✅ APK builds successfully
- ✅ APK uploaded as artifact
- ❌ No GitHub release (PR only)

### Test 2: Main Branch Push (Build + Release)
```bash
# After PR is merged to main, or direct push to main
git checkout main
git merge cursor/automate-android-app-release-dbf3
git push origin main
```

**Expected Results:**
- ✅ Workflow triggers on main push
- ✅ APK builds successfully  
- ✅ APK uploaded as artifact
- ✅ GitHub release created with version `v1.0.{run_number}`
- ✅ APK attached to release

### Test 3: Keystore Signing Validation
```bash
# After successful build, check APK signing
unzip -l app/build/outputs/apk/release/app-release.apk | grep -i meta-inf
```

**Expected Files:**
- `META-INF/CERT.RSA`
- `META-INF/CERT.SF` 
- `META-INF/MANIFEST.MF`

### Test 4: Workflow Error Scenarios

#### Missing Secrets Test:
- Remove one secret → expect build failure
- Check logs for clear error message

#### Permission Issues Test:
- Workflow should handle `gradlew` permissions automatically
- No "Permission denied" errors

## 📊 Monitoring & Validation

### 1. GitHub Actions Tab
**URL**: https://github.com/xman-bug/Pocket-OMR/actions

**Check for:**
- Workflow runs appearing
- Green checkmarks (success)
- Build times (should be faster with caching)
- Artifact downloads working

### 2. Releases Page
**URL**: https://github.com/xman-bug/Pocket-OMR/releases

**Check for:**
- Automatic release creation
- Version numbering: `v1.0.{run_number}`
- APK file attachment
- Release notes with commit messages

### 3. Build Artifacts
**Access via**: Actions tab → specific run → Artifacts section

**Validate:**
- APK file size reasonable (>1MB)
- APK can be downloaded
- APK installs on Android device/emulator

## 🐛 Common Issues & Solutions

### Issue: "Keystore not found"
**Solution**: Verify `KEYSTORE_BASE64` secret is set correctly

### Issue: "Permission denied: gradlew"
**Solution**: Workflow includes `chmod +x ./gradlew` step

### Issue: "Java version mismatch"
**Solution**: Workflow uses Java 17, update local environment if needed

### Issue: "Gradle build fails"
**Solution**: Check `settings.gradle` includes `':app'` module

## 🎯 Success Criteria

### ✅ PR Workflow Success:
- [ ] Builds complete without errors
- [ ] APK artifact available for download
- [ ] Build completes in <5 minutes (with caching)
- [ ] No release created (correct for PR)

### ✅ Main Branch Workflow Success:
- [ ] All PR workflow criteria met
- [ ] GitHub release created automatically
- [ ] APK attached to release
- [ ] Version follows `v1.0.X` pattern
- [ ] Release notes include commit message

### ✅ Production Readiness:
- [ ] APK properly signed with test keystore
- [ ] Workflow secrets configured securely
- [ ] Documentation complete and accurate
- [ ] Caching improves subsequent build times

## 🚀 Next Steps After Testing

1. **Replace test keystore** with production keystore
2. **Update secret values** with real credentials
3. **Add additional quality gates** (tests, linting)
4. **Configure branch protection** rules
5. **Set up notification** for build failures

---

**Ready to test!** 🎉

Start by configuring the GitHub secrets, then create a pull request to trigger the first workflow run.