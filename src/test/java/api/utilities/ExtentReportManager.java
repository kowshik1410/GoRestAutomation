/*package api.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener {
    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest test;

    String repName;

    public void onStart(ITestContext testContext) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); // Time stamp
        repName = "Test-Report-" + timeStamp + ".html";

        sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName); // Specify location of the report

        sparkReporter.config().setDocumentTitle("RestAssuredAutomationProject"); // Title of report
        sparkReporter.config().setReportName("Go Rest API"); // Name of the report
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Application", "Go Rest Users API");
        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("User", "Kowshik");
    }

    public void onTestSuccess(ITestResult result) {
        test = extent.createTest(result.getName()); // Create new test node in the report
        test.assignCategory(result.getMethod().getGroups());
        test.createNode(result.getName());

        // Log the success status
        test.log(Status.PASS, "Test Passed");

        // Log assertions if available
        Object[] parameters = result.getParameters();
        for (Object param : parameters) {
            test.log(Status.INFO, "Assertion Detail: " + param.toString());
        }
    }

    public void onTestFailure(ITestResult result) {
        test = extent.createTest(result.getName()); 
        test.createNode(result.getName());
        test.assignCategory(result.getMethod().getGroups());

        // Log the failure status
        test.log(Status.FAIL, "Test Failed");
        test.log(Status.FAIL, result.getThrowable().getMessage());

        // Log failed assertion details
        Object[] parameters = result.getParameters();
        for (Object param : parameters) {
            test.log(Status.INFO, "Failed Assertion Detail: " + param.toString());
        }
    }

    public void onTestSkipped(ITestResult result) {
        test = extent.createTest(result.getName()); 
        test.createNode(result.getName());
        test.assignCategory(result.getMethod().getGroups());

        // Log the skipped status
        test.log(Status.SKIP, "Test Skipped");
        test.log(Status.SKIP, result.getThrowable().getMessage());

        // Log skipped assertion details if available
        Object[] parameters = result.getParameters();
        for (Object param : parameters) {
            test.log(Status.INFO, "Skipped Assertion Detail: " + param.toString());
        }
    }

    public void onFinish(ITestContext testContext) {
        extent.flush();
    }
}
*/


package api.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener {
    public static ExtentSparkReporter sparkReporter;
    public static ExtentReports extent;
    public static ExtentTest test;

    String repName;

    public void onStart(ITestContext testContext) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); // Time stamp
        repName = "Test-Report-" + timeStamp + ".html";

        sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName); // Specify location of the report
        sparkReporter.config().setDocumentTitle("RestAssuredAutomationProject"); // Title of report
        sparkReporter.config().setReportName("Go Rest API"); // Name of the report
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Application", "Go Rest Users API");
        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("User", "Kowshik");
    }

    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getName()); // Create test node
    }

    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS, "Test Passed: " + result.getName());
    }

    public void onTestFailure(ITestResult result) {
        test.log(Status.FAIL, "Test Failed: " + result.getName());
        test.log(Status.FAIL, result.getThrowable().getMessage());     
    }

    public void onTestSkipped(ITestResult result) {
        test.log(Status.SKIP, "Test Skipped: " + result.getName());
        test.log(Status.SKIP, result.getThrowable().getMessage());
    }

    public void onFinish(ITestContext testContext) {
        extent.flush();
    }
}
