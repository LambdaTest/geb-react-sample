package functionaltest.specs;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class LTOnDemandAuthentication {


    private String username = "";

    private String accessKey = "";

    private static final String LT_USER_NAME = "LT_USER_NAME";

    private static final String LT_USERNAME = "LT_USERNAME";

    private static final String LT_API_KEY = "LT_API_KEY";

    private static final String LT_ACCESS_KEY = "LT_ACCESS_KEY";


    public LTOnDemandAuthentication() {
        //first try to retrieve information from properties/environment variables
        this.username = getPropertyOrEnvironmentVariable(LT_USER_NAME);
        if (username == null || username.equals("")) {
            //try the LT_USERNAME environment variable
            this.username = getPropertyOrEnvironmentVariable(LT_USERNAME);
        }
        this.accessKey = getPropertyOrEnvironmentVariable(LT_API_KEY);
        if (accessKey == null || accessKey.equals("")) {
            //try the LT_ACCESS_KEY environment variable
            this.accessKey = getPropertyOrEnvironmentVariable(LT_ACCESS_KEY);
        }

        if (username == null || accessKey == null) {
            System.out.print("Null value obtained");
        }


    }

    public LTOnDemandAuthentication(String username, String accessKey) {
        this.username = username;
        this.accessKey = accessKey;
    }

    private void loadCredentialsFromFile(File propertyFile) {
        Properties props = new Properties();
        FileInputStream in = null;
        try {
            if (propertyFile.exists()) {
                in = new FileInputStream(propertyFile);
                props.load(in);
                this.username = props.getProperty("username");
                this.accessKey = props.getProperty("key");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                //ignore error and continue
            }
        }
    }

    private static String getPropertyOrEnvironmentVariable(String property) {
        String value = System.getProperty(property);
        if (value == null || value.equals("")) {
            value = System.getenv(property);
        }
        return value;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    /**
     * Persists this credential to the disk.
     * @param propertyFile File object to save java propery style credentials to.
     * @throws IOException If the file I/O fails.
     */
    public void saveTo(File propertyFile) throws IOException {
        Properties props = new Properties();
        props.put("username", username);
        props.put("key", accessKey);
        FileOutputStream out = new FileOutputStream(propertyFile);
        try {
            props.store(out, "LT OnDemand access credential");
        } finally {
            out.close();
        }
    }
}

