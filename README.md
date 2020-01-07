### Prerequisites

* You must have gradle, groovy and selenium readily installed in your system so that the sample code works perfectly without any conflicts or failures.


### Environment Setup

1. Open up TERMINAL from your IDE or in the root location where this sample code has been stored. 
    
2. In order to run the sample geb code for lambdatest, you need to pass your username, accesskey and capabilities.
     
3. Now, provide the syntax in your terminal in the following manner:
    ```sh 
      ./gradlew geb-lambdatest -Pusername="Your username" -Pkey="Your access key" -PdeviceName="iPhone XS"
                                                    or
      ./gradlew geb-lambdatest -Pusername="Your username" -Pkey="Your access key" -Pplatform="Platform capability" -PbrowserName="Browser capability"                                        
    ```

4. Press enter the run the sample geb-test on lambdatest selenium grid.

### Passing capabilities

    * Go to https://www.lambdatest.com/capabilities-generator/ and select your desired capabilities.