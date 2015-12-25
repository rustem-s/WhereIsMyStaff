package kz.pio.whereismystuff.web.conversion;

import kz.pio.whereismystuff.domain.Category;
import kz.pio.whereismystuff.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

/**
 * Category objects formatter (for Thymeleaf framework using)
 * @version 20140614
 * @author Rustem S
 */
public class CategoryFormatter implements Formatter<Category> {
    @Autowired
    private CategoryService categoryService;
    public CategoryFormatter() {
        super();
    }
    public Category parse(final String text, final Locale locale) throws ParseException {
        final Long id = Long.valueOf(text);
        return this.categoryService.findById(id);
    }
    public String print(final Category object, final Locale locale) {
        return (object != null ? object.getId().toString() : "");
    }
}
