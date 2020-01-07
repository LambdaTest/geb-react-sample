package functionaltest.specs

import geb.driver.CachingDriverFactory
import geb.spock.GebSpec
import groovy.json.JsonSlurper
import org.junit.Rule
import org.junit.rules.TestName
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import spock.lang.Stepwise


class BasePageGebSpec extends GebSpec {
    public String username = System.getProperty('username')
    public String accessKey = System.getProperty('key')
    public String deviceName = System.getProperty('deviceName')

    public String platform = System.getProperty('platform')
    public String browserName = System.getProperty('browserName')
    public String testDriver = System.getProperty('testDriver')
    /**
     * Represents the browser to be used as part of the test run.
     */
    private String browser
    /**
     * Represents the operating system to be used as part of the test run.
     */
    private String os
    /**
     * Represents the version of the browser to be used as part of the test run.
     */
    private String version
    /**
     * Represents the device-orientation of mobile device
     */
    private String deviceOrientation
    /**
     * Instance variable which contains the Lambda Job Id.
     */
    private String sessionId

    public String capabilityString

    private static boolean driverCreated

    public LTOnDemandAuthentication authentication = new LTOnDemandAuthentication(username, accessKey)

    @Rule
    public TestName name = new TestName() {
        public String getMethodName() {
            return super.getMethodName()
        }
    }

    public String getSessionId() {
        return sessionId
    }

    public void setupSpec() throws Exception {
        driverCreated = false
    }

    private isSpecStepwise() {
        this.class.getAnnotation(Stepwise) != null
    }

    public void setup() throws Exception {
        println(username)
        println(deviceName)
        if (!driverCreated || !isSpecStepwise()) {
            Map<String, String> capMap

            if (!deviceName.isEmpty()) {
                println("Device Name Provided")
                capabilityString = '{"build": "Geb", "deviceName":"' + deviceName + '"}'
            } else if (deviceName.isEmpty() && !platform.isEmpty() && !browserName.isEmpty()) {
                println("Device Name Not Provided")
                capabilityString = '{"build": "Geb", "platform":"' + platform + '", "browserName":"' + browserName + '"}'
            } else
                println("Provide either Device Name or provide Platform & Browser Name")


            if (capabilityString && testDriver == "lambda") {
                capMap = new JsonSlurper().parseText(capabilityString)
                DesiredCapabilities capabilities = new DesiredCapabilities(capMap)
                String methodName = name.getMethodName()
                String specName = this.class.getSimpleName()
                if (isSpecStepwise()) {
                    methodName = "All tests in " + specName
                }
                capabilities.setCapability("name", String.format("%s.%s", specName, methodName))
                capabilities.setCapability("newCommandTimeout", 180)
                driver = new RemoteWebDriver(
                        new URL("https://" + authentication.getUsername() + ":" + authentication.getAccessKey() +
                                "@hub.lambdatest.com/wd/hub"), capabilities)

                this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString()

            }

            driverCreated = true
        }
    }

    @Override
    public void cleanup() throws Exception {
        if (!isSpecStepwise()) {
            CachingDriverFactory.clearCache()
            driver.quit()
        }
    }
}