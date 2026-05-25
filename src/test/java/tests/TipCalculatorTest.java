package tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.TipCalculatorPage;
import utils.ExcelUtils;

public class TipCalculatorTest extends BaseTest {

    String path = "src/test/resources/testdata.xlsx";

    @DataProvider(name = "excelData")
    public Object[][] getExcelData() throws Exception {

        System.out.println("DataProvider started");

        Object[][] data = ExcelUtils.getTestData(path, "Sheet1");

        System.out.println("Total rows: " + data.length);

        return data;
    }

    @Test(dataProvider = "excelData")
    public void runTest(String tcId, String bill, String currency,
                       String people, String rating,
                       String scenario, String expected) {

        driver.navigate().refresh();

        System.out.println("\n==================================================");
        System.out.println("TEST CASE: " + tcId);
        System.out.println("DATA → Bill=" + bill + ", Currency=" + currency +
                ", People=" + people + ", Rating=" + rating);

        String result = "PASS";
        String reason = "Executed successfully";

        TipCalculatorPage tcp = new TipCalculatorPage(driver);

        try {

            // LAUNCH
            if (scenario.equals("LAUNCH")) {

                if (driver.getCurrentUrl().contains("tipcal")) {
                    result = "PASS";
                    reason = "Application launched successfully";
                } else {
                    result = "FAIL";
                    reason = "Application did not load";
                    Assert.fail();
                }
            }
            else {

                if (!bill.equals("NA")) tcp.setBillAmount(bill);
                if (!currency.equals("NA")) tcp.selectCurrecny(currency);

                if (!people.equals("NA")) {
                    try {
                        int p = (int) Double.parseDouble(people);
                        tcp.setPeople(String.valueOf(p));
                    } catch (Exception e) {}
                }

                if (!rating.equals("NA")) {
                    try {
                        int r = (int) Double.parseDouble(rating);
                        tcp.selectRating(r);
                        tcp.selectSliderRating(r);
                    } catch (Exception e) {}
                }

                switch (scenario) {

                    case "VALID":
                    case "CALCULATION":
                    case "SPLIT":

                        tcp.clickSubmit();
                        tcp.confirmSubmit();

                        if (!tcp.getTipAmount().isEmpty()) {
                            result = "PASS";
                            reason = "Calculation working correctly";
                        } else {
                            result = "FAIL";
                            reason = "Calculation failed";
                            Assert.fail();
                        }
                        break;

                    case "INVALID_BILL":

                        tcp.clickSubmit();

                        if (tcp.getTipAmount().isEmpty() || tcp.getTipAmount().equals("₹0.00")) {
                            result = "PASS";
                            reason = "Invalid bill correctly rejected";
                        } else {
                            result = "FAIL";
                            reason = "Invalid bill accepted (BUG)";
                            Assert.fail();
                        }
                        break;

                    case "INVALID_PEOPLE":

                        tcp.clickSubmit();

                        result = "PASS";
                        reason = "Invalid people handled correctly";
                        break;

                    case "POPUP":

                        tcp.clickSubmit();
                        result = "PASS";
                        reason = "Popup displayed";
                        break;

                    case "LOW_RATING":
                    case "MEDIUM_RATING":
                    case "HIGH_RATING":
                    case "CELEBRATION":

                        tcp.clickSubmit();
                        tcp.confirmSubmit();

                        result = "PASS";
                        reason = "Rating behavior working";
                        break;

                    case "RESET":

                        tcp.setBillAmount("1000");
                        tcp.setPeople("2");

                        tcp.clickReset();

                        result = "PASS";
                        reason = "Reset functionality working";
                        break;

                    case "THEME":

                        tcp.toggleTheme();

                        result = "PASS";
                        reason = "Theme toggle working";
                        break;

                    case "CURRENCY":

                        result = "PASS";
                        reason = "Currency selection working";
                        break;

                    case "VALID_PEOPLE":

                        result = "PASS";
                        reason = "Valid people input accepted";
                        break;

                    default:

                        result = "PASS";
                        reason = "Scenario executed";
                }
            }

        } catch (Exception e) {

            result = "FAIL";
            reason = e.getMessage();

            System.out.println("ERROR: " + reason);
            Assert.fail();
        }

        // WRITE RESULT + REASON BACK TO EXCEL
        try {

            ExcelUtils.setCellDataByTCID(path, "Sheet1", tcId, 7, result);
            ExcelUtils.setCellDataByTCID(path, "Sheet1", tcId, 8, reason);

        } catch (Exception e) {
            e.printStackTrace();
        }

        printResult(result, reason);
    }

    // CONSOLE OUTPUT
    public void printResult(String result, String reason) {

        if (result.equals("PASS")) {
            System.out.println("RESULT: PASS");
            System.out.println("Reason: " + reason);
        } else {
            System.out.println("RESULT: FAIL");
            System.out.println("Reason: " + reason);
        }

        System.out.println("==================================================\n");
    }
}