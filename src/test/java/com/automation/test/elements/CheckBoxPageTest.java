package com.automation.test.elements;

import com.automation.base.BaseTest;
import com.automation.page.elements.CheckBoxPage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CheckBoxPageTest extends BaseTest {

    @DataProvider(name = "checkBoxNames")
    public Object[][] checkBoxNames(){
        return new Object[][] {
                {"Home"},
                {"Desktop"},
                {"Notes"},
                {"Commands"},
                {"Documents"},
                {"WorkSpace"},
                {"React"},
                {"Angular"},
                {"Veu"},
                {"Office"},
                {"Public"},
                {"Private"},
                {"Classified"},
                {"General"},
                {"Downloads"},
                {"Word File.doc"},
                {"Excel File.doc"}
        };
    }

    @Test
    void verifyCheckBoxPageVisibility() {
        CheckBoxPage checkBoxPage = new CheckBoxPage(getPage());
        checkBoxPage
                .navigateToPage();
        assertThat(checkBoxPage.getMainHeader()).hasText("Check Box");
    }

//TODO : CONTINUE HERE!
    @Test(dataProvider = "checkBoxNames")
    void clickAllToggle(String name){
        CheckBoxPage checkBoxPage = new CheckBoxPage(getPage());
        checkBoxPage
                .navigateToPage()
                .clickOnToogle(name);
    }
}
