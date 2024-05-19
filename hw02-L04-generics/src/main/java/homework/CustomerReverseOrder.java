package homework;

import java.util.ArrayDeque;
import java.util.Deque;

public class CustomerReverseOrder {
    private Deque<Customer> customers = new ArrayDeque<>();

    public void add(Customer customer) {
        customers.addFirst(customer);
    }

    public Customer take() {
        return customers.pollFirst();
    }
}
