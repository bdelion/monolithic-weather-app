/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fifiz.training.java.monolithic_weather_app.owm;

/**
 *
 * @author bertrand
 */
public class TechnicalException extends RuntimeException{

    private static final long serialVersionUID = 8454379425059984991L;

    public TechnicalException(String message) {
        super(message);
    }

    public TechnicalException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
