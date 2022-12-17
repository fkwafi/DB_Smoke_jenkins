package com.cydeo.steps;

import com.cydeo.pages.BasePage;
import com.cydeo.pages.DashBoardPage;
import com.cydeo.pages.LoginPage;
import com.cydeo.utility.BrowserUtil;
import com.cydeo.utility.ConfigurationReader;
import com.cydeo.utility.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class US2_Farag_stepDefs  extends ObjectPage {

    LoginPage loginPage =new LoginPage();
    BasePage basePage =new  BasePage();
    DashBoardPage dashBoardPage=new DashBoardPage();
    String actualBorrowedBookNum;
    @Given("user login as a librarian")
    public void user_login_as_a_librarian() {
        loginPage.login(ConfigurationReader.getProperty("librarian_username"),
                ConfigurationReader.getProperty("password"));
        BrowserUtil.waitForVisibility(basePage.accountHolderName,15);
    }
    @When("user take borrowed books number")
    public void user_take_borrowed_books_number() {
        actualBorrowedBookNum = dashBoardPage.borrowedBooksNumber.getText();
        System.out.println("actualBorrowedBookNum = "+actualBorrowedBookNum);


    }
    @Then("borrowed books number information must match with DB")
    public void borrowed_books_number_information_must_match_with_db() {
        String query = "select count(is_returned) from book_borrow\n" +
                        "where is_returned =0;";
        //run query to get  all IDs count from users
        DB_Util.runQuery(query);

        //store data
     String   expectedBorrowedBookNum = DB_Util.getFirstRowFirstColumn();
        System.out.println("expectedBorrowedBookNum = " + expectedBorrowedBookNum);
        Assert.assertEquals(expectedBorrowedBookNum,actualBorrowedBookNum);


    }
}
