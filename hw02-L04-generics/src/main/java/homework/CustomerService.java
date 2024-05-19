package homework;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {
    private TreeMap<Customer, String> map = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> firstEntry = map.firstEntry();
        if (firstEntry == null) {
            return null;
        }
        Customer key = firstEntry.getKey();
        return new AbstractMap.SimpleEntry<>(
                new Customer(key.getId(), key.getName(), key.getScores()), firstEntry.getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> higherEntry = map.higherEntry(customer);
        if (higherEntry == null) {
            return null;
        }
        Customer key = higherEntry.getKey();
        return new AbstractMap.SimpleEntry<>(
                new Customer(key.getId(), key.getName(), key.getScores()), higherEntry.getValue());
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }
}
