package com.automation.test.elements;

import com.automation.base.BaseTest;
import com.automation.factory.TextBoxDataFactory;
import com.automation.model.TextBoxData;
import com.automation.page.elements.TextBoxPage;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Slf4j
public class TextBoxPageTest extends BaseTest {

    @Test
    void verifyTextBoxVisibility() {
        TextBoxPage textBoxPage = new TextBoxPage(getPage());
        textBoxPage
                .navigateToPage();
        assertThat(textBoxPage.getMainHeader()).hasText("Text Box");
    }

    @Test
    void verifyTextBoxWithValidData() {
        TextBoxData validInputData = TextBoxDataFactory.createValidTextBoxData();
        TextBoxPage textBoxPage = new TextBoxPage(getPage());
        textBoxPage
                .navigateToPage();
        assertThat(textBoxPage.getTextboxOutput()).isHidden();
        textBoxPage
                .fillAllInputAndSubmit(validInputData);
        assertThat(textBoxPage.getTextboxOutput()).isVisible();
        textBoxPage
                .verifyValidResult(validInputData);
    }

    @Test
    void verifyTextBoxPageWithInvalidEmail() {
        TextBoxData dataWithInvalidEmail = TextBoxDataFactory.createInvalidTextBoxDataWrongEmail();
        TextBoxPage textBoxPage = new TextBoxPage(getPage());
        textBoxPage
                .navigateToPage();
        assertThat(textBoxPage.getTextboxOutput()).isHidden();
        textBoxPage
                .fillAllInputAndSubmit(dataWithInvalidEmail)
                .verifyInvalidEmailError();
        assertThat(textBoxPage.getTextboxOutput()).isHidden();
    }

    @Test
    void verifyTextBoxWithIncompleteData() {
        TextBoxData validInputData = TextBoxDataFactory.createValidTextBoxData();
        TextBoxPage textBoxPage = new TextBoxPage(getPage());
        textBoxPage
                .navigateToPage();
        assertThat(textBoxPage.getTextboxOutput()).isHidden();
        textBoxPage
                .fillIncompleteInputAndSubmit(validInputData.getFullName(), validInputData.getEmail());
        assertThat(textBoxPage.getTextboxOutput()).isVisible();
        assertThat(textBoxPage.getTextBoxOutPutParagraphs()).hasCount(2);
        textBoxPage
                .reloadPage()
                .fillIncompleteInputAndSubmit(validInputData.getFullName());
        assertThat(textBoxPage.getTextBoxOutPutParagraphs()).hasCount(1);
        log.info("Incomplete data verified.");
    }
}
