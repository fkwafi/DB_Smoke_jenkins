package com.cydeo.steps;

import com.cydeo.utility.DB_Util;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import java.util.List;

public class US1_Farag_stepDefs {


    //   Scenario: verify users has unique IDs ////////////////////////////////////////////////////////////////////////
      String countIdsUsers;
     String countUniqueIdsUsers;
    @When("Execute query to get all IDs from users")
    public void execute_query_to_get_all_i_ds_from_users() {

        String query="select count(id) from users";

        //run query to get  all IDs count from users
        DB_Util.runQuery(query);

        //store data
        countIdsUsers = DB_Util.getFirstRowFirstColumn();
        System.out.println("countIdsUsers = " + countIdsUsers);
    }


    @Then("verify all users has unique ID")
    public void verify_all_users_has_unique_id() {

        String query1="select count(distinct id) from users";

        //run query to get  all unique IDs count from users
        DB_Util.runQuery(query1);
        //store data
        countUniqueIdsUsers =DB_Util.getCellValue(1,1);
        System.out.println(countUniqueIdsUsers);
        // Assertions
        Assert.assertEquals(countIdsUsers,countUniqueIdsUsers);
    }


  // Scenario: verify users table columns /////////////////////////////////////////////////////////////////////////////

    List<String> actualColumnNameLst;

    @When("Execute query to get all columns")
    public void execute_query_to_get_all_columns() {
        //run query
        String query ="select * from users";
        DB_Util.runQuery(query);
        actualColumnNameLst = DB_Util.getAllColumnNamesAsList();
        System.out.println(actualColumnNameLst);
    }

    @Then("verify the below columns are listed in result")
    public void verify_the_below_columns_are_listed_in_result(List<String> expectedUsersTableColumns) {
        // Assertions
        Assert.assertEquals(expectedUsersTableColumns, actualColumnNameLst);
    }

}