import com.github.javafaker.Faker;
import com.saucedemo.page_object.*;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SauceDemoTest {

    ChromeDriver driver;
    LoginPage loginPage;
    InventoryPage inventoryPage;
    HeaderPage headerPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;

    Configurations configs;
    Configuration config;
    Faker randomData = new Faker();


    @BeforeMethod
    public void setUp() throws ConfigurationException {
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        headerPage = new HeaderPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);

        configs = new Configurations();
        config = configs.properties("config.properties");

        driver.get(config.getString("web.url"));
    }

    @Test
    public void sauceDemoLoginTest() {
        loginPage.authorize(config.getString("username"), config.getString("password"));
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html");
    }

    @Test
    public void sauceDemoAddItemToTheCartTest() {
        loginPage.authorize(config.getString("username"), config.getString("password"));
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html");

        inventoryPage.selectItemByName("Backpack");
        inventoryPage.selectItemByName("Bike Light");
        assertThat(headerPage.getShoppingCartBadge().getText()).isEqualTo("2");

        headerPage.getShoppingCartLink().click();
        assertThat(cartPage.getCartItems().size()).isEqualTo(2);

        // Classic way of asserting by contains
        assertThat(cartPage.getCartItems().get(0).getText()).contains("Backpack");
        assertThat(cartPage.getCartItems().get(1).getText()).contains("Bike Light");

        // Functional programming style -> Handling partial matches
        assertThat(cartPage.getCartItems())
                .extracting(WebElement::getText)
                .anyMatch(text -> text.contains("Bike Light"));

        assertThat(cartPage.getCartItems())
                .extracting(WebElement::getText)
                .anyMatch(text -> text.contains("Backpack"));

        cartPage.getCheckoutButton().click();
        checkoutPage.fillStepOne(
                randomData.funnyName().name(),
                randomData.howIMetYourMother().character(),
                randomData.address().zipCode() );
      double backpackPrice = checkoutPage.convertStringWithDollarToDouble(checkoutPage.getPriceByItemName("Backpack"));
      double bikeLightPrice = checkoutPage.convertStringWithDollarToDouble(checkoutPage.getPriceByItemName("Bike Light"));
      double sumPrice = backpackPrice + bikeLightPrice;
        System.out.println(sumPrice);


    }

    @AfterMethod
    public void tearDown() {
        driver.close();
        driver.quit();
    }
}