package ru.bellintegrator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.bellintegrator.exceptionHandler.WeatherException;
import ru.bellintegrator.view.WeatherInfoView;

import javax.annotation.Resource;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Сервис погоды
 */
@ApplicationScoped
public class YahooServiceImpl implements YahooService {

    private final Logger log = LoggerFactory.getLogger(YahooServiceImpl.class);

    @Resource(mappedName = "java:/jms/queue/dbQueue")
    private Queue queue;

    @Inject
    private JMSContext context;

    /**
     * Получить данные о погоде с сервиса Yahoo.
     *
     * Запрос отправляется в зашифрованном виде
     * @param city город
     * @param region регион
     */
    @Override
    public void getWeather(String city, String region) {
        HttpEntity<String> entity = encryptHeaders(city, region);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<WeatherInfoView> result = restTemplate.exchange(
                "https://weather-ydn-yql.media.yahoo.com/forecastrss?location=" + city + "," + region + "&format=json&u=c",
                HttpMethod.GET, entity, WeatherInfoView.class);

        if(result.getStatusCodeValue() != 200) {
            throw new WeatherException("Service is temporarily unavailable. Try later");
        }

        WeatherInfoView weatherInfo = result.getBody();
        if(weatherInfo == null || weatherInfo.getLocation() == null || weatherInfo.getLocation().getWoeid() == null) {
            throw new WeatherException("Wrong city or region! Try again");
        }
        if(weatherInfo.getCurrentObservation() == null || weatherInfo.getCurrentObservation().getPubDate() == null ||
                weatherInfo.getForecasts() == null || weatherInfo.getForecasts().isEmpty() ||
                weatherInfo.getForecasts().get(0) == null) {
            throw new WeatherException("Service is temporarily unavailable. Try later");
        }

        sendMessage(weatherInfo);
    }

    /**
     * Отправить сообщение в очередь
     *
     * @param weatherInfo
     */
    private void sendMessage(WeatherInfoView weatherInfo) {
        context.createProducer().send(queue, weatherInfo);
        log.info("Message has been sent to dbQueue");
    }

    /**
     * Сформировать и зашифровать заголовки для отправки на Yahoo
     *
     * Для этого нужно предварительно получить следующие ключи для Вашего API:
     * App ID
     * Client ID (Consumer Key)
     * Client Secret (Consumer Secret)
     *
     * @param city город
     * @param region регион
     * @return объект HTTP-запроса с зашифрованными данными
     */
    private HttpEntity<String> encryptHeaders(String city, String region) {

        final String appId = "test-app-id";
        final String consumerKey = "your-consumer-key";
        final String consumerSecret = "your-consumer-secret";

        final String url = "https://weather-ydn-yql.media.yahoo.com/forecastrss";

        long timestamp = new Date().getTime() / 1000;
        byte[] nonce = new byte[32];
        Random rand = new Random();
        rand.nextBytes(nonce);
        String oauthNonce = new String(nonce).replaceAll("\\W", "");

        List<String> parameters = new ArrayList<>();
        parameters.add("oauth_consumer_key=" + consumerKey);
        parameters.add("oauth_nonce=" + oauthNonce);
        parameters.add("oauth_signature_method=HMAC-SHA1");
        parameters.add("oauth_timestamp=" + timestamp);
        parameters.add("oauth_version=1.0");
        try {
            parameters.add("location=" + URLEncoder.encode(city + "," + region, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("Error encoding parameter {}", e.getMessage(), e);
        }
        parameters.add("format=json");
        parameters.add("u=c");
        Collections.sort(parameters);

        StringBuffer parametersList = new StringBuffer();
        for (int i = 0; i < parameters.size(); i++) {
            parametersList.append(((i > 0) ? "&" : "") + parameters.get(i));
        }

        String signatureString = "";
        try {
            signatureString = "GET&" +
                    URLEncoder.encode(url, "UTF-8") + "&" +
                    URLEncoder.encode(parametersList.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("Error encoding parameter {}", e.getMessage(), e);
        }

        String signature = null;
        try {
            SecretKeySpec signingKey = new SecretKeySpec((consumerSecret + "&").getBytes(), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] rawHMAC = mac.doFinal(signatureString.getBytes());
            Base64.Encoder encoder = Base64.getEncoder();
            signature = encoder.encodeToString(rawHMAC);
        } catch (Exception e) {
            System.err.println("Unable to append signature");
            System.exit(0);
        }

        String authorizationLine = "OAuth " +
                "oauth_consumer_key=\"" + consumerKey + "\", " +
                "oauth_nonce=\"" + oauthNonce + "\", " +
                "oauth_timestamp=\"" + timestamp + "\", " +
                "oauth_signature_method=\"HMAC-SHA1\", " +
                "oauth_signature=\"" + signature + "\", " +
                "oauth_version=\"1.0\"";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authorizationLine);
        headers.add("Yahoo-App-Id", appId);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return new HttpEntity<>("parameters", headers);
    }
}
