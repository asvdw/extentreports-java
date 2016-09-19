package dw;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.reporter.ExtentHtmlReporter;
import com.relevantcodes.extentreports.reporter.ExtentXReporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by DWI on 15-09-16.
 */
public class ExtentxTest2 {
    public static final String TEST_FILEPATH = "src/test/resources/test.png";
    private ExtentReports reporter;
    String reportDir = "./report/test/";

    @BeforeClass
    public void initListener(){
        reporter = new ExtentReports();

        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportDir+"test.html");
        //htmlReporter.config().setAutoCreateRelativePathMedia(true);
        ExtentXReporter extentxReporter = new ExtentXReporter("localhost", 27017);
        extentxReporter.config().setServerUrl("http://localhost:1337");
        extentxReporter.config().setProjectName("Test project");
        extentxReporter.config().setReportName("Test report");

        //reporter.attachReporter(htmlReporter); // WORKS
        reporter.attachReporter(extentxReporter); // WORKS
        //reporter.attachReporter(extentxReporter, htmlReporter); //WORKS
        //reporter.attachReporter(htmlReporter, extentxReporter); //CRASH with setAutoCreateRelativePathMedia(true) but WORKS if set to false

    }



    @Test (description = "Media and node test")
    public void loadGoogle(){

        String filepath = reportDir+"/google.png";
        takeScreenshot(filepath);

        //following line to skip dependency to FileUtils.copyFile - but it doesn't fix issues
        filepath = TEST_FILEPATH;

        try {
            reporter.createTest("node test", "test description").createNode("this is a child", "node description").addScreenCaptureFromPath(filepath).info("Screenshot in child");
            reporter.createTest("media test", "test description").addScreenCaptureFromPath(filepath).info( "Screenshot post");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @AfterTest
    public void tear(){
        reporter.flush();
    }

    private void takeScreenshot(String filepath){
        File screenShotFile= new File(TEST_FILEPATH);
        try {
            File dest = new File(filepath);
            if (dest.exists()) dest.delete();
            if (!dest.getParentFile().exists()) dest.getParentFile().mkdirs();

            Files.copy(screenShotFile.toPath(), new File(filepath).toPath());
        } catch (IOException e) {e.printStackTrace();
        }


    }


}
