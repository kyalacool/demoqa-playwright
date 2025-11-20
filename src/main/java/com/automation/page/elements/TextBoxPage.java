package com.automation.page.elements;

import com.automation.page.BasePage;
import com.automation.utility.PropertyReader;
import com.automation.utility.WebDriverManager;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

@Slf4j
public class TextBoxPage extends BasePage {
    private final String textBoxUrl;
    private final String title;
    private final Locator titleLocator;
    private final Locator nameInput;
    private final Locator emailInput;
    private final Locator currentAddressInput;
    private final Locator permanentAddressInput;
    private final Locator submitButton;
    private final Locator nameResult;
    private final Locator emailResult;
    private final Locator currentResult;
    private final Locator permanentResult;

    private final Map<String, String> dataMap = Map.of(
            "name", "Bence Varga",
            "email", "bencevarga@gmail.com",
            "invalidEmail", "asa",
            "current", "Bp. 1123 Ilka utca 6.",
            "permanent", "Szeged Tiszavirág utca 8.");

    public TextBoxPage(Page page) {
        super(page);
        PropertyReader.getInstance();
        this.textBoxUrl = WebDriverManager.getHomeUrl() + PropertyReader.getProperty("url.textbox");
        this.nameInput = page.getByPlaceholder("Full Name");
        this.emailInput = page.getByPlaceholder("name@example.com");
        this.currentAddressInput = page.getByPlaceholder("Current Address");
        this.permanentAddressInput = page.locator("#permanentAddress");
        this.submitButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit"));
        this.nameResult = page.locator("#name");
        this.emailResult = page.locator("#email");
        this.currentResult = page.locator("#currentAddress");
        this.permanentResult = page.locator("#permanentAddress");
        this.title = "Text Box";
        this.titleLocator = page.locator("h1.text-center");
    }

    private boolean isResultContain(Locator result, String key) {
        return result.allInnerTexts().getLast().contains(dataMap.get(key));
    }

    public TextBoxPage verifyPageVisibility(){
        page.navigate(textBoxUrl);
        Assert.assertEquals(titleLocator.innerText(), title);
        return this;
    }

    public TextBoxPage fillAndSubmitWithValidData() {
        Assert.assertFalse(page.locator("div #output").isVisible());
        nameInput.fill(dataMap.get("name"));
        emailInput.fill(dataMap.get("email"));
        currentAddressInput.fill(dataMap.get("current"));
        permanentAddressInput.fill(dataMap.get("permanent"));
        submitButton.click();
        return this;
    }

    public TextBoxPage fillAndSubmitWithInvalidEmail() {
        page.reload();
        Assert.assertFalse(page.locator("div #output").isVisible());
        String errorMessage = emailInput.evaluate("e => e.validationMessage").toString();
        Assert.assertNotEquals(errorMessage, "Please include an '@' in the email address. '"+dataMap.get("invalidEmail")+"' is missing an '@'.");
        nameInput.fill(dataMap.get("name"));
        emailInput.fill(dataMap.get("invalidEmail"));
        currentAddressInput.fill(dataMap.get("current"));
        permanentAddressInput.fill(dataMap.get("permanent"));
        submitButton.click();
        return this;
    }

    public TextBoxPage verifyInvalidEmailResult() {
        Assert.assertFalse(page.locator("div #output").isVisible());
        try {
            emailInput.page().waitForFunction(
                    "([e, args]) => window.getComputedStyle(e).getPropertyValue('border-top-color') === args.color",
                    List.of(emailInput.elementHandle(), Map.of("color", "rgb(255, 0, 0)")),
                    new Page.WaitForFunctionOptions().setTimeout(5000));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        String newColor = emailInput.evaluate("e => window.getComputedStyle(e).getPropertyValue('border-top-color')").toString();
        Assert.assertEquals(newColor, "rgb(255, 0, 0)");
        String errorMessage = emailInput.evaluate("e => e.validationMessage").toString();
        Assert.assertEquals(errorMessage, "Please include an '@' in the email address. '"+dataMap.get("invalidEmail")+"' is missing an '@'.");
        log.info("Text Box subpage verified with invalid email.");
        return this;
    }

    public TextBoxPage verifyValidResult() {
        Assert.assertTrue(page.locator("div #output").isVisible());
        Assert.assertTrue(isResultContain(nameResult, "name"));
        Assert.assertTrue(isResultContain(emailResult, "email"));
        Assert.assertTrue(isResultContain(currentResult, "current"));
        Assert.assertTrue(isResultContain(permanentResult, "permanent"));
        log.info("Text Box subpage verified with valid data.");
        return this;
    }

    public void submitAndVerifyIncompleteForm(){
        page.reload();
        Assert.assertFalse(page.locator("div #output").isVisible());
        emailInput.fill(dataMap.get("email"));
        submitButton.click();
        Assert.assertTrue(page.locator("div #output").isVisible());
        Assert.assertEquals(page.locator("div #output div p").count(), 1);
        nameInput.fill(dataMap.get("name"));
        submitButton.click();
        Assert.assertEquals(page.locator("div #output div p").count(), 2);
        log.info("Text Box subpage verified with incomplete data.");
    }
}
