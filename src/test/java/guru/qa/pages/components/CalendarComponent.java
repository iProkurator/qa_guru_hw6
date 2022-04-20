package guru.qa.pages.components;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.$;

public class CalendarComponent {

    public void setDate(Date date) {

        String[] suffixes =
                //    0     1     2     3     4     5     6     7     8     9
                {"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
                        //    10    11    12    13    14    15    16    17    18    19
                        "th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
                        //    20    21    22    23    24    25    26    27    28    29
                        "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
                        //    30    31
                        "th", "st" };

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DateFormat dateFormatMonthOnly = new SimpleDateFormat("MMMM", Locale.ENGLISH);
        String  dayStr = day + suffixes[day],
                longMonthStr = dateFormatMonthOnly.format(date),
                yearStr = String.valueOf(cal.get(Calendar.YEAR));
        String dateLocator = longMonthStr + " " + dayStr + ", " + yearStr;

        $(".react-datepicker__month-select").selectOption(longMonthStr);
        $(".react-datepicker__year-select").selectOption(yearStr);
        $("[aria-label$='" + dateLocator + "']").click();
    }
}
