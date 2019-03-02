package org.fifiz.training.java.monolithic_weather_app;

//import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class WeatherBean {

    private String codePostal = "79000";

    public String goToResult() {
        return "welcome";
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }
}
