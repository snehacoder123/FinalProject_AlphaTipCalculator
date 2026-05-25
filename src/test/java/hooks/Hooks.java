package hooks;

import java.time.Duration;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import base.BaseTest;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks extends BaseTest {

    @Before
    public void setupScenario() {

        //DIRECT DRIVER INIT (avoid TestNG annotations)

        String browser = "chrome";  

        switch (browser.toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver();
                break;

            case "edge":
                driver = new EdgeDriver();
                break;
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://tipcal-navy.vercel.app/");
        driver.manage().window().maximize();

        System.out.println("✅ Driver initialized in Hooks");
    }

    @After
    public void tearDownScenario() {

        if (driver != null) {
            driver.quit();
        }

        System.out.println("✅ Driver closed");
    }
}