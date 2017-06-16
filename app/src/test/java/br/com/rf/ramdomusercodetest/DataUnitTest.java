package br.com.rf.ramdomusercodetest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import br.com.rf.ramdomusercodetest.model.User;
import br.com.rf.ramdomusercodetest.model.UserWrapper;
import br.com.rf.ramdomusercodetest.users.UsersPresenter;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class DataUnitTest {

    @Test
    public void noDuplicateUsers() {
        TreeSet<User> users = new TreeSet<>();
        users.add(new User("Rodrigo", "Ferreira", "dilcve@gmail.com", "123456"));
        users.add(new User("Joao", "Silva", "joao@gmail.com", "123456"));
        users.add(new User("Rodrigo", "Ferreira", "dilcve@gmail.com", "123456"));
        users.add(new User("John", "Snow", "jsnow@gmail.com", "123456"));

        assertEquals(3, users.size());
    }

    @Test
    public void showActiveUsers() {
        TreeSet<User> users = new TreeSet<>();
        users.add(new User("Rodrigo", "Ferreira", "dilcve@gmail.com", "123456", true));
        users.add(new User("Joao", "Silva", "joao@gmail.com", "123456"));
        users.add(new User("Roger", "Moore", "rmoore@gmail.com", "123456"));
        users.add(new User("John", "Snow", "jsnow@gmail.com", "123456", true));

        assertEquals(2, UserWrapper.getNoDeletedUsers(users).size());
    }

    @Test
    public void getFiltredUsersByFirstName() {
        List<User> users = new ArrayList<>();
        users.add(new User("Rodrigo", "Ferreira", "dilcve@gmail.com", "123456"));
        users.add(new User("Joao", "Silva", "joao@gmail.com", "123456"));
        users.add(new User("Roger", "Moore", "rmoore@gmail.com", "123456"));
        users.add(new User("John", "Snow", "jsnow@gmail.com", "123456"));

        assertEquals(2, UsersPresenter.getFilteredUsers("ro", users).size());
    }

    @Test
    public void getFiltredUsersByLastName() {
        List<User> users = new ArrayList<>();
        users.add(new User("Rodrigo", "Ferreira", "dilcve@gmail.com", "123456"));
        users.add(new User("Joao", "Silva", "joao@gmail.com", "123456"));
        users.add(new User("Roger", "Moore", "rmoore@gmail.com", "123456"));
        users.add(new User("John", "Snow", "jsnow@gmail.com", "123456"));

        assertEquals(1, UsersPresenter.getFilteredUsers("fe", users).size());
    }

    @Test
    public void getFiltredUsersByEmail() {
        List<User> users = new ArrayList<>();
        users.add(new User("Rodrigo", "Ferreira", "dilcve@gmail.com", "123456"));
        users.add(new User("Joao", "Silva", "joao@gmail.com", "123456"));
        users.add(new User("Roger", "Moore", "rmoore@gmail.com", "123456"));
        users.add(new User("John", "Snow", "jsnow@gmail.com", "123456"));

        assertEquals(1, UsersPresenter.getFilteredUsers("js", users).size());
    }
}