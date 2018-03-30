package report.models.converters.dateStringConverters;


import javafx.util.StringConverter;

import java.time.LocalDate;

public class EpochDayStringConverter extends StringConverter<Number> {

    private final StringConverter<LocalDate> converter;

    /*!******************************************************************************************************************
     *                                                                                                        Constructor
     ********************************************************************************************************************/

    public EpochDayStringConverter(StringConverter<LocalDate> converter) {
        this.converter = converter;
    }

    public EpochDayStringConverter(String pattern) {
        this.converter = new LocalDayStringConverter(pattern);
    }


    public EpochDayStringConverter() {
        this.converter = new LocalDayStringConverter();
    }


    /*!******************************************************************************************************************
     *                                                                                                        Methods
     ********************************************************************************************************************/

    @Override
    public String toString(Number localDate) {
        return converter.toString(
                LocalDate.ofEpochDay(
                        localDate.longValue()
                )
        );


    }

    @Override
    public Number fromString(String dateString) {
        return converter.fromString(dateString).toEpochDay();

    }


}
