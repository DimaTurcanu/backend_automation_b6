package database;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.DBUtil;

import java.util.List;

public class TestDBUtil {

    @BeforeMethod
    public void setDB(){
        DBUtil.createDBConnection();
    }

    @Test
    public void executeDatabase(){
        List<List<Object>> result = DBUtil.getQueryResultList("SELECT first_name, last_name FROM employees");

//        System.out.println(result);

        for (List<Object> row : result) {
            String firstName = row.get(0).toString();
            String lastName = row.get(1).toString();

            System.out.println(firstName + " " + lastName);
        }
    }

    @Test
    public void executeSingleRow(){
        String query = "SELECT * FROM employees WHERE first_name = 'Steven'";
        List<List<Object>> result = DBUtil.getQueryResultList(query);

        List<Object> row = result.get(0);
        for (int i = 0; i < row.size(); i++) {
            System.out.println(DBUtil.getColumnNames(query).get(i) + ": " + row.get(i));
        }

    }


    @Test
    public void executeSingleColumn(){
        String query = "SELECT first_name FROM employees";
        List<List<Object>> result = DBUtil.getQueryResultList(query);

        for (int i = 0; i < result.size(); i++) {
            for (int j = 0; j < result.get(i).size(); j++) {
                System.out.println((i+1) + "." + result.get(i).get(j));
            }
        }
    }
}
