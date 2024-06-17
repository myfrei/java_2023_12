package object;

import ru.example.framework.annotation.After;
import ru.example.framework.annotation.Before;
import ru.example.framework.annotation.Test;
import ru.example.object.Phone;

public class PhoneTest {
    private Phone phone;

    @Before
    public void setUp() {
        phone = new Phone("+79998887766");
    }

    @Test(hint = "Check Creating Date")
    public void testCheckCreatingDate() {
        if (phone.getCreateDate() == null) {
            throw new RuntimeException("Wrong create date: " + phone.getCreateDate());
        }
    }

    @Test(hint = "Check Phone Number")
    public void testCheckPhoneNumber() {
        if (!phone.getNumber().equals("+79998887766")) {
            throw new RuntimeException("Wrong Phone number: " + phone.getNumber());
        }
    }

    @After
    public void tearDown() {
        phone = null;
    }
}
