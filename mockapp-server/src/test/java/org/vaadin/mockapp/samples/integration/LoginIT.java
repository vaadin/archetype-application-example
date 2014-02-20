package org.vaadin.mockapp.samples.integration;

import com.vaadin.testbench.By;
import com.vaadin.testbench.TestBenchElement;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.*;

public class LoginIT extends TestBenchTestCase {

    @Before
    public void setUp() throws Exception {
        setDriver(new FirefoxDriver());
    }

    @After
    public void tearDown() throws Exception {
        getDriver().quit();
    }

    private void clearAndType(TestBenchElement element, String text) {
        element.clear();
        element.sendKeys(text);
    }

    @Test
    public void testLoginAsAdmin_formIsVisible() throws Exception {
        getDriver().get("http://localhost:8080/?restartApplication");

        TextFieldElement usernameField = $(TextFieldElement.class).caption("Username").first();
        clearAndType(usernameField, "admin");
        clearAndType($(PasswordFieldElement.class).first(), "foo");

        $(ButtonElement.class).caption("Login").first().click();

        $(MenuBarElement.class).first().findElements(By.xpath("span[@class='v-menubar-menuitem']")).get(0).click();

        assertFalse($(ButtonElement.class).caption("Save").all().isEmpty());
    }

    @Test
    public void testLoginAsUser_formNotVisible() throws Exception {
        getDriver().get("http://localhost:8080/?restartApplication");

        TextFieldElement usernameField = $(TextFieldElement.class).caption("Username").first();
        clearAndType(usernameField, "user");
        clearAndType($(PasswordFieldElement.class).first(), "foo");

        $(ButtonElement.class).caption("Login").first().click();

        $(MenuBarElement.class).first().findElements(By.xpath("span[@class='v-menubar-menuitem']")).get(0).click();

        LabelElement infoLabel = $(LabelElement.class).in(PanelElement.class).first();
        assertNotNull(infoLabel);
        assertEquals("Login as 'admin' to have edit access", infoLabel.getText());

        assertTrue($(ButtonElement.class).caption("Save").all().isEmpty());
    }

}
