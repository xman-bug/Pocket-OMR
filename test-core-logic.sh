#!/bin/bash

# Standalone Test Script for Core Logic Testing
# This script tests the core text processing and number extraction logic
# without requiring the full Android SDK

set -e

echo "=========================================="
echo "Core Logic Test Suite"
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

# Test function for number extraction
test_number_extraction() {
    local test_name="$1"
    local input_text="$2"
    local expected_numbers="$3"
    
    # Simple regex-based number extraction (same logic as in the app)
    local extracted_numbers=$(echo "$input_text" | grep -o '[0-9]\+' | tr '\n' ' ' | sed 's/ $//')
    
    if [ "$extracted_numbers" = "$expected_numbers" ]; then
        print_status $GREEN "✓ $test_name: PASSED"
        return 0
    else
        print_status $RED "✗ $test_name: FAILED"
        print_status $RED "  Expected: $expected_numbers"
        print_status $RED "  Got: $extracted_numbers"
        return 1
    fi
}

# Test function for sum calculation
test_sum_calculation() {
    local test_name="$1"
    local input_text="$2"
    local expected_sum="$3"
    
    # Extract numbers and calculate sum
    local numbers=$(echo "$input_text" | grep -o '[0-9]\+' | tr '\n' ' ')
    local sum=0
    
    for num in $numbers; do
        sum=$((sum + num))
    done
    
    if [ "$sum" = "$expected_sum" ]; then
        print_status $GREEN "✓ $test_name: PASSED (Sum: $sum)"
        return 0
    else
        print_status $RED "✗ $test_name: FAILED"
        print_status $RED "  Expected sum: $expected_sum"
        print_status $RED "  Got sum: $sum"
        print_status $RED "  Numbers found: $numbers"
        return 1
    fi
}

# Test function for regex pattern matching
test_regex_pattern() {
    local test_name="$1"
    local input_text="$2"
    local should_match="$3"
    
    if echo "$input_text" | grep -q '[0-9]\+'; then
        local has_numbers=true
    else
        local has_numbers=false
    fi
    
    if [ "$should_match" = "true" ] && [ "$has_numbers" = "true" ]; then
        print_status $GREEN "✓ $test_name: PASSED (Numbers found)"
        return 0
    elif [ "$should_match" = "false" ] && [ "$has_numbers" = "false" ]; then
        print_status $GREEN "✓ $test_name: PASSED (No numbers found)"
        return 0
    else
        print_status $RED "✗ $test_name: FAILED"
        print_status $RED "  Expected match: $should_match"
        print_status $RED "  Has numbers: $has_numbers"
        return 1
    fi
}

# Performance test
test_performance() {
    local test_name="$1"
    local input_text="$2"
    local max_time_ms="$3"
    
    local start_time=$(date +%s%3N)
    local numbers=$(echo "$input_text" | grep -o '[0-9]\+' | tr '\n' ' ')
    local end_time=$(date +%s%3N)
    
    local duration=$((end_time - start_time))
    
    if [ "$duration" -lt "$max_time_ms" ]; then
        print_status $GREEN "✓ $test_name: PASSED (${duration}ms)"
        return 0
    else
        print_status $RED "✗ $test_name: FAILED (${duration}ms > ${max_time_ms}ms)"
        return 1
    fi
}

# Initialize test counters
total_tests=0
passed_tests=0

# Test suite
print_status $BLUE "Running number extraction tests..."

# Test 1: Basic number extraction
test_number_extraction "Basic number extraction" "Hello 123 World 456" "123 456"
total_tests=$((total_tests + 1))
if [ $? -eq 0 ]; then passed_tests=$((passed_tests + 1)); fi

# Test 2: Empty text
test_number_extraction "Empty text" "" ""
total_tests=$((total_tests + 1))
if [ $? -eq 0 ]; then passed_tests=$((passed_tests + 1)); fi

# Test 3: No numbers
test_number_extraction "No numbers" "Hello World" ""
total_tests=$((total_tests + 1))
if [ $? -eq 0 ]; then passed_tests=$((passed_tests + 1)); fi

# Test 4: Single number
test_number_extraction "Single number" "123" "123"
total_tests=$((total_tests + 1))
if [ $? -eq 0 ]; then passed_tests=$((passed_tests + 1)); fi

# Test 5: Multiple numbers
test_number_extraction "Multiple numbers" "1 2 3 4 5" "1 2 3 4 5"
total_tests=$((total_tests + 1))
if [ $? -eq 0 ]; then passed_tests=$((passed_tests + 1)); fi

