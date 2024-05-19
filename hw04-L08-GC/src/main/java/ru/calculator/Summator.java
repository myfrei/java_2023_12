package ru.calculator;

import java.util.ArrayList;
import java.util.List;

public class Summator {
    private long sum = 0;
    private int prevValue = 0;
    private int prevPrevValue = 0;
    private long sumLastThreeValues = 0;
    private long someValue = 0;
    private final List<Data> listValues = new ArrayList<>();

    // !!! сигнатуру метода менять нельзя
    public void calc(Data data) {
        sum += data.getValue();

        sumLastThreeValues = (long) data.getValue() + prevValue + prevPrevValue;

        prevPrevValue = prevValue;
        prevValue = data.getValue();

        someValue += (sumLastThreeValues * sumLastThreeValues / (data.getValue() + 1) - sum);
        someValue = Math.abs(someValue);

        if (listValues.size() % 6_600_000 == 0) {
            listValues.clear();
        }
        listValues.add(data);
    }

    public long getSum() {
        return sum;
    }

    public int getPrevValue() {
        return prevValue;
    }

    public int getPrevPrevValue() {
        return prevPrevValue;
    }

    public long getSumLastThreeValues() {
        return sumLastThreeValues;
    }

    public long getSomeValue() {
        return someValue;
    }
}
