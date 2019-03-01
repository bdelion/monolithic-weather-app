/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fifiz.training.java.monolithic_weather_app.owm;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URL;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

import org.junit.Rule;
import org.junit.Test;

/**
 *
 * @author bertrand
 */
public class OwmClientTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(0);
    
    /**
     * Chemin de la ressource de l'api current weather de OWM
     */
    private final static String WEATHER_API_PATH = "/current";

    @Test
    public void testGetWeather_Ok() throws IOException {        
        (new WeatherStub(WEATHER_API_PATH, 200, "owm_weather_bessines_ok.json")).stub();
        
        OwmClient client = new OwmClient(
            new URL(
                "http://localhost:{port}{path}"
                    .replace("{port}", String.valueOf(wireMockRule.port()))
                    .replace("{path}", WEATHER_API_PATH)
            )
        );
        
        WeatherResult weatherResult = client.getWeather();
        assertEquals("Bessines", weatherResult.getName());
        // TODO il faut développer le test
    }
    
    @Test(expected = TechnicalException.class)
    public void testGetWeather_404() throws IOException {        
        (new WeatherStub(WEATHER_API_PATH, 404)).stub();
        
        OwmClient client = new OwmClient(
            new URL(
                "http://localhost:{port}{path}"
                    .replace("{port}", String.valueOf(wireMockRule.port()))
                    .replace("{path}", WEATHER_API_PATH)
            )
        );
        client.getWeather();
    }

}
