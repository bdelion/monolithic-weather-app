package org.fifiz.training.java.monolithic_weather_app;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.Scanner;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.fifiz.training.java.monolithic_weather_app.owm.OwmClient;
import org.fifiz.training.java.monolithic_weather_app.owm.TechnicalException;
import org.fifiz.training.java.monolithic_weather_app.owm.WeatherResult;

@ManagedBean
@SessionScoped
public class WeatherBean {
    private static final Logger MYLOGGER = LogManager.getLogger(WeatherBean.class);
    private static final Double DELTATEMP = -273.15;
    private static final String FORMATTEMP = "%.2f °C";

    private String codePostal = "79000";
    private String result = "Waiting";

    public String goToResult() throws MalformedURLException {
        MYLOGGER.info("Start");
        MYLOGGER.info("this.codePostal : " + this.codePostal);
        try {
            OwmClient owmc = new OwmClient(this.codePostal);

            WeatherResult cpWeather = owmc.getWeather();
            String msgTmpl = MessageTemplateReaderUtils.read();
            MYLOGGER.info("msgTmpl : " + msgTmpl);

            if (!"".equals(msgTmpl)) {
                this.result = (msgTmpl.replace("{city}", cpWeather.getName()).replace("{cp}", codePostal)
                        .replace("{country}", cpWeather.getSys().getCountry())
                        .replace("{base}",cpWeather.getBase())
                        .replace("{lon}", String.valueOf(cpWeather.getCoord().getLon()))
                        .replace("{lat}", String.valueOf(cpWeather.getCoord().getLat()))
                        .replace("{weather.main}", cpWeather.getWeather().get(0).getMain())
                        .replace("{weather.description}", cpWeather.getWeather().get(0).getDescription())
                        .replace("{temp}", String.format(FORMATTEMP, cpWeather.getMain().getTemp() + DELTATEMP))
                        .replace("{temp-min}", String.format(FORMATTEMP, cpWeather.getMain().getTemp_min() + DELTATEMP))
                        .replace("{temp-max}", String.format(FORMATTEMP, cpWeather.getMain().getTemp_max() + DELTATEMP))
                        .replace("{humid}", String.format("%.2f", cpWeather.getMain().getHumidity()))
                        .replace("{wind}", String.format("%.2f m/sec", cpWeather.getWind().getSpeed())));
            } else {
                this.result = "Template vide !";
            }
        } catch (TechnicalException te) {
            te.printStackTrace(System.err);
        } finally {
            // TODEL sc.close();
        }
        return "welcome";
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
