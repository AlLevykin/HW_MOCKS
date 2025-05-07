package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import java.util.Objects;

public class GeoServiceImplTests {

    static GeoService service = new GeoServiceImpl();

    @ParameterizedTest
    @CsvSource({ "127.0.0.1, , , , 0",
                 "172.0.32.11, Moscow, RUSSIA, Lenina, 15",
                 "96.44.183.149, New York, USA, 10th Avenue, 32",
                 "172.0.32.12, Moscow, RUSSIA, , 0",
                 "96.44.183.1, New York, USA, , 0"
    })
    void testByIp(ArgumentsAccessor argumentsAccessor) {
        // Arrange
        String ip = argumentsAccessor.getString(0);
        Country country = argumentsAccessor.getString(2) == null ? null :
                Country.valueOf(argumentsAccessor.getString(2));
        Location expected = new Location(argumentsAccessor.getString(1),
                country,
                argumentsAccessor.getString(3),
                argumentsAccessor.getInteger(4));

        // Act
        Location result = service.byIp(ip);

        // Assert
        Assertions.assertTrue(
                Objects.equals(result.getCountry(), expected.getCountry()) &&
                        Objects.equals(result.getCity(), expected.getCity()) &&
                        Objects.equals(result.getStreet(), expected.getStreet()) &&
                        result.getBuiling() == expected.getBuiling()
        );
    }

    @Test
    void testByIpForNullLocation() {
        // Arrange
        String ip = "95.44.183.1";

        // Act
        Location result = service.byIp(ip);

        // Assert
        Assertions.assertNull(result);
    }

}
