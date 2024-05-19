package object;

import ru.example.framework.annotation.After;
import ru.example.framework.annotation.Before;
import ru.example.framework.annotation.Test;
import ru.example.object.Address;

public class AddressTest {
    private Address address;

    @Before
    public void setUp() {
        address = new Address("Moscow", "Leninsky Prospekt", 123);
    }

    @Test(hint = "Check Creating Date")
    public void testCheckCreatingDate() {
        if (address.getCreateDate() == null) {
            throw new RuntimeException("Wrong create date: " + address.getCreateDate());
        }
    }

    @Test(hint = "Check Address Fields")
    public void testCheckAddressFields() {
        if (!address.getCity().equals("Moscow") || !address.getStreet().equals("Leninsky Prospekt") || address.getHouseNumber() != 123) {
            throw new RuntimeException("Wrong Address fields: " + address);
        }
    }

    @After
    public void tearDown() {
        address = null;
    }
}