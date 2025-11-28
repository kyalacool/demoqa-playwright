package com.automation.elements;

import com.automation.base.BaseTest;
import com.automation.factory.CheckBoxDataFactory;
import com.automation.page.elements.CheckBoxPage;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.util.regex.Pattern;

import static com.automation.utils.BrowserManager.getPage;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Slf4j
public class CheckBoxPageTest extends BaseTest {

    @Test
    void verifyCheckBoxPageVisibility() {
        CheckBoxPage checkBoxPage = new CheckBoxPage(getPage());
        checkBoxPage
                .navigateToPage();
        assertThat(checkBoxPage.getMainHeader()).hasText("Check Box");
        log.info("Check Box main header is verified.");
    }

    @Test
    void verifyOpenAllAndCloseAllButton() {
        CheckBoxPage checkBoxPage = new CheckBoxPage(getPage());
        String[] toggleNames = CheckBoxDataFactory.getAllToggleName();
        checkBoxPage
                .navigateToPage();
        assertThat(checkBoxPage.getTextedRows()).hasCount(1);
        checkBoxPage
                .openAllToggle();
        assertThat(checkBoxPage.getTextedRows()).hasCount(toggleNames.length);
        checkBoxPage.
                verifyToggleTitlesVisibility(toggleNames)
                .closeAllToggle();
        assertThat(checkBoxPage.getTextedRows()).hasCount(1);
        log.info("'Expands all' and 'Collapse all' buttons are verified ");
    }

    @Test
    void verifyAllToggleFunctionality() {
        CheckBoxPage checkBoxPage = new CheckBoxPage(getPage());
        String[] toggleNames = CheckBoxDataFactory.getAllToggleName();
        checkBoxPage
                .navigateToPage();
        for (String toggleName : toggleNames) {
            checkBoxPage.clickOnToogle(toggleName);
        }
        assertThat(checkBoxPage.getTextedRows()).hasCount(toggleNames.length);
        checkBoxPage.verifyToggleTitlesVisibility(toggleNames);
        log.info("Toggle functionality (one by one) are verified.");
    }

    @Test
    void verifyVisibilityOfCheckBoxes() {
        CheckBoxPage checkBoxPage = new CheckBoxPage(getPage());
        String[] toggleNames = CheckBoxDataFactory.getAllToggleName();
        checkBoxPage
                .navigateToPage();
        assertThat(checkBoxPage.getCheckBoxes()).hasCount(1);
        checkBoxPage
                .openAllToggle();
        assertThat(checkBoxPage.getCheckBoxes()).hasCount(toggleNames.length);
        log.info("All checkbox visibility are verified.");
    }

    @Test
    void verifyCheckBoxFunctionalityOneByOne() {
        CheckBoxPage checkBoxPage = new CheckBoxPage(getPage());
        String[] toggleNames = CheckBoxDataFactory.getAllToggleName();
        checkBoxPage
                .navigateToPage()
                .openAllToggle();
        for (String name : toggleNames) {
            assertThat(checkBoxPage.checkBox(name)).not().isChecked();
            checkBoxPage.checkBox(name).click();
            assertThat(checkBoxPage.checkBox(name)).isChecked();
            checkBoxPage.checkBox(name).click();
        }
        log.info("All checkbox functionality (one by one) are verified.");
    }

    @Test
    void verifyCheckBoxFunctionalityChain() {
        CheckBoxPage checkBoxPage = new CheckBoxPage(getPage());
        String[] toggleNames = CheckBoxDataFactory.getAllToggleName();
        checkBoxPage
                .navigateToPage()
                .openAllToggle();
        for (String name : toggleNames) {
            assertThat(checkBoxPage.checkBox(name)).not().isChecked();
        }
        checkBoxPage
                .checkBox(toggleNames[0]).click();
        for (String name : toggleNames) {
            assertThat(checkBoxPage.checkBox(name)).isChecked();
        }
        checkBoxPage
                .checkBox(toggleNames[0]).click();
        for (String name : toggleNames) {
            assertThat(checkBoxPage.checkBox(name)).not().isChecked();
        }
        log.info("Checkbox chain functionality are verified");
    }

    @Test
    void verifyHalfCheckedHome(){
        CheckBoxPage checkBoxPage = new CheckBoxPage(getPage());
        checkBoxPage
                .navigateToPage()
                .openAllToggle()
                .checkBox("Home").click();
        checkBoxPage
                .checkBox("Documents").click();
        assertThat(checkBoxPage.checkBox("Home")
                .locator("svg"))
                .hasClass(Pattern.compile(".*half-check.*"));
        log.info("Home's half checked checkbox functionality are verified.");
    }

    @Test
    void verifyResultWindow(){
        CheckBoxPage checkBoxPage = new CheckBoxPage(getPage());
        checkBoxPage
                .navigateToPage()
                .openAllToggle()
                .checkBox("Desktop").click();
        assertThat(checkBoxPage.getCheckedResult()).containsText(new String[] {"desktop", "notes", "commands"});
        log.info("Checkbox reuslt window are verified.");
    }
}
