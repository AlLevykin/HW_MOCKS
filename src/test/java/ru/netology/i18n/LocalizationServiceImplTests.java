package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;
import ru.netology.entity.Country;

public class LocalizationServiceImplTests {

    static LocalizationService localizationService = new LocalizationServiceImpl();

    @ParameterizedTest
    @CsvSource({ "RUSSIA, Добро пожаловать",
            "GERMANY, Welcome",
            "USA, Welcome",
            "BRAZIL, Welcome"
    })
    void testLocale(ArgumentsAccessor argumentsAccessor) {
        Country country = argumentsAccessor.getString(0) == null ? null :
                Country.valueOf(argumentsAccessor.getString(0));
        String expected = argumentsAccessor.getString(1);

        // Act
        String result = localizationService.locale(country);

        // Assert
        Assertions.assertEquals(expected, result);
    }
}
