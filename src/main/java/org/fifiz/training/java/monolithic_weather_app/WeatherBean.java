package org.fifiz.training.java.monolithic_weather_app;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "weatherBean")
@SessionScoped
public class WeatherBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String codePostal;

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
