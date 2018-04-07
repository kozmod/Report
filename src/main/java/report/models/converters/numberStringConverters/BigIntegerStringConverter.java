package report.models.converters.numberStringConverters;

import javafx.util.StringConverter;

import java.math.BigInteger;

public class BigIntegerStringConverter extends StringConverter<BigInteger> {
    @Override
    public BigInteger fromString(String s) {
        return new BigInteger(s);
    }

    @Override
    public String toString(BigInteger i) {
        return i.toString();
    }
}
