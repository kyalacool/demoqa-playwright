package com.automation.base;

import com.automation.page.ElementsMainPage;
import org.testng.annotations.Test;

public class ElementsPageTest extends BaseTest {

    @Test
    void verifyElementsSubPagesNamesAndRoutes() {
        ElementsMainPage elementsMainPage = new ElementsMainPage(getPage());
        elementsMainPage.printAllElementsSubPage();
    }
}
