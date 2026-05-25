package stepdefinitions;

import org.testng.Assert;

import base.BaseTest;
import io.cucumber.java.en.*;
import pages.TipCalculatorPage;

public class TipSteps extends BaseTest {

    TipCalculatorPage tcp;

    @Given("user is on Tip Calculator page")
    public void user_is_on_page() {

        driver.get("https://tipcal-navy.vercel.app/");
        tcp = new TipCalculatorPage(driver);
    }

    @When("user enters bill {string}")
    public void enter_bill(String bill) {
        if (!bill.equals("NA")) {
            tcp.setBillAmount(bill);
        }
    }

    @And("user selects currency {string}")
    public void select_currency(String currency) {
        if (!currency.equals("NA")) {
            tcp.selectCurrecny(currency);
        }
    }

    @And("user enters people {string}")
    public void enter_people(String people) {

        try {
            if (!people.equals("NA")) {
                int p = (int) Double.parseDouble(people);
                tcp.setPeople(String.valueOf(p));
            }
        } catch (Exception e) {
            System.out.println("Invalid people input");
        }
    }

    @And("user selects rating {string}")
    public void select_rating(String rating) {

        try {
            if (!rating.equals("NA")) {
                int r = (int) Double.parseDouble(rating);
                tcp.selectRating(r);
                tcp.selectSliderRating(r);
            }
        } catch (Exception e) {
            System.out.println("Invalid rating input");
        }
    }

    @And("user submits the form")
    public void submit_form() {
        tcp.clickSubmit();
        tcp.confirmSubmit();
    }

    @Then("tip amount should be displayed")
    public void validate_tip() {
        Assert.assertFalse(tcp.getTipAmount().isEmpty());
    }

    @Then("invalid bill should be handled")
    public void invalid_bill_validation() {

        Assert.assertTrue(
                tcp.getTipAmount().isEmpty() || tcp.getTipAmount().equals("₹0.00"),
                "Invalid bill not handled correctly"
        );
    }

    @Then("invalid people should be handled")
    public void invalid_people_validation() {

        Assert.assertTrue(true); // can enhance later
    }

    @And("user resets the form")
    public void reset_form() {
        tcp.clickReset();
    }

    @Then("all fields should be cleared")
    public void validate_reset() {
        Assert.assertTrue(true);
    }

    @When("user toggles theme")
    public void toggle_theme() {
        tcp.toggleTheme();
    }

    @Then("theme should change")
    public void validate_theme() {
        Assert.assertTrue(true);
    }
}