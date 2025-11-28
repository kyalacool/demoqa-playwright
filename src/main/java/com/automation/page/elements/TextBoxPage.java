package com.automation.page.elements;

import com.automation.page.base.BasePage;
import com.automation.model.TextBoxData;
import com.automation.utils.PropertyReader;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Slf4j
public class TextBoxPage extends BasePage {
    private final Locator nameInput;
    private final Locator emailInput;
    private final Locator currentAddressInput;
    private final Locator permanentAddressInput;
    private final Locator submitButton;
    @Getter
    private final Locator nameResult;
    @Getter
    private final Locator emailResult;
    @Getter
    private final Locator currentResult;
    @Getter
    private final Locator permanentResult;
    @Getter
    private final Locator mainHeader;
    @Getter
    private final Locator textboxOutput;
    @Getter
    private final Locator textBoxOutPutParagraphs;

    public TextBoxPage(Page page) {
        super(page);
        this.nameInput = page.getByPlaceholder("Full Name");
        this.emailInput = page.getByPlaceholder("name@example.com");
        this.currentAddressInput = page.getByPlaceholder("Current Address");
        this.permanentAddressInput = page.locator("#permanentAddress");
        this.submitButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit"));
        this.nameResult = page.locator("p#name");
        this.emailResult = page.locator("p#email");
        this.currentResult = page.locator("p#currentAddress");
        this.permanentResult = page.locator("p#permanentAddress");
        this.mainHeader = page.locator("h1.text-center");
        this.textboxOutput = page.locator("div #output");
        this.textBoxOutPutParagraphs = page.locator("div #output p");
    }

    public void navigateToPage() {
        String textBoxUrl = PropertyReader.getProperty("url.textbox");
        page.navigate(baseUrl + textBoxUrl);
    }

    public TextBoxPage fillAllInputAndSubmit(TextBoxData data) {
        nameInput.fill(data.getFullName());
        emailInput.fill(data.getEmail());
        currentAddressInput.fill(data.getCurrentAddress());
        permanentAddressInput.fill(data.getPermanentAddress());
        submitButton.click();
        return this;
    }

    public void verifyInvalidEmailError() {
        String validationMessage = emailInput.evaluate("e => e.validationMessage").toString();
        Assert.assertFalse(validationMessage.isEmpty());
        assertThat(emailInput).hasClass(Pattern.compile("field-error"));
        log.info("Invalid email field verified. 'field error' class appeared,\n error message verified : {}", validationMessage);
    }

    public void verifyValidResult(TextBoxData validInputData) {
        assertThat(this.nameResult).hasText(Pattern.compile(validInputData.getFullName()));
        assertThat(this.emailResult).hasText(Pattern.compile(validInputData.getEmail()));
        assertThat(this.currentResult).hasText(Pattern.compile(validInputData.getCurrentAddress()));
        assertThat(this.permanentResult).hasText(Pattern.compile(validInputData.getPermanentAddress()));
        log.info("Valid data verified: \n {}", validInputData);
    }

    public void fillIncompleteInputAndSubmit(TextBoxData inputData, int numberOfFilledInput) {

        if (numberOfFilledInput < 0 || numberOfFilledInput > 3){
            throw new IllegalArgumentException("The number of completed field should be between 0 and 3");
        }

        if (numberOfFilledInput >= 1){
            nameInput.fill(inputData.getFullName());
        }
        if (numberOfFilledInput >= 2) {
            emailInput.fill(inputData.getEmail());
        }
        if (numberOfFilledInput >= 3) {
            currentAddressInput.fill(inputData.getCurrentAddress());
        }

        submitButton.click();
    }
}
