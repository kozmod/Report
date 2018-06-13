package report.spring.utils;

public abstract class MathUtils {
    public static  double deductPercent(double value, double percent) {
        return value - (value / 100) * percent;
    }
}