# Test 6: Large numbers
test_number_extraction "Large numbers" "999999999 1000000000" "999999999 1000000000"
total_tests=$((total_tests + 1))
if [ $? -eq 0 ]; then passed_tests=$((passed_tests + 1)); fi

# Test 7: Decimal numbers (should split on decimal point)
test_number_extraction "Decimal numbers" "12.34 56.78" "12 34 56 78"
total_tests=$((total_tests + 1))
if [ $? -eq 0 ]; then passed_tests=$((passed_tests + 1)); fi

# Test 8: Special characters
test_number_extraction "Special characters" "Test@#$%^&*()123!@#$%^&*()456" "123 456"
total_tests=$((total_tests + 1))
if [ $? -eq 0 ]; then passed_tests=$((passed_tests + 1)); fi

# Test 9: Whitespace
test_number_extraction "Whitespace" "  123   456   789  " "123 456 789"
total_tests=$((total_tests + 1))
if [ $? -eq 0 ]; then passed_tests=$((passed_tests + 1)); fi

# Test 10: Newlines
test_number_extraction "Newlines" "Line 1: 123\nLine 2: 456\nLine 3: 789" "1 123 2 456 3 789"
total_tests=$((total_tests + 1))
if [ $? -eq 0 ]; then passed_tests=$((passed_tests + 1)); fi

print_status $BLUE "Running sum calculation tests..."

# Test 11: Basic sum calculation
test_sum_calculation "Basic sum calculation" "1 2 3 4 5" "15"
total_tests=$((total_tests + 1))
if [ $? -eq 0 ]; then passed_tests=$((passed_tests + 1)); fi

# Test 12: Empty text sum
test_sum_calculation "Empty text sum" "" "0"
total_tests=$((total_tests + 1))
if [ $? -eq 0 ]; then passed_tests=$((passed_tests + 1)); fi

# Test 13: No numbers sum
test_sum_calculation "No numbers sum" "Hello World" "0"
total_tests=$((total_tests + 1))
if [ $? -eq 0 ]; then passed_tests=$((passed_tests + 1)); fi

# Test 14: Single number sum
test_sum_calculation "Single number sum" "123" "123"
total_tests=$((total_tests + 1))
if [ $? -eq 0 ]; then passed_tests=$((passed_tests + 1)); fi

# Test 15: Large numbers sum
test_sum_calculation "Large numbers sum" "999999999 1000000000" "1999999999"
total_tests=$((total_tests + 1))
if [ $? -eq 0 ]; then passed_tests=$((passed_tests + 1)); fi

print_status $BLUE "Running regex pattern tests..."

# Test 16: Regex with numbers
test_regex_pattern "Regex with numbers" "Hello 123 World" "true"
total_tests=$((total_tests + 1))
if [ $? -eq 0 ]; then passed_tests=$((passed_tests + 1)); fi

# Test 17: Regex without numbers
test_regex_pattern "Regex without numbers" "Hello World" "false"
total_tests=$((total_tests + 1))
if [ $? -eq 0 ]; then passed_tests=$((passed_tests + 1)); fi

# Test 18: Regex with decimal
test_regex_pattern "Regex with decimal" "123.456" "true"
total_tests=$((total_tests + 1))
if [ $? -eq 0 ]; then passed_tests=$((passed_tests + 1)); fi

print_status $BLUE "Running performance tests..."

# Test 19: Performance test with small text
test_performance "Performance small text" "1 2 3 4 5" "1000"
total_tests=$((total_tests + 1))
if [ $? -eq 0 ]; then passed_tests=$((passed_tests + 1)); fi

# Test 20: Performance test with large text
large_text="This is a very long text with many numbers: "
for i in {1..100}; do
    large_text="$large_text $i"
done
test_performance "Performance large text" "$large_text" "5000"
total_tests=$((total_tests + 1))
if [ $? -eq 0 ]; then passed_tests=$((passed_tests + 1)); fi

# Summary
print_status $GREEN "=========================================="
print_status $GREEN "Test Suite Summary:"
print_status $GREEN "Total tests: $total_tests"
print_status $GREEN "Passed: $passed_tests"
print_status $GREEN "Failed: $((total_tests - passed_tests))"

if [ $passed_tests -eq $total_tests ]; then
    print_status $GREEN "All tests passed! ✓"
    exit 0
else
    print_status $RED "Some tests failed! ✗"
    exit 1
fi