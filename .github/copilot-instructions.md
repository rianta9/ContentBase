# GitHub Copilot AI Test Generation Workflow
**Declarative Prompt for Automated High-Quality Test Case Generation**
**Version:** 1.0
**Date:** December 6, 2025
**Target:** Integration Tests, Unit Tests, E2E Tests

---

## 📋 Table of Contents
1. [System Prompt](#system-prompt)
2. [Test Generation Framework](#test-generation-framework)
3. [Quality Standards](#quality-standards)
4. [Template Library](#template-library)
5. [Execution Workflow](#execution-workflow)
6. [Validation Checklist](#validation-checklist)

---

## System Prompt

### Primary Instruction
```
You are an expert test engineer with 10+ years of experience in enterprise software testing.
Your role is to generate comprehensive, production-ready test cases that follow modern
testing standards, best practices, and industry conventions.

When generating test cases:
1. ALWAYS follow the provided templates exactly
2. ALWAYS include complete documentation for each test case
3. ALWAYS validate inputs/outputs are clearly specified
4. ALWAYS generate multiple test scenarios (happy path, edge cases, error cases)
5. ALWAYS ensure tests are isolated, deterministic, and maintainable
6. ALWAYS generate with 100% code coverage in mind
7. ALWAYS use professional naming conventions (AAA pattern, descriptive names)
8. ALWAYS include assertions that validate business logic, not just syntax
```

---

## Test Generation Framework

### Phase 1: Test Planning & Analysis
**Input:** Feature/API Specification
**Output:** Test Plan Document

#### Prompt: Analysis Phase
```
Analyze the following [FEATURE/API] specification and generate a comprehensive test plan:

INPUTS REQUIRED:
1. Feature name: [PROVIDE]
2. API endpoint/method: [PROVIDE]
3. Business requirements: [PROVIDE]
4. Data model: [PROVIDE]
5. Error scenarios: [PROVIDE]
6. Security requirements: [PROVIDE]

DELIVERABLES:
Generate a TEST_PLAN_{feature_name}.md file containing:
- Feature Overview (2-3 sentences)
- Business Requirements (numbered list)
- Data Model Diagram (ASCII or description)
- Test Scenario Categories:
  * Happy Path (main success scenario)
  * Edge Cases (boundary conditions)
  * Error Cases (exception handling)
  * Security Tests (data protection, authorization)
  * Performance Tests (load, stress)
- Test Coverage Matrix (endpoints × scenarios)
- Risk Assessment (priority × severity)

Format as structured markdown with clear sections.
```

### Phase 2: Test Case Generation
**Input:** Test Plan
**Output:** Individual Test Case Files with Documentation

#### Prompt: Generation Phase
```
Generate production-ready test cases based on the test plan.

FOR EACH TEST SCENARIO:

1. Create test case file: TEST_{feature_name}_{scenario_code}.java

2. Include comprehensive documentation block:
   /**
    * Test Case ID: TC_{feature}_{number}
    * Title: [Clear, descriptive title]
    * 
    * BUSINESS REQUIREMENT: [Which requirement does this test verify?]
    * 
    * INPUTS:
    *   - Input Parameter 1: [description, valid range, example value]
    *   - Input Parameter 2: [description, valid range, example value]
    * 
    * EXPECTED OUTPUT:
    *   - HTTP Status: [specific status code]
    *   - Response Fields: [list with types]
    *   - Business Logic Result: [what should happen]
    * 
    * EDGE CASES: [none/list any edge cases handled]
    * 
    * PRECONDITIONS:
    *   - Database state: [required state]
    *   - User role/permissions: [required role]
    *   - External dependencies: [any mocks needed]
    * 
    * RISK LEVEL: [LOW/MEDIUM/HIGH]
    * PRIORITY: [P0/P1/P2]
    */

3. Implement using AAA Pattern:
   @Test
   @DisplayName("[Function] [Scenario] [Expected Result]")
   void test{CamelCaseDescriptiveNameFollowingConvention}() throws Exception {
       // ARRANGE - Setup test data with clear variable names
       // ACT - Execute the action with documented parameters
       // ASSERT - Verify with multiple specific assertions
   }

4. Generate minimum 3 test cases per feature:
   - Happy Path: Main success scenario
   - Error Case: Most critical failure scenario
   - Edge Case: Boundary condition or special case

5. Include test fixture setup with clear initialization
```

### Phase 3: Documentation Generation
**Input:** Test Cases
**Output:** Test Case Documentation

#### Prompt: Documentation Phase
```
For each test case, generate structured documentation.

CREATE: TEST_CASE_DOCUMENTATION_{feature_name}.md

For EACH test case include:

## Test Case: {TC_ID} - {Title}
**Status:** [PASS/FAIL/NOT_RUN]
**Priority:** [P0/P1/P2]
**Risk Level:** [LOW/MEDIUM/HIGH]

### Business Context
- **Feature:** [Feature name]
- **Requirement:** [Which requirement verified]
- **Use Case:** [Real-world scenario this tests]

### Test Inputs
| Parameter | Type | Value | Description |
|-----------|------|-------|-------------|
| param1 | String | "test-value" | Description of input |
| param2 | Integer | 42 | Description of input |

### Expected Output
| Field | Type | Value | Description |
|-------|------|-------|-------------|
| statusCode | Integer | 200 | HTTP status |
| field1 | String | "expected" | Response field |

### Test Steps
1. [Step 1: Setup/Arrange]
2. [Step 2: Execute/Act]
3. [Step 3: Verify/Assert]

### Assertions Validated
- [ ] Status code matches expected value
- [ ] Response contains required fields
- [ ] Response data types are correct
- [ ] Business logic executed correctly
- [ ] No sensitive data exposed
- [ ] Error handling works as expected

### Edge Cases Handled
- [List any edge cases this test covers]

### Related Tests
- [List related test cases]

### Potential Issues
- [Document any known limitations or issues]

### Manual Verification Steps
1. [If test needs manual verification, list steps]
```

---

## Quality Standards

### Test Case Quality Checklist

```
NAMING STANDARDS ✓
[ ] Test name follows pattern: test{CamelCaseDescriptiveNameFollowingConvention}
[ ] Test class name follows pattern: {FeatureName}IntegrationTest or {FeatureName}Test
[ ] Test name is self-documenting (someone can understand purpose from name)
[ ] No abbreviations except for common terms (HTTP, API, etc.)
[ ] Test name describes: WHAT is being tested, SCENARIO, EXPECTED RESULT

STRUCTURE STANDARDS ✓
[ ] Follows AAA Pattern (Arrange, Act, Assert)
[ ] Arrange section: Creates all test data and mocks
[ ] Act section: Single logical action being tested
[ ] Assert section: Multiple specific assertions
[ ] Each section clearly commented
[ ] Maximum 50 lines of code per test (excluding setup)

ASSERTION STANDARDS ✓
[ ] At least 2 assertions per test (ideally 3-5)
[ ] Assertions test business logic, not just syntax
[ ] Assertions have clear error messages
[ ] No assertion testing test framework (e.g., no assertNotNull on obvious objects)
[ ] All assertions are independent (later assertions don't depend on earlier ones)

DOCUMENTATION STANDARDS ✓
[ ] JavaDoc block includes: TC_ID, Title, Business Requirement
[ ] Inputs documented with types, ranges, examples
[ ] Expected outputs documented with types, examples
[ ] Preconditions clearly stated
[ ] Risk and Priority levels specified
[ ] Related tests/dependencies documented

DATA STANDARDS ✓
[ ] Test data is realistic but simplified
[ ] Test data doesn't contain sensitive information
[ ] Test data uses fixtures or builders for consistency
[ ] Test data cleanup is implemented (tearDown or @After)
[ ] Test data generation is isolated per test (no shared mutable state)

ISOLATION STANDARDS ✓
[ ] Test is completely independent (can run in any order)
[ ] Test doesn't depend on other tests
[ ] Test doesn't modify shared state
[ ] Test uses @BeforeEach/@Before for setup
[ ] Test uses @AfterEach/@After for cleanup
[ ] Test doesn't rely on external systems (uses mocks)

DETERMINISM STANDARDS ✓
[ ] Test produces same result every run
[ ] No time-dependent logic
[ ] No random data (unless testing randomization)
[ ] No external API calls (must be mocked)
[ ] No date/time dependencies (mock CurrentDateTime)

ERROR HANDLING STANDARDS ✓
[ ] Error cases tested separately from happy path
[ ] Exception types are specific (not generic Exception)
[ ] Error messages are validated
[ ] Error handling doesn't mask failures
[ ] Assertion errors have descriptive messages

PERFORMANCE STANDARDS ✓
[ ] Test runs in < 100ms (unit tests)
[ ] Test runs in < 1s (integration tests)
[ ] Test doesn't use Thread.sleep (use WaitFor patterns)
[ ] Test doesn't create unnecessary objects
[ ] Test data is minimal but sufficient

SECURITY STANDARDS ✓
[ ] Tests verify authorization (who can access)
[ ] Tests verify authentication (who are you)
[ ] Tests verify sensitive data is not exposed
[ ] Tests verify input validation
[ ] Tests verify SQL injection protection
[ ] Tests verify XSS protection (if applicable)
```

---

## Template Library

### Template 1: Unit Test
```java
package com.ryanlab.contentbase.unit.{module};

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

/**
 * Test Case ID: TC_UNIT_{number}
 * Title: [Descriptive test title]
 * 
 * BUSINESS REQUIREMENT: [Which requirement this tests]
 * 
 * INPUTS:
 *   - input1: [description]
 *   - input2: [description]
 * 
 * EXPECTED OUTPUT:
 *   - output1: [description]
 *   - output2: [description]
 * 
 * PRIORITY: P{0/1/2}
 * RISK LEVEL: {LOW/MEDIUM/HIGH}
 */
class {FeatureName}Test {
    
    private {ClassUnderTest} objectUnderTest;
    private {Dependency} mockDependency;
    
    @BeforeEach
    void setUp() {
        // ARRANGE: Initialize test objects
        mockDependency = mock({Dependency}.class);
        objectUnderTest = new {ClassUnderTest}(mockDependency);
    }
    
    @AfterEach
    void tearDown() {
        // Cleanup if needed
    }
    
    @Test
    @DisplayName("[Method] [Scenario] [Expected Result]")
    void testHappyPathScenario() {
        // ARRANGE - Setup input data
        String input = "test-value";
        when(mockDependency.someMethod()).thenReturn("mocked-result");
        
        // ACT - Execute the method under test
        String result = objectUnderTest.methodUnderTest(input);
        
        // ASSERT - Verify the output
        assertNotNull(result, "Result should not be null");
        assertEquals("expected-value", result, "Result should match expected value");
        verify(mockDependency).someMethod(); // Verify interaction
    }
    
    @Test
    @DisplayName("[Method] [Error Scenario] [Throws Exception]")
    void testErrorHandling() {
        // ARRANGE
        when(mockDependency.someMethod()).thenThrow(new RuntimeException("Test error"));
        
        // ACT & ASSERT
        assertThrows(RuntimeException.class, 
            () -> objectUnderTest.methodUnderTest("input"),
            "Should throw RuntimeException when dependency fails");
    }
    
    @Test
    @DisplayName("[Method] [Edge Case] [Handles Boundary Condition]")
    void testEdgeCase() {
        // ARRANGE
        String input = ""; // Empty string edge case
        
        // ACT
        String result = objectUnderTest.methodUnderTest(input);
        
        // ASSERT
        assertNotNull(result, "Should handle empty input gracefully");
        assertTrue(result.isEmpty(), "Empty input should produce empty output");
    }
}
```

### Template 2: Integration Test
```java
package com.ryanlab.contentbase.integration.controller.{module};

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Test Case ID: TC_INT_{number}
 * Title: [Descriptive test title]
 * 
 * BUSINESS REQUIREMENT: [Which requirement this tests]
 * 
 * INPUTS:
 *   - Endpoint: [HTTP method] /api/path/{id}
 *   - Request Parameters: [list parameters]
 *   - Request Body: [structure]
 * 
 * EXPECTED OUTPUT:
 *   - HTTP Status: [status code]
 *   - Response Structure: [fields]
 *   - Side Effects: [what changes in database]
 * 
 * PRECONDITIONS:
 *   - Database State: [required data]
 *   - User Role: [required role]
 * 
 * PRIORITY: P{0/1/2}
 * RISK LEVEL: {LOW/MEDIUM/HIGH}
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class {FeatureName}IntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private {Repository} repository;
    
    @BeforeEach
    void setUp() {
        // ARRANGE: Create test data in database
        {Entity} testEntity = {EntityTestFixture}.createDefault();
        repository.save(testEntity);
    }
    
    @Test
    @DisplayName("[GET /api/endpoint] [Valid Request] [Returns 200 with Data]")
    void testGetEndpointHappyPath() throws Exception {
        // ACT & ASSERT
        mockMvc.perform(get("/api/endpoint/{id}", 1)
                .contentType("application/json"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").exists())
            .andExpect(jsonPath("$.status").value("ACTIVE"));
    }
    
    @Test
    @DisplayName("[GET /api/endpoint/{id}] [Not Found] [Returns 404]")
    void testGetEndpointNotFound() throws Exception {
        // ACT & ASSERT
        mockMvc.perform(get("/api/endpoint/{id}", 9999)
                .contentType("application/json"))
            .andExpect(status().isNotFound());
    }
    
    @Test
    @DisplayName("[POST /api/endpoint] [Valid Request] [Creates Resource]")
    void testCreateEndpointSuccess() throws Exception {
        // ARRANGE
        String requestBody = """
            {
                "name": "Test Item",
                "description": "Test Description"
            }
            """;
        
        // ACT & ASSERT
        mockMvc.perform(post("/api/endpoint")
                .contentType("application/json")
                .content(requestBody))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.name").value("Test Item"));
    }
    
    @Test
    @DisplayName("[POST /api/endpoint] [Invalid Input] [Returns 400]")
    void testCreateEndpointValidationError() throws Exception {
        // ARRANGE
        String requestBody = """
            {
                "name": ""
            }
            """;
        
        // ACT & ASSERT
        mockMvc.perform(post("/api/endpoint")
                .contentType("application/json")
                .content(requestBody))
            .andExpect(status().isBadRequest());
    }
}
```

### Template 3: Test Case Documentation
```markdown
# Test Case Documentation: {Feature Name}

## Test Case: TC_{MODULE}_{NUMBER}
**Title:** {Clear descriptive title}
**Status:** [PASS/FAIL/BLOCKED/NOT_RUN]
**Priority:** P0 | P1 | P2
**Risk Level:** LOW | MEDIUM | HIGH
**Automation Status:** AUTOMATED | MANUAL | N/A
**Last Updated:** [Date]

### 1. Business Context
- **Module:** {Module Name}
- **Feature:** {Feature Name}
- **User Story:** {Story ID if applicable}
- **Business Requirement:** {Describe which business requirement this test validates}
- **Use Case:** {Describe the real-world scenario}

### 2. Test Scope
- **Component Under Test:** {Class/Method/Endpoint}
- **Related Tests:** {List related test case IDs}
- **Dependencies:** {External systems, databases, services}
- **Excluded:** {What this test does NOT cover}

### 3. Test Inputs (ARRANGE Phase)
| Input Parameter | Data Type | Value/Range | Description | Example |
|-----------------|-----------|-------------|-------------|---------|
| parameter1 | String | [a-zA-Z0-9]{3,50} | User input for name | "John Doe" |
| parameter2 | Integer | 0-100 | Percentage value | 75 |
| parameter3 | Date | YYYY-MM-DD | Effective date | 2025-12-06 |

### 4. Preconditions
- [ ] Database contains test data
- [ ] User is authenticated with role: {ROLE}
- [ ] System is in state: {STATE}
- [ ] External service {SERVICE} is mocked/available
- [ ] Configuration parameter {PARAM} = {VALUE}

### 5. Test Steps (ACT Phase)
1. **Step 1:** {Describe the action}
   - Input: {specific input values}
   - Expected intermediate result: {what should happen}

2. **Step 2:** {Describe next action}
   - Input: {specific input values}
   - Expected intermediate result: {what should happen}

3. **Step 3:** {Describe final action}
   - Input: {specific input values}
   - Expected intermediate result: {what should happen}

### 6. Expected Results (ASSERT Phase)
| Assertion | Expected Value | Actual Value | Status | Notes |
|-----------|----------------|--------------|--------|-------|
| HTTP Status Code | 200 | | PASS | Success response |
| Response Field: id | exists | | PASS | Auto-generated UUID |
| Response Field: name | "value" | | PASS | Exact match |
| Response Field: timestamp | ISO-8601 format | | PASS | Current timestamp |
| Database State | 1 new record | | PASS | Record persisted |
| Audit Log | 1 entry created | | PASS | Tracked creation |

### 7. Edge Cases Tested
- [ ] Null/empty input values: {describe handling}
- [ ] Boundary values: {describe handling}
- [ ] Special characters: {describe handling}
- [ ] Maximum data size: {describe handling}
- [ ] Concurrent requests: {describe handling}
- [ ] Invalid data types: {describe handling}

### 8. Error Handling Scenarios
| Error Scenario | Input | Expected HTTP Status | Expected Message |
|---|---|---|---|
| Missing required field | No "name" field | 400 | "Field 'name' is required" |
| Invalid data type | name = 123 | 400 | "Field 'name' must be string" |
| Duplicate record | Existing name | 409 | "Record already exists" |
| Not found | Invalid ID | 404 | "Resource not found" |
| Unauthorized | Invalid token | 401 | "Authentication required" |

### 9. Security Considerations
- [ ] Tests verify authorization (only authorized users can access)
- [ ] Tests verify authentication (user identity verified)
- [ ] Tests verify sensitive data is not exposed in logs
- [ ] Tests verify input is sanitized (no SQL injection)
- [ ] Tests verify output is escaped (no XSS)
- [ ] Tests verify CORS policy is enforced

### 10. Performance Criteria
- **Expected Response Time:** < 100ms
- **Database Queries:** Expected 2-3 queries
- **Resource Usage:** < 50MB memory
- **Concurrency:** Can handle 10 concurrent requests

### 11. Test Data Requirements
```java
// Test fixture creation
{Entity} testData = {EntityTestFixture}
    .builder()
    .id(UUID.randomUUID())
    .name("Test Item")
    .status(Status.ACTIVE)
    .createdAt(LocalDateTime.now())
    .build();
```

### 12. Assertions Detailed
```java
// Each assertion explained
assertEquals(200, statusCode, "Should return success status code");
assertNotNull(responseBody.getId(), "Response should contain generated ID");
assertTrue(responseBody.getId().matches(UUID_PATTERN), "ID should be valid UUID format");
assertThat(responseBody.getCreatedAt(), is(notNullValue()));
verify(auditLog).log(any(AuditEvent.class)); // Verify side effects
```

### 13. Known Issues & Limitations
- [ ] {Issue description}: Impact and workaround
- [ ] {Limitation description}: Why it exists and mitigation

### 14. Maintenance Notes
- **Last Modified:** [Date] by [Person]
- **Reason for Last Modification:** {Description}
- **Test Data Maintenance:** {How test data is maintained}
- **Deprecation Notice:** {Any notices about deprecation}

### 15. Attachment Links
- [Test Case Source Code](/path/to/test)
- [Related Documentation](/path/to/docs)
- [Bug Reports](link if applicable)
- [Performance Baseline](/path/to/baseline)
```

---

## Execution Workflow

### Step 1: Feature Analysis
```
INPUT: Feature specification or API documentation
COMMAND TO COPILOT:
"Analyze this [FEATURE/API] and create a comprehensive test plan.
Generate TEST_PLAN_{feature_name}.md with:
- Feature overview
- Business requirements (numbered)
- Data model description
- Test scenario categories (happy path, edge cases, error cases, security, performance)
- Test coverage matrix
- Risk assessment"
```

### Step 2: Test Case Generation
```
INPUT: Test plan document
COMMAND TO COPILOT:
"Generate production-ready test cases based on TEST_PLAN_{feature_name}.md

Generate FOUR test cases per feature:
1. Happy path success scenario
2. Error/exception handling
3. Edge case/boundary condition
4. Security/authorization test

For EACH test case:
- Create separate Java test class or method
- Follow AAA pattern (Arrange, Act, Assert)
- Include comprehensive JavaDoc block with TC_ID, business requirement, inputs, outputs
- Generate 3-5 specific assertions per test
- Include test data fixture creation
- Use @DisplayName with clear test description

Templates to follow:
[INSERT APPROPRIATE TEMPLATE FROM TEMPLATE LIBRARY]"
```

### Step 3: Documentation Generation
```
INPUT: Generated test cases
COMMAND TO COPILOT:
"Generate comprehensive documentation for each test case.

Create TEST_CASE_DOCUMENTATION_{feature_name}.md

For EACH test case generated, include:
- Test metadata (ID, title, priority, risk level)
- Business context and use case
- Detailed inputs table with types, ranges, examples
- Preconditions checklist
- Step-by-step test procedures
- Expected results table
- Edge cases tested
- Error scenarios and handling
- Security considerations
- Performance criteria
- Test data fixtures

Follow documentation template structure exactly."
```

### Step 4: Quality Validation
```
COMMAND TO COPILOT:
"Validate the generated test suite against quality standards.

For the test cases in [TEST_FILE]:
- Check naming conventions compliance
- Verify AAA pattern implementation
- Validate assertion quality (2-5 assertions, business logic focused)
- Verify documentation completeness
- Check data isolation and independence
- Verify determinism (no time dependencies, mocks used)
- Check error handling coverage
- Verify security tests included

Generate VALIDATION_REPORT_{feature_name}.md with:
- Compliance score per standard
- Issues found
- Recommendations
- Corrective actions needed"
```

---

## Validation Checklist

### Pre-Generation Checklist
- [ ] Feature specification is complete and clear
- [ ] Data models are documented
- [ ] API contracts are defined (request/response formats)
- [ ] Business requirements are listed
- [ ] Error scenarios are identified
- [ ] Security requirements are documented
- [ ] Performance requirements are defined

### Post-Generation Checklist
- [ ] All tests follow naming conventions (test{CamelCaseDescriptiveName})
- [ ] All tests implement AAA pattern (Arrange, Act, Assert)
- [ ] All tests have JavaDoc with TC_ID, business requirement, inputs, outputs
- [ ] All tests have 3-5 specific assertions
- [ ] All tests are isolated (can run independently)
- [ ] All tests are deterministic (consistent results)
- [ ] Happy path test exists and passes
- [ ] Error case test exists and passes
- [ ] Edge case test exists and passes
- [ ] Security test exists (authorization/authentication)
- [ ] Test coverage is 80%+ for feature code
- [ ] Test documentation is complete
- [ ] Performance benchmarks are met
- [ ] No test relies on external systems (all mocked)
- [ ] Test data is realistic but simplified
- [ ] Sensitive data not hardcoded in tests

### Quality Gate Criteria
- **Naming:** 100% compliance with conventions ✓
- **Structure:** 100% AAA pattern compliance ✓
- **Assertions:** Min 3 per test, business logic focused ✓
- **Documentation:** Complete per template ✓
- **Coverage:** Min 80% of feature code ✓
- **Isolation:** All tests independent ✓
- **Determinism:** No flaky tests ✓
- **Performance:** Tests run < 1s (integration) ✓

---

## Usage Examples

### Example 1: Generate Tests for GET Endpoint
```
COPILOT COMMAND:
"I have a GET endpoint: GET /api/v1/articles/{slug}

Feature details:
- Returns article by slug
- Only published articles accessible
- Returns 404 if not found or draft/hidden
- Response includes: id, title, content, datePublished

Generate comprehensive test suite with:
1. Happy path: GET published article
2. Error case: GET non-existent article (404)
3. Edge case: GET draft article (should return 404)
4. Security: Verify hidden articles not returned

Create test classes following IntegrationTest template.
Include complete JavaDoc with TC_ID, inputs, expected outputs.
Generate 4-5 specific assertions per test.
Create documentation with test metadata, steps, expected results."
```

### Example 2: Generate Tests for POST Endpoint with Validation
```
COPILOT COMMAND:
"I have a POST endpoint: POST /api/v1/articles

Feature details:
- Creates new article
- Validates: title (required, 5-200 chars), content (required, 50+ chars)
- Sets status to DRAFT initially
- Returns 201 with created article including generated ID and timestamp
- Requires ADMIN role
- Returns 400 for validation errors, 403 for unauthorized

Generate comprehensive test suite covering:
1. Happy path: Create valid article
2. Validation error: Missing required field
3. Validation error: Title too short
4. Authorization: Non-admin user rejected
5. Edge case: Special characters in title

For each test, follow AAA pattern and include complete documentation
showing inputs, expected outputs, and all assertions."
```

### Example 3: Generate Tests for Complex Business Logic
```
COPILOT COMMAND:
"I have a method: calculateArticleEngagementScore()

Business Logic:
- Formula: (likes × 2) + (comments × 1.5) + (shares × 3)
- Minimum score: 0
- Maximum score: 1000 (capped)
- If article status is DRAFT: score = 0 (always)

Generate comprehensive unit test suite:
1. Happy path: Calculate score for published article with engagement
2. Edge case: Draft article always returns 0
3. Edge case: Score capped at 1000 maximum
4. Edge case: No engagement returns 0
5. Boundary test: Score calculation with decimal precision

For each test, provide clear inputs and expected outputs.
Use mocks for dependencies.
Include assertions validating formula calculations.
Create documentation explaining test data and expected results."
```

---

## Integration with CI/CD

### GitHub Actions Workflow Example
```yaml
name: Generate and Validate Tests

on:
  pull_request:
    paths:
      - 'src/main/**'
      - '.github/workflows/test-generation.yml'

jobs:
  generate-tests:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      
      - name: Set up Java
        uses: actions/setup-java@v3
        with:
          java-version: '17'
      
      - name: Generate Test Cases with Copilot
        run: |
          # Run Copilot-guided test generation
          mvn clean test -DgenerateCopilotTests=true
      
      - name: Validate Test Quality
        run: |
          # Run quality validation
          mvn verify -DvalidateTestQuality=true
      
      - name: Generate Documentation
        run: |
          # Generate test documentation
          mvn site -DgenerateTestDocs=true
      
      - name: Comment with Results
        uses: actions/github-script@v6
        with:
          script: |
            # Post test generation results to PR
```

---

## Continuous Improvement

### Feedback Loop
1. **Generate** → Test cases created
2. **Execute** → Run tests and collect results
3. **Analyze** → Review failures and patterns
4. **Document** → Update test documentation
5. **Improve** → Enhance test cases based on learnings
6. **Repeat** → Iterate on test quality

### Metrics to Track
- Test pass rate
- Code coverage percentage
- Test execution time
- Defect detection rate (bugs found by tests)
- Test maintenance effort
- Test flakiness rate

---

## Quick Reference

### Command Templates

**Analysis:** 
```
Analyze [FEATURE] and generate TEST_PLAN_{name}.md with test scenarios
```

**Generation:**
```
Generate production-ready test cases from TEST_PLAN_{name}.md using [TEMPLATE]
```

**Documentation:**
```
Create TEST_CASE_DOCUMENTATION_{name}.md with complete metadata for all tests
```

**Validation:**
```
Validate tests in [FILE] against quality checklist and generate VALIDATION_REPORT_{name}.md
```

---

**End of Declarative Prompt**

**Next Steps:**
1. Copy this prompt into GitHub Copilot Chat
2. Provide feature/API specification
3. Follow the workflow phases (Analysis → Generation → Documentation → Validation)
4. Review generated tests against quality checklist
5. Iterate based on validation feedback
