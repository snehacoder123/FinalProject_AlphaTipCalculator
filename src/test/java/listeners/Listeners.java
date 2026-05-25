package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class Listeners implements ITestListener {

    ExtentReports extent;

    ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onStart(ITestContext context) {

        ExtentSparkReporter spark = new ExtentSparkReporter("reports/ExtentReport.html");

        extent = new ExtentReports();
        extent.attachReporter(spark);

        System.out.println("Extent Report initialized");
    }

    @Override
    public void onTestStart(ITestResult result) {

        ExtentTest extentTest = extent.createTest(result.getName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        if (test.get() != null) {
            test.get().pass("Test Passed");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (test.get() == null) {
            ExtentTest extentTest = extent.createTest(result.getName());
            test.set(extentTest);
        }

        test.get().fail("Test Failed");

        if (result.getThrowable() != null) {
            test.get().fail(result.getThrowable());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        if (test.get() == null) {
            ExtentTest extentTest = extent.createTest(result.getName());
            test.set(extentTest);
        }

        test.get().skip("Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {

        extent.flush();
        System.out.println("Extent Report generated");
    }
}
