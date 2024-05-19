package object;

import ru.example.framework.annotation.After;
import ru.example.framework.annotation.Before;
import ru.example.framework.annotation.Skip;
import ru.example.framework.annotation.Test;
import ru.example.object.Address;
import ru.example.object.Phone;
import ru.example.object.User;

public class UserTest {
    private User user;

    @Before
    public void setUp() {
        user = new User("Ilia");
    }

    @Test(hint = "Check Creating Date")
    public void testCheckCreatingDate() {
        if (user.getCreateDate() == null) {
            throw new RuntimeException("Wrong create date: " + user.getCreateDate());
        }
    }

    @Test(hint = "Check Change Name")
    public void testCheckChangeName() {
        String name = "Kirill";
        user.setName(name);
        if (!user.getName().equals(name)) {
            throw new RuntimeException("Wrong Name: \"" + user.getName() + "\" expected: \"" + name + "\"");
        }
    }

    @Test(hint = "Check Add Address")
    public void testAddAddress() {
        Address address = new Address("Moscow", "Leninsky Prospekt", 123);
        user.setAddress(address);
        if (!user.getAddress().equals(address)) {
            throw new RuntimeException("Wrong Address: " + user.getAddress());
        }
    }

    @Test(hint = "Check Add Phone")
    public void testAddPhone() {
        Phone phone = new Phone("+79998887766");
        user.addPhone(phone);
        if (!user.getPhones().contains(phone)) {
            throw new RuntimeException("Phone not added: " + phone);
        }
    }

    @Skip(reason = "This test is not implemented yet")
    @Test(hint = "Check Remove Phone")
    public void testRemovePhone() {
        // Этот тест пока не реализован
        throw new RuntimeException("This test is not implemented yet");
    }

    @After
    public void tearDown() {
        user = null;
    }
}