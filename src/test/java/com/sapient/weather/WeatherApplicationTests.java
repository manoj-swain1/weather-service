package com.sapient.weather;

import com.sapient.weather.controller.WeatherController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = WeatherController.class)
class WeatherApplicationTests {

    @Test
    void contextLoads() {
    }

}
