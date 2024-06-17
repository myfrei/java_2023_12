import ru.example.framework.ProcessingAnnotation;
import ru.example.framework.Statistic;

public class RunTests {
    public static void main(String[] args) {
        System.out.println("--------------------------------");
        System.out.println("Processing started");
        System.out.println("--------------------------------");
        Statistic statistic = new Statistic();
        ProcessingAnnotation.run("object.UserTest", statistic);
        ProcessingAnnotation.run("object.AddressTest", statistic);
        ProcessingAnnotation.run("object.PhoneTest", statistic);
        System.out.println("--------------------------------");
        statistic.printStatistics();
        System.out.println("--------------------------------");
        System.out.println("Processing finished");
        System.out.println("--------------------------------");
    }
}
