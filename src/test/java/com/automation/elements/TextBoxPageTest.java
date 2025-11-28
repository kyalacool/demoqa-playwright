package com.automation.elements;

import com.automation.BaseTest;
import com.automation.factory.TextBoxDataFactory;
import com.automation.model.TextBoxData;
import com.automation.page.elements.TextBoxPage;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.automation.utils.BrowserManager.getPage;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Slf4j
public class TextBoxPageTest extends BaseTest {

    @Test
    void verifyTextBoxVisibility() {
        TextBoxPage textBoxPage = new TextBoxPage(getPage());
        textBoxPage
                .navigateToPage();
        assertThat(textBoxPage.getMainHeader()).hasText("Text Box");
        log.info("Text Box main header is verified.");
    }

    @Test
    void verifyTextBoxWithValidData() {
        TextBoxData validInputData = TextBoxDataFactory.createValidTextBoxInputData();
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
        TextBoxData dataWithInvalidEmail = TextBoxDataFactory.createInvalidTextBoxInputDataWithWrongEmail();
        TextBoxPage textBoxPage = new TextBoxPage(getPage());
        textBoxPage
                .navigateToPage();
        assertThat(textBoxPage.getTextboxOutput()).isHidden();
        textBoxPage
                .fillAllInputAndSubmit(dataWithInvalidEmail)
                .verifyInvalidEmailError();
        assertThat(textBoxPage.getTextboxOutput()).isHidden();
    }

    @DataProvider(name = "numberOfFilledFields", parallel = true)
    public Object[][] numberOfFilledFields(){
        return new Object[][] {
                {0}, {1}, {2}, {3}
        };
    }
    @Test(dataProvider = "numberOfFilledFields")
    void verifyTextBoxWithIncompleteData(int numberOfFilledFields) {
        TextBoxPage textBoxPage = new TextBoxPage(getPage());
        TextBoxData inputData = TextBoxDataFactory.createValidTextBoxInputData();
        textBoxPage
                .navigateToPage();
        assertThat(textBoxPage.getTextboxOutput()).isHidden();
        textBoxPage
                .fillIncompleteInputAndSubmit(inputData,numberOfFilledFields);
        if (numberOfFilledFields == 0){
            assertThat(textBoxPage.getTextboxOutput()).isHidden();
        } else {
        assertThat(textBoxPage.getTextboxOutput()).isVisible();
        assertThat(textBoxPage.getTextBoxOutPutParagraphs()).hasCount(numberOfFilledFields);}
        log.info("Incomplete data verified with {} filled field.", numberOfFilledFields);
    }
}
