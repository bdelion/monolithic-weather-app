package org.fifiz.training.java.monolithic_weather_app.owm;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Classe client d'appel à openweathermap.
 *
 * @author bertrand
 */
// Open weather map api key :
// appid=8c05dfed7d5d0d8ba3a2bc70b83b227f
public class OwmClient {

    private static final Logger LOG = LogManager.getLogger(OwmClient.class.getName());

    /**
     * URL du serveur.
     */
    private URL ownUrl;

    private ObjectMapper jsonMapper;

    /**
     * Constructor.
     *
     * @author bertrand
     * @throws MalformedURLException
     */
    public OwmClient(URL urlClient) throws MalformedURLException {
        LOG.info("URL this.owmUrl : " + this.getOwnUrl());

        this.ownUrl = urlClient;
        this.jsonMapper = new ObjectMapper();
        // attention à la configuration du mapper
        this.jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        LOG.info("URL this.owmUrl : " + this.getOwnUrl());
    }

    /**
     * Constructor.
     *
     * @author bertrand
     * @throws MalformedURLException
     */
    public OwmClient(String codePostal) throws MalformedURLException {
        String urlApiOwm = "http://api.openweathermap.org/data/2.5/weather?zip={codePostal},fr&APPID=8c05dfed7d5d0d8ba3a2bc70b83b227f";

        LOG.info("STRING this.ownUrl : " + this.getOwnUrl());
        LOG.info("STRING codePostal : " + codePostal);
        LOG.info("STRING urlApiOwm : " + urlApiOwm);
        urlApiOwm = urlApiOwm.replace("{codePostal}", codePostal);
        LOG.info("STRING urlApiOwm.replace : " + urlApiOwm);

        this.ownUrl = new URL(urlApiOwm);
        this.jsonMapper = new ObjectMapper();
        // attention à la configuration du mapper
        this.jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        LOG.info("STRING this.ownUrl : " + this.getOwnUrl());
    }

    /**
     * @author bertrand
     * @return weatherResult d'un appel
     */
    public WeatherResult getWeather() {
        // déclarations de variables locales
        WeatherResult weatherResult = null;
        HttpURLConnection owmConnection = null;

        LOG.info("this.owmUrl : " + this.getOwnUrl());

        // lire le flux et le convertir en objet
        try {
            owmConnection = (HttpURLConnection) ownUrl.openConnection();
            // sortie en erreur si le code retour est KO <>200
            if (owmConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new TechnicalException(
                        "Statut de la réponse invalide (code retour = '" + owmConnection.getResponseCode()
                                + "' / message = '" + owmConnection.getResponseMessage() + "')");
            }
            // pour avoir une sortie structurée du flux : http://json.parser.online.fr/
            weatherResult = this.jsonMapper.readValue(owmConnection.getInputStream(), WeatherResult.class);
        } catch (MalformedURLException ex) {
            throw new TechnicalException("Oups ! Pb sur l'URL", ex);
        } catch (IOException ex) {
            throw new TechnicalException("Oups ! I/O erreur - Impossible de contacter OWN", ex);
        } finally {
            if (owmConnection != null) {
                owmConnection.disconnect();
            }
        }
        return weatherResult;
    }

    public URL getOwnUrl() {
        return ownUrl;
    }

    public void setOwnUrl(URL ownUrl) {
        this.ownUrl = ownUrl;
    }

    public ObjectMapper getJsonMapper() {
        return jsonMapper;
    }

    public void setJsonMapper(ObjectMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

}
