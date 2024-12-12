package com.saucedemo.page_object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Optional;

import static com.saucedemo.utils.Helper.convertStringWithDollarToDouble;

public class CheckoutPage {

    public CheckoutPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    private final By itemPriceElement = By.xpath(".//div[@class='inventory_item_price']");

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

    @FindBy(how = How.CLASS_NAME, using = "summary_tax_label")
    private WebElement taxLabel;

    @FindBy(how = How.CLASS_NAME, using = "summary_total_label")
    private WebElement summaryTotalLabel;

    @FindBy(how = How.ID, using = "finish")
    private WebElement finishButton;

    @FindBy(how = How.CLASS_NAME, using = "complete-header")
    private WebElement completeHeader;

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

    public List<WebElement> getCartItems() {
        return cartItems;
    }

    public WebElement getTaxLabel() {
        return taxLabel;
    }

    public WebElement getSummaryTotalLabel() {
        return summaryTotalLabel;
    }

    public WebElement getFinishButton() {
        return finishButton;
    }

    public WebElement getCompleteHeader() {
        return completeHeader;
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

        return cartItem.get().findElement(itemPriceElement).getText();
    }

    public double getTax() {
        return convertStringWithDollarToDouble(getTaxLabel().getText().replace("Tax: ", ""));
    }

    public double getSummaryTotal() {
        return convertStringWithDollarToDouble(getSummaryTotalLabel().getText().replace("Total: ", ""));
    }
}