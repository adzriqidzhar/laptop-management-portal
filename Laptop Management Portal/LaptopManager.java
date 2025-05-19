import java.util.ArrayList;
import java.util.List;

public class LaptopManager {
    private static final List<LaptopDashboard.Laptop> laptops = new ArrayList<>();

    public static void addLaptop(LaptopDashboard.Laptop laptop) {
        laptops.add(laptop);
    }

    public static List<LaptopDashboard.Laptop> getLaptops() {
        return laptops;
    }

    public static void updateLaptop(int index, LaptopDashboard.Laptop laptop) {
        laptops.set(index, laptop);
    }

    public static void deleteLaptop(int index) {
        laptops.remove(index);
    }
}
