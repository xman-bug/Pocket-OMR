#!/bin/bash

# GitHub Secrets Setup Script
# This script helps configure the required secrets for the Android build workflow

echo "🔧 GitHub Secrets Setup for Android Build Workflow"
echo "=================================================="
echo

# Check if gh CLI is available
if command -v gh &> /dev/null; then
    echo "✅ GitHub CLI (gh) found"
    
    # Check if authenticated
    if gh auth status &> /dev/null; then
        echo "✅ Already authenticated with GitHub"
        
        echo
        echo "🔑 Setting up secrets..."
        echo "⚠️  Note: You'll need to provide your own keystore values"
        echo
        
        # Prompt for secrets
        read -p "Enter KEYSTORE_BASE64 (base64 encoded keystore): " KEYSTORE_BASE64
        read -p "Enter KEYSTORE_PASSWORD: " KEYSTORE_PASSWORD
        read -p "Enter KEY_ALIAS: " KEY_ALIAS
        read -p "Enter KEY_PASSWORD: " KEY_PASSWORD
        
        # Set secrets
        echo "$KEYSTORE_BASE64" | gh secret set KEYSTORE_BASE64
        echo "$KEYSTORE_PASSWORD" | gh secret set KEYSTORE_PASSWORD
        echo "$KEY_ALIAS" | gh secret set KEY_ALIAS
        echo "$KEY_PASSWORD" | gh secret set KEY_PASSWORD
        
        echo "✅ All secrets configured successfully!"
        echo
        echo "🚀 You can now trigger the workflow by:"
        echo "   1. Creating a pull request to main branch"
        echo "   2. Pushing directly to main branch"
        
    else
        echo "❌ Please authenticate with GitHub first:"
        echo "   gh auth login"
    fi
else
    echo "ℹ️  GitHub CLI not found. Please configure secrets manually:"
    echo
    echo "📋 Go to your repository settings:"
    echo "   https://github.com/USERNAME/REPO/settings/secrets/actions"
    echo
    echo "🔑 Add these four secrets:"
    echo "   - KEYSTORE_BASE64 (base64 encoded keystore file)"
    echo "   - KEYSTORE_PASSWORD (keystore password)" 
    echo "   - KEY_ALIAS (signing key alias)"
    echo "   - KEY_PASSWORD (signing key password)"
    echo
    echo "📄 See README.md for detailed setup instructions"
fi

echo
echo "📊 Current repository: $(git remote get-url origin | sed 's/.*github.com\///g' | sed 's/.git$//')"
echo "🌿 Current branch: $(git branch --show-current)"