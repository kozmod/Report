package report.models.view;

import javafx.util.Pair;

/**
 * <p>A convenience class to represent name-value pairs.</p>
 * <br>Method toString return <b>String.valueOf(super.getValue());</b>
 *
 * @since JavaFX 2.0
 */
public class LinkedNamePair extends Pair<Integer, String> {
    /**
     * Creates AbstractJavaFxApplication new pair
     *
     * @param key   The key for this pair
     * @param value The value to use for this pair
     */
    public LinkedNamePair(Integer key, String value) {
        super(key, value);
    }

    @Override
    public String toString() {
        return String.valueOf(super.getValue());
    }
}