package com.saucedemo.page_object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Optional;

public class CheckoutPage {
    public CheckoutPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    // Create 3 fields and 1 button using @FindBy;

    @FindBy(how = How.ID, using = "first-name")
    private WebElement firstNameInputField;

    @FindBy(how = How.ID, using = "last-name")
    private WebElement lastNameInputField;

    @FindBy(how = How.ID, using = "postal-code")
    private WebElement postalCodeInputField;

    @FindBy(how = How.ID, using = "continue")
    private WebElement submitButton;

    @FindBy(how = How.CLASS_NAME, using = "cart_item")
    private List<WebElement> cartItems;

    public WebElement getFirstNameInputField() {
        return firstNameInputField;
    }

    public WebElement getLastNameInputField() {
        return lastNameInputField;
    }

    public WebElement getPostalCodeInputField() {
        return postalCodeInputField;
    }

    public WebElement getSubmitButton() {
        return submitButton;
    }
    public void fillStepOne(String firstName, String lastName, String postalCode) {
        firstNameInputField.sendKeys(firstName);
       lastNameInputField.sendKeys(lastName);
       postalCodeInputField.sendKeys(postalCode);
       submitButton.click();

    }
    public String getPriceByItemName(String itemName) {
        Optional<WebElement> cartItem = cartItems.stream()
                .filter(item -> item.getText().contains(itemName)).
                findFirst();

        return cartItem.get().findElement(By.xpath(".//div[@class='inventory_item_price']")).getText();
    }

   public double convertStringWithDollarToDouble(String amount) {
      return Double.parseDouble(amount.replace("$", ""));

   }
}
