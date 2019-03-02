package org.fifiz.training.java.monolithic_weather_app;

import java.net.MalformedURLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.fifiz.training.java.monolithic_weather_app.owm.OwmClient;
import org.fifiz.training.java.monolithic_weather_app.owm.WeatherResult;

@ManagedBean
@SessionScoped
public class WeatherBean {

    private String codePostal = "79000";
    private String meteo = "";

    public String goToResult() throws MalformedURLException {
        // appel au service de météo
        OwmClient owmc = new OwmClient(this.codePostal);
        this.meteo = owmc.getWeather().toString();
        return "welcome";
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }
}
