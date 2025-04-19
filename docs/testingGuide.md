# Testing Guide
This page supplements the [developer guide](developerGuide.md) and provides more information about the methodology for 
testing.


## How to run tests
There are two main ways to run tests.
1. Right-Click the test folder and click `Run Tests in ...`
2. Run `gradle test` at the root folder

__NOTE:__ If you encounter an error such that the tests do not start, you can try removing the build by running 
`gradle clean`.

## Types of Testing Done
### Unit Testing
Unit Testing was done for the lowest level modules that are within their own submodules. These tests ensure that the 
   internal functionality of the application works as expected.
   * Examples: SessionDurationTest, StorageServiceTest

### Integration Testing
Integration Testing was done at a higher level where it incorporates lower level modules and test their interactions 
   with each other.
   * Examples: SessionTest, SessionServiceTest

### UI Testing <br>
UI Testing was done briefly to ensure that the correct statistics was being displayed by the UI.
   * Examples: StatsControllerTest

You have reached the end of the testing guide!

To head back, click [here](./developerGuide.md)