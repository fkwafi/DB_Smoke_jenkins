package com.cydeo.steps;


import com.cydeo.pages.BasePage;
import com.cydeo.pages.BookPage;
import com.cydeo.pages.DashBoardPage;
import com.cydeo.pages.LoginPage;
import com.cydeo.utility.BrowserUtil;
import com.cydeo.utility.ConfigurationReader;
import com.cydeo.utility.DB_Util;
import com.cydeo.utility.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class US4_Farag_stepDefs {
    LoginPage loginPage =new LoginPage();
    BasePage basePage =new  BasePage();
    DashBoardPage dashBoardPage=new DashBoardPage();

    BookPage bookPage = new BookPage();
    String bookName;


    @Given("I login as a librarian")
    public void i_login_as_a_librarian() {
        BrowserUtil.waitFor(3);

        loginPage.login(ConfigurationReader.getProperty("librarian_username"),
                ConfigurationReader.getProperty("password"));
        BrowserUtil.waitForVisibility(basePage.accountHolderName,15);
        BrowserUtil.waitFor(3);


    }
    @Given("I navigate to {string} page")
    public void i_navigate_to_page(String Books) {

        dashBoardPage.books.click();
        BrowserUtil.waitFor(3);

    }
    @When("I open book {string}.")
    public void i_open_book(String bookName) {
        this.bookName=bookName;
bookPage.search.sendKeys(bookName);
BrowserUtil.waitFor(2);

    }
    @Then("book information must match the Database")
    public void book_information_must_match_the_database() {
        //get data from UI
        BrowserUtil.waitForClickablility(bookPage.editBook(bookName), 5).click();
        BrowserUtil.waitFor(2);

        String actualBookName = bookPage.bookName.getAttribute("value");
        String actualAuthorName = bookPage.author.getAttribute("value");
        String actualISBN = bookPage.isbn.getAttribute("value");
        String actualYear = bookPage.year.getAttribute("value");
        String actualDesc = bookPage.description.getAttribute("value");
        System.out.println("actualBookName = " + actualBookName);
        System.out.println("actualAuthorName = " + actualAuthorName);
        System.out.println("actualISBN = " + actualISBN);
        System.out.println("actualYear = " + actualYear);
        System.out.println("actualDesc = " + actualDesc);

        //get related book info from DB
        String query="select name,author,isbn,description,year from books where name ='"+bookName+"'";

        DB_Util.runQuery(query);

        Map<String, String> rowMap = DB_Util.getRowMap(1);
        System.out.println(rowMap);

        String expectedBookName = rowMap.get("name");
        String expectedAuthorName = rowMap.get("author");
        String expectedISBN = rowMap.get("isbn");
        String expectedDesc = rowMap.get("description");
        String expectedYear = rowMap.get("year");

        //Assertion

        Assert.assertEquals(expectedBookName,actualBookName);
        Assert.assertEquals(expectedAuthorName,actualAuthorName);
        Assert.assertEquals(expectedISBN,actualISBN);
        Assert.assertEquals(expectedDesc,actualDesc);
        Assert.assertEquals(expectedYear,actualYear);
    }



    }




