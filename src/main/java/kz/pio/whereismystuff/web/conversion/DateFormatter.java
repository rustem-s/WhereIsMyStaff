package kz.pio.whereismystuff.web.conversion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Date objects formatter (for Thymeleaf framework using)
 * @version 20140614
 * @author Rustem S
 */
public class DateFormatter implements Formatter<Date> {
    @Autowired
    private MessageSource messageSource;

    public DateFormatter() {
        super();
    }
    public Date parse(final String text, final Locale locale) throws ParseException {
        final SimpleDateFormat dateFormat = createDateFormat(locale);
        return dateFormat.parse(text);
    }
    public String print(final Date object, Locale locale) {
        locale = new Locale("en", "US");
        final SimpleDateFormat dateFormat = createDateFormat(locale);
        return dateFormat.format(object);
    }
    private SimpleDateFormat createDateFormat(final Locale locale) {
        final String format = this.messageSource.getMessage("date.format", null, locale);
        final SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false);
        return dateFormat;
    }
}