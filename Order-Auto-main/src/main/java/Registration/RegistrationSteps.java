package Registration;


import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationSteps {
    WebDriver driver = null;
    WebDriverWait wait = null;
    JavascriptExecutor js = null;
    Variables var = new Variables();
    String username,password;
    DataTable dataTable;
    String ItemPrice;


    private class Variables {
        String MyStor_Home = "http://automationpractice.com";
        String chrome_path = "C:\\Users\\Sanele Mbense\\Documents\\Assessment\\Order-Auto-main\\src\\main\\resources\\drivers\\chromedriver.exe";
        String Sign_in_button = "//*[@id=\"header\"]//a[contains(.,\"Sign in\")]";
        String Authentication_page = "//*[@id=\"center_column\"]/h1[contains(.,\"Authentication\")]";
        String Create_account_email = "email_create";
        String Create_account_button = "SubmitCreate";
        String email_exists_errormessage = "An account using this email address has already been registered. Please enter a valid password or request a new one.";
        String email_invalid_errormessage = "Invalid email address";
        String email_already_exists = "exists";
        String email_format_invalid = "invalid";
        String email = "Test@1222email.com";
        String Password = "54321";
    }
    public void SetChromeBrowserDriver(){
        System.setProperty("webdriver.chrome.driver",var.chrome_path);
        driver = new ChromeDriver();

    }


    @Before
    public void Load_Browser_and_navigate_to_registration(){
        //TC1 - Open website, do a search and confirm that the search is displayed
        String projectPath = System.getProperty("user.dir");
        SetChromeBrowserDriver();
        js = (JavascriptExecutor) driver;
        driver.get(var.MyStor_Home);
        //driver.get("http://automationpractice.com");
        driver.navigate().to("http://automationpractice.com/index.php");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.findElement(By.id("page")).isDisplayed();
        verifyHomePageLoad();
        //The_user_enters_an_already_existing_email();

        driver.findElement(By.xpath("//*//a[contains(@title,\"My Store\")]")).isDisplayed();
        driver.findElement(By.xpath("//*//a[contains(@title,\"My Store\")]")).click();
        wait = new WebDriverWait(driver,30);
        driver.findElement(By.xpath("//*[@id=\"search_query_top\"]")).isDisplayed();
        driver.findElement(By.xpath("//*[@id=\"search_query_top\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"search_query_top\"]")).sendKeys("Blouse");
        driver.findElement(By.xpath("//*[@id=\"searchbox\"]/button")).click();
        driver.findElement(By.xpath("//*[@id=\"center_column\"]/h1")).isDisplayed();
        driver.findElement(By.xpath("//*[@id=\"center_column\"]/h1[contains(.,\"Blouse\")]")).getText();

    }
    @Given("The user enters an already existing email")
    public void The_user_enters_an_already_existing_email() {
        // This function validates that the user is redirected to the correct page
        driver.findElement(By.id("header_logo")).isDisplayed();
        driver.findElement(By.className("ajax_cart_no_product")).isDisplayed();
        driver.findElement(By.className("login")).isDisplayed();

    }

    @Given("The user attempts to register with an existing account")
    public void The_user_attempts_to_register_with_an_existing_account(){
        driver.findElement(By.id("header_logo")).isDisplayed();
        driver.findElement(By.xpath(var.Sign_in_button)).isDisplayed();
        driver.findElement(By.xpath(var.Sign_in_button)).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.findElement(By.xpath(var.Authentication_page)).isDisplayed();
        //enter_user_email(email);

    }

    @Given("the user enters their {string} and clicks create")
    public void enter_user_email(){
        username_and_Password_shows_on_console_from_datatable(dataTable);
        driver.findElement(By.id(var.Create_account_email)).isDisplayed();
        driver.findElement(By.id(var.Create_account_email)).clear();
        driver.findElement(By.id(var.Create_account_email)).click();
        driver.findElement(By.id(var.Create_account_email)).sendKeys(username);
        //driver.findElement(By.id(var.Create_account_label_create)).click();
        driver.findElement(By.id(var.Create_account_button)).click();
        //validate_user_already_exists();
    }

    @And("Error message is displayed on clicking create")
    public void validate_user_already_exists(){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        //driver.findElement(By.id(var.email_exists_errormessage)).isDisplayed();
        Verify_error_message(var.email_exists_errormessage, var.email_already_exists);
    }

    public void verify_user_email_valid(){
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Verify_error_message(var.email_invalid_errormessage, var.email_format_invalid);
        //driver.findElement(By.id(var.email_invalid_errormessage)).isDisplayed();
    }

    public void Verify_error_message(String message, String error ){
        String pageSource= driver.getPageSource();

        if (error == "invalid"){
            boolean invalidDisplayed = pageSource.contains(message);
            if (invalidDisplayed){
                System.out.println("Invalid email address entered error message is displayed");
            } else {
                System.out.println("Invalid email address entered error message is not displayed to the user");
            }
        } else if (error == "exists"){
            boolean existsDisplayed = pageSource.contains(message);
            if (existsDisplayed){
                System.out.println("Already exists email address entered error message is displayed");
            } else {
                System.out.println("Already exists email address entered error message is not displayed to the user");
            }
        }

    }

    public void verifyHomePageLoad(){

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        String url = driver.getCurrentUrl();
        UserCurrentPage(url);

    }

    public void UserCurrentPage(String url){
        if(url.equals("http://automationpractice.com/index.php")){
            System.out.println("Correct page is being presented to the user");
        } else {
            System.out.println("User is presented with another page");
        }
    }

    @Then("UserName and Password shows on console from datatable.")
    public void username_and_Password_shows_on_console_from_datatable(DataTable dataTable) {
        System.out.println("Data Table UserName : " + dataTable.asLists().get(1).get(0) + " Password : " + dataTable.asLists().get(1).get(1));
        System.out.println("Data Table UserName : " + dataTable.asList().get(2) + " Password : " + dataTable.asList().get(3));
        System.out.println("Data Table UserName : " + dataTable.asMaps().get(0).get("UserName") + " Password : " + dataTable.asMaps().get(0).get("Password"));
        System.out.println("Data Table UserName : " + dataTable.asMaps(String.class, String.class).get(0).get("UserName") + " Password : " + dataTable.asMaps(String.class, String.class).get(0).get("Password"));
        username= dataTable.asMaps(String.class, String.class).get(0).get("UserName");
        password= dataTable.asMaps(String.class, String.class).get(1).get("password");
    }

    @Given("An Existing user Logins in and searches the Tshirt Category")
    public void an_existing_user_logins_in_and_searches_the_tshirt_category() throws InterruptedException {
        // Write code here that turns the phrase above into concrete actions
        //this function Logs in an existing user using the variables set at the top of this class
        driver.findElement(By.xpath("//*//a[contains(.,\"Sign in\")]")).isDisplayed();
        driver.findElement(By.xpath("//*//a[contains(.,\"Sign in\")]")).click();
        wait = new WebDriverWait(driver,30);
        driver.findElement(By.xpath("//*[@id=\"create-account_form\"]/h3[contains(.,\"Create an account\")]")).isDisplayed();
        driver.findElement(By.id("email")).isDisplayed();
        driver.findElement(By.id("email")).isEnabled();
        driver.findElement(By.id("email")).click();
        driver.findElement(By.id("email")).sendKeys(var.email);
        wait = new WebDriverWait(driver,30);
        driver.findElement(By.id("passwd")).isDisplayed();
        driver.findElement(By.id("passwd")).isEnabled();
        driver.findElement(By.id("passwd")).click();
        driver.findElement(By.id("passwd")).sendKeys(var.Password);
        wait = new WebDriverWait(driver,30);
        driver.findElement(By.id("SubmitLogin")).isDisplayed();
        driver.findElement(By.id("SubmitLogin")).isEnabled();
        driver.findElement(By.id("SubmitLogin")).click();
        driver.findElement(By.xpath("//*//a[contains(@title,\"My Store\")]")).isDisplayed();
        driver.findElement(By.xpath("//*//a[contains(@title,\"My Store\")]")).click();
        wait = new WebDriverWait(driver,50);
        driver.findElement(By.xpath("//*[@id=\"block_top_menu\"][contains(.,\"T-shirts\")]")).isDisplayed();
        driver.findElement(By.xpath("//*[@id=\"block_top_menu\"][contains(.,\"T-shirts\")]")).click();
        driver.findElement(By.className("product-container")).isDisplayed();

        driver.findElement(By.className("product-container")).isDisplayed();
        driver.findElements(By.xpath("//*[@id=\"center_column\"]/ul/li"));


    }
    @When("User Hovers over Product item to reveal and click the add to cart button")
    public void user_hovers_over_product_item_to_reveal_and_click_the_add_to_cart_button() throws InterruptedException {
        // Write code here that turns the phrase above into concrete actions
        Actions actions = new Actions(driver);
        WebElement listItem = driver.findElement(By.xpath("//*[@id=\"homefeatured\"]/li[1]"));
        actions.moveToElement(listItem);
        actions.perform();
        driver.findElement(By.xpath("//*//span[contains(.,\"Add to cart\")]")).isDisplayed();
        driver.findElement(By.xpath("//*//span[contains(.,\"Add to cart\")]")).click();
        wait = new WebDriverWait(driver,10);
        ItemPrice = driver.findElement(By.className("content_price")).getText();
        System.out.println(ItemPrice);
        driver.findElement(By.xpath("//*//span[contains(.,\"Add to cart\")]")).click();

        wait = new WebDriverWait(driver,30);
        driver.findElement(By.xpath("//*[@id=\"layer_cart\"]/div[1]/div[1]/h2")).isDisplayed();
        driver.findElement(By.xpath("//*[@id=\"layer_cart_product_price\"]")).isDisplayed();
        driver.findElement(By.xpath("//*[@id=\"layer_cart_product_price\"]")).isEnabled();
        String OrderPrice = driver.findElement(By.xpath("//*[@id=\"layer_cart_product_price\"]")).getText();
        System.out.println(OrderPrice);
        driver.findElement(By.xpath("//*//h2/span[contains(.,\"There is 1 item in your cart.\")]")).isDisplayed();
        String ItemCount = driver.findElement(By.xpath("//*//h2/span[contains(.,\"There is 1 item in your cart.\")]")).getText();
    }
    @And("user is taken through checkout for validation and summary")
    public void user_is_taken_through_checkout_for_validation_and_summary() throws InterruptedException {
        // Write code here that turns the phrase above into concrete actions
        js.executeScript("window.scrollBy(0,100)");
        clickCheckout();

        driver.findElement(By.id("cart_title")).isDisplayed();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,100)");
        clickSummaryCheckout();

        js.executeScript("window.scrollBy(0,700)");
        clickAddressCheckout();
    }
    @And("Order, shipping and Payment confirmations are successful")
    public void order_shipping_and_payment_confirmations_are_successful() throws InterruptedException {
        // Write code here that turns the phrase above into concrete actions
        // This function clicks on the conrifmation checkbox and calls the address page helper function to click "Proceed to checkout"
        driver.findElement(By.id("cgv")).isDisplayed();
        driver.findElement(By.id("cgv")).isEnabled();
        driver.findElement(By.id("cgv")).click();
        wait = new WebDriverWait(driver,30);
        driver.findElement(By.id("cgv")).isSelected();
        clickAddressCheckout();

        // This function selects the payment Method Bank wire as payment
        driver.findElement(By.className("bankwire")).isDisplayed();
        driver.findElement(By.className("bankwire")).isEnabled();
        driver.findElement(By.className("bankwire")).click();
        wait = new WebDriverWait(driver,30);


        // This function validates that the user is taken to correct page and clicks confirm order, and waits for order confirmation header
        driver.findElement(By.className("module-bankwire-payment")).isDisplayed();
        driver.findElement(By.xpath("//*//button[contains(.,\"I confirm my order\")]")).isDisplayed();
        driver.findElement(By.xpath("//*//button[contains(.,\"I confirm my order\")]")).click();
        wait = new WebDriverWait(driver,30);
        driver.findElement(By.xpath("//*//h1[contains(.,\"Order confirmation\")]")).isDisplayed();
        driver.findElement(By.xpath("//*//h1[contains(.,\"Order confirmation\")]")).click();
    }
    @Then("the User is Returned to the Home Page")
    public void the_user_is_returned_to_the_home_page() throws InterruptedException {
        // Write code here that turns the phrase above into concrete actions
        // This function clicks on the home menu logo and validates the user is returned home then closes and quits the browser session
        driver.findElement(By.xpath("//*//a[contains(@title,\"My Store\")]")).isDisplayed();
        driver.findElement(By.xpath("//*//a[contains(@title,\"My Store\")]")).click();
        wait = new WebDriverWait(driver,30);
        driver.getCurrentUrl().contains("http://automationpractice.com/index.php");
        Actions actions = new Actions(driver);
        WebElement listItem1 = driver.findElement(By.xpath("//*[@id=\"block_top_menu\"]/ul/li[1]/a"));
        actions.moveToElement(listItem1);
        actions.perform();
        driver.findElement(By.xpath("//*[@id=\"block_top_menu\"][contains(.,\"Women\")]")).isDisplayed();
        driver.findElement(By.xpath("//*[@id=\"block_top_menu\"]/ul/li[1]/ul/li[1]/ul/li[1]/a[contains(.,\"T-shirts\")]")).click();
        wait = new WebDriverWait(driver,10);
        ItemPrice = driver.findElement(By.xpath("//*[@id=\"center_column\"]/h1/span[1][contains(.,\"T-shirts\")]")).getText();
                driver.findElement(By.xpath("//*[@id=\"center_column\"]/h1/span[1][contains(.,\"T-shirts\")]")).isDisplayed();
        driver.close();
        driver.quit();
    }
    private void clickCheckout() throws InterruptedException {
        // This function is a helper function that is called to click the Proceed to checkout button on the cart pop-up
        wait = new WebDriverWait(driver,30);
        driver.findElement(By.xpath("//*//a[contains(.,\"Proceed to checkout\")]")).isDisplayed();
        driver.findElement(By.xpath("//*//a[contains(.,\"Proceed to checkout\")]")).click();
    }
    private void clickSummaryCheckout() throws InterruptedException {
        // This function is a helper function that is called to click the Proceed to checkout button on the checkout summary
        wait = new WebDriverWait(driver,30);
        driver.findElement(By.xpath("//*//p[2]/a[contains(.,\"Proceed to checkout\")]")).isDisplayed();
        driver.findElement(By.xpath("//*//p[2]/a[contains(.,\"Proceed to checkout\")]")).click();
    }
    private void clickAddressCheckout() throws InterruptedException {
        // This function is a helper function that is called to click the Proceed to checkout button on the Address summary
        wait = new WebDriverWait(driver,30);
        driver.findElement(By.xpath("//*//p/button[contains(.,\"Proceed to checkout\")]")).isDisplayed();
        driver.findElement(By.xpath("//*//p/button[contains(.,\"Proceed to checkout\")]")).click();
    }

}
