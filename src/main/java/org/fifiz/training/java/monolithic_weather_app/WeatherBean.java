package org.fifiz.training.java.monolithic_weather_app;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.fifiz.training.java.monolithic_weather_app.owm.OwmClient;
import org.fifiz.training.java.monolithic_weather_app.owm.WeatherResult;

@ManagedBean
@SessionScoped
public class WeatherBean {

    private String codePostal = "79000";
    private WeatherResult meteo;

    public String goToResult() {
        // appel au service de météo
        OwmClient owmc = new OwmClient(this.codePostal);
        WeatherResult weather = OwmClient.getWeather();
        return weather.getName();
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }
}
