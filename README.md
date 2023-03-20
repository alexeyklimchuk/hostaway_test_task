# Test task for `hostaway` from Alexey Klimchuk

## **How To Use**

### **Configuration Files**
You can change values for your case.

1. [log4j.properties](src/test/resources/log4j.properties)<br/>
    > Configuring log4j involves assigning the Level, defining Appender, and specifying Layout objects in a configuration file.
2. [selenide.properties](src/test/resources/selenide.properties)<br/>
    > Configuration settings for Selenide default browser. Settings can be set either via system property or programmatically.
3. [junit-platform.properties](src/test/resources/junit-platform.properties)<br/>
    > If a properties file with this name is present in the root of the classpath, it will be used as a source for configuration parameters. If multiple files are present, only the first one detected in the classpath will be used.
4. [allure.properties](src/test/resources/allure.properties)<br/>
    > Configuring all Allure properties by passing classpath.
5. [categories.json](src/test/resources/categories.json) for Allure Report<br/>
    > Defining categories to group tests.<br/>
    > **Category structure:**<br/>
    > ```json
    > {
    >   "name": "{Category name}",
    >   "matchedStatuses": "{One or more statuses of tests}",
    >   "traceRegex": "{Exception name}",
    >   "messageRegex": "{The content or description of Exeception}"
    > }
    > ```
    > **Define a category by the following:**<br/>
    > - `matchedStatuses` -> The status of test, one in `skipped`, `passed`, `broken`, `failed`, `unknown`<br/>
    > - `traceRegex` -> Find the exception caused status to the test<br/>
    > - `messageRegex` -> Get content or description shortly or fully<br/>
    > - `name` -> Set category name based on above keys<br/>

### **Run Tests With Gradle**
> `./gradlew clean test allureReport`<br/>

Task `allureReport`: Build report from `hostaway_test_task/build/allure-results` folder

#### **Perform On Browsers**
- chrome
- edge
- firefox
- ie
- safari

> If run safari, you must enable the 'Allow Remote Automation' option in Safari's Develop menu to control Safari via WebDriver.

Able to select browser by passing system property `selenide.browser`<br/>
> `./gradlew clean test -Dselenide.browser=edge allureReport`