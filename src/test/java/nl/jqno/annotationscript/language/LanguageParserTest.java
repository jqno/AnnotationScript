package nl.jqno.annotationscript.language;

import io.vavr.collection.List;
import nl.jqno.annotationscript.abstract_test.ParserTest;
import nl.jqno.annotationscript.language.exceptions.ParseException;

public class LanguageParserTest extends ParserTest {

    @Override
    public Object parse(String... input) {
        var sut = new Parser(List.of(input));
        return sut.parse();
    }

    @Override
    public Class<? extends Throwable> exception() {
        return ParseException.class;
    }
}
