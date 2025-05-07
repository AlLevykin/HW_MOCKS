package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

import java.util.HashMap;
import java.util.Map;

public class MessageSenderImplTests {

    @ParameterizedTest
    @CsvSource({ "172.0.32.11,RUSSIA,Добро пожаловать",
            "181.214.1.149,GERMANY,Welcome",
            "96.44.183.149,USA,Welcome",
            "177.67.136.41,BRAZIL,Welcome",
            "96.44.183.149,USA,Welcome",
            ",USA,Welcome"
    })
    public void testSend(ArgumentsAccessor argumentsAccessor) {
        // Arrange
        String ip = argumentsAccessor.getString(0);
        Country country = argumentsAccessor.getString(1) == null ? null :
                Country.valueOf(argumentsAccessor.getString(1));
        String expected = argumentsAccessor.getString(2);

        GeoService geoServiceMock = Mockito.mock(GeoService.class);
        Mockito.when(geoServiceMock.byIp(ip)).thenReturn(new Location("", country, "", 0));

        LocalizationService localizationServiceMock = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationServiceMock.locale(country)).thenReturn(expected);

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);

        // Act
        MessageSenderImpl messageSender = new MessageSenderImpl(geoServiceMock, localizationServiceMock);
        String result = messageSender.send(headers);

        // Assert
        Assertions.assertEquals(expected, result);
    }
}
