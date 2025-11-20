package com.automation.base;

import com.automation.page.ElementsMainPage;
import com.automation.page.elements.TextBoxPage;
import org.testng.annotations.Test;

public class ElementsPageTest extends BaseTest {

    @Test
    void verifyElementsSubPagesNamesAndRoutes() {
        ElementsMainPage elementsMainPage = new ElementsMainPage(getPage());
        elementsMainPage
                .printAllElementsSubPage();
    }

    @Test
    void verifyTextBoxSubPage(){
        TextBoxPage textBoxPage = new TextBoxPage(getPage());
        textBoxPage
                .verifyPageVisibility()
                .fillAndSubmitWithValidData()
                .verifyValidResult()
                .fillAndSubmitWithInvalidEmail()
                .verifyInvalidEmailResult()
                .submitAndVerifyIncompleteForm();
    }
}
