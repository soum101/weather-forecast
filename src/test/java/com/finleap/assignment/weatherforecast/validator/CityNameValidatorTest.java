package com.finleap.assignment.weatherforecast.validator;

import com.finleap.assignment.weatherforecast.exception.WeatherServiceException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Base64Utils;

import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
public class CityNameValidatorTest {

    @Spy
    @InjectMocks
    private CityNameValidator cityNameValidator;

    Random randomGenerator = new Random();

    @Test
    public void whenValidCityNameThenExpectSuccess() {
        Assert.assertTrue(cityNameValidator.validate("pune"));

    }

    @Test(expected = WeatherServiceException.class)
    public void whenCityNameExceedsRangeThenExpectFailure() {
        cityNameValidator.validate(generateRandomString(60));
    }

    @Test(expected = WeatherServiceException.class)
    public void whenCityNameContainsInvalidCharsThenExpectFailure() {
        cityNameValidator.validate("Pune@{}");
    }

    private String generateRandomString(int length) {
        randomGenerator.ints(60).toString();
        byte[] randomBytes = new byte[randomGenerator.nextInt(length)];
        randomGenerator.nextBytes((randomBytes));
        return Base64Utils.encodeToString(randomBytes);
    }
}
