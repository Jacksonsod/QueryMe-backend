# Testing Custom Sandbox Exceptions

## How to Run the Tests

### Option 1: Using Maven (Recommended)
```bash
mvn test -Dtest=SandboxServiceImplTest
```

### Option 2: Run All Tests
```bash
mvn test
```

### Option 3: Run from IDE
- Right-click on `SandboxServiceImplTest.java` in your IDE
- Select "Run 'SandboxServiceImplTest'"

## What the Tests Cover

### 1. SandboxProvisioningException Tests
- ✅ Schema creation failure
- ✅ Database user creation failure  
- ✅ Seed SQL execution failure
- ✅ Proper cleanup on failure (DROP SCHEMA/USER)

### 2. SandboxNotFoundException Tests
- ✅ Teardown of non-existent sandbox
- ✅ Getting connection details for non-existent sandbox

### 3. SandboxExpiredException Tests
- ✅ Sandbox with "DROPPED" status
- ✅ Sandbox with null status

### 4. Success Cases
- ✅ Successful sandbox provisioning
- ✅ Successful connection details retrieval

## Expected Output

When you run the tests, you should see:
```
[INFO] Running com.year2.queryme.sandbox.service.SandboxServiceImplTest
[INFO] Tests run: 9, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: X.XXX s
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 9, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] BUILD SUCCESS
```

## Manual Testing (Optional)

You can also test the exceptions manually by creating a simple controller endpoint:

```java
@RestController
@RequestMapping("/api/test")
public class SandboxTestController {
    
    @Autowired
    private SandboxService sandboxService;
    
    @GetMapping("/provision-fail")
    public String testProvisionFail() {
        try {
            return sandboxService.provisionSandbox(null, null, "invalid sql");
        } catch (SandboxProvisioningException e) {
            return "Caught SandboxProvisioningException: " + e.getMessage();
        }
    }
    
    @GetMapping("/not-found")
    public String testNotFound() {
        try {
            return sandboxService.getSandboxConnectionDetails(
                UUID.randomUUID(), UUID.randomUUID()).toString();
        } catch (SandboxNotFoundException e) {
            return "Caught SandboxNotFoundException: " + e.getMessage();
        }
    }
}
```

## Integration with Group G

Group G can now catch these specific exceptions:

```java
try {
    String schema = sandboxService.provisionSandbox(examId, studentId, seedSql);
    // Use sandbox...
} catch (SandboxProvisioningException e) {
    // Handle provisioning failure
    log.error("Failed to create sandbox for student {}", studentId, e);
    return ResponseEntity.status(500).body("Sandbox creation failed");
} catch (SandboxNotFoundException e) {
    // Handle missing sandbox
    log.warn("Sandbox not found for student {}", studentId);
    return ResponseEntity.status(404).body("Sandbox not found");
} catch (SandboxExpiredException e) {
    // Handle expired/inactive sandbox
    log.warn("Sandbox expired for student {}", studentId);
    return ResponseEntity.status(410).body("Sandbox has expired");
}
```
