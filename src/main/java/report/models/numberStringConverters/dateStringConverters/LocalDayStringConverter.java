package report.models.numberStringConverters.dateStringConverters;


import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDayStringConverter extends StringConverter<LocalDate>  {

    private final DateTimeFormatter dateFormatter;


    /*!******************************************************************************************************************
    *                                                                                                        Constructor
    ********************************************************************************************************************/

    public LocalDayStringConverter(DateTimeFormatter dateFormatter) {
        this.dateFormatter = dateFormatter;
    }

    public LocalDayStringConverter() {
        this.dateFormatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    public LocalDayStringConverter(String patter) {
        this.dateFormatter  = DateTimeFormatter.ofPattern(patter);
    }


    /*!******************************************************************************************************************
    *                                                                                                        Methods
    ********************************************************************************************************************/

    @Override
    public String toString(LocalDate localDate) {
        String string = "";
        if (localDate != null) {
            string =  dateFormatter.format(localDate);
        }
        return string;

    }

    @Override
    public LocalDate fromString(String doubleString)  {
        return LocalDate.parse(doubleString, dateFormatter);
    }



}
