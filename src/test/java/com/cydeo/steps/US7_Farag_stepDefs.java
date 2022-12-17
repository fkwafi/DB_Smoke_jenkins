package com.cydeo.steps;

import com.cydeo.pages.*;
import com.cydeo.utility.BrowserUtil;
import com.cydeo.utility.ConfigurationReader;
import com.cydeo.utility.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.List;

public class US7_Farag_stepDefs {

    LoginPage loginPage = new LoginPage();
    BasePage basePage = new BasePage();
    BookPage bookPage = new BookPage();


    @Given("I login as a student")
    public void i_login_as_a_student() {
        loginPage.login(ConfigurationReader.getProperty("student_username"),
                ConfigurationReader.getProperty("student_password"));
        BrowserUtil.waitForVisibility(basePage.accountHolderName, 15);
    }
String bookName;
    @Given("I search book name called {string}")
    public void i_search_book_name_called(String bookName) {
        this.bookName=bookName;
        bookPage.search.sendKeys(bookName);
        System.out.println("bookName = " + bookName);
        BrowserUtil.waitFor(3);

    }

    @When("I click Borrow Book")
    public void i_click_borrow_book() {
       bookPage.addBorrowBook.click();
       BrowserUtil.waitFor(3);

    }



    @Then("verify that book is shown in {string} page")
    public void verify_that_book_is_shown_in_page(String borrowBooks) {

          BorrowedBooksPage borrowedBooksPage = new BorrowedBooksPage();
        new DashBoardPage().navigateModule(borrowBooks);

        Assert.assertTrue(BrowserUtil.getElementsText(borrowedBooksPage.allBorrowedBooksName).contains(bookName));


    }

    @Then("verify logged student has same book in database")
    public void verify_logged_student_has_same_book_in_database() {
        String query = "select full_name,b.name,bb.is_returned from users u\n" +
                "                                                  inner join book_borrow bb on u.id = bb.user_id\n" +
                "                                                  inner join books b on bb.book_id = b.id\n" +
                "where full_name='Test Student 4' and is_returned=0 and b.name= 'Mohamed Ali'\n" +
                "order by 3 ;";

        DB_Util.runQuery(query);
        List<String> actualList = DB_Util.getColumnDataAsList(2);
        System.out.println("actualList from DB= " + actualList);
        Assert.assertTrue(actualList.contains(bookName));
    }

}
