fastlane documentation
================
# Installation

Make sure you have the latest version of the Xcode command line tools installed:

```
xcode-select --install
```

Install _fastlane_ using
```
[sudo] gem install fastlane -NV
```
or alternatively using `brew install fastlane`

# Available Actions
## Android
### android test
```
fastlane android test
```
Runs all the tests
### android assemble_build
```
fastlane android assemble_build
```
Assemble Build
### android assemble_test_application
```
fastlane android assemble_test_application
```
Assemble Test Application
### android assemble
```
fastlane android assemble
```
Assemble Build and Test Application
### android beta
```
fastlane android beta
```
Submit a new Beta Build to Crashlytics Beta
### android instrumentation_tests
```
fastlane android instrumentation_tests
```
Run instrumentation tests
### android instrumentation_tests_testlab
```
fastlane android instrumentation_tests_testlab
```
Run instrumentation tests in Firebase Test Lab
### android deploy
```
fastlane android deploy
```
Deploy a new version to the Google Play

----

This README.md is auto-generated and will be re-generated every time [_fastlane_](https://fastlane.tools) is run.
More information about fastlane can be found on [fastlane.tools](https://fastlane.tools).
The documentation of fastlane can be found on [docs.fastlane.tools](https://docs.fastlane.tools).
