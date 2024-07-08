package com.sottie.app.certification.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.sottie.app.certification.model.Certification;
import com.sottie.app.user.error.UserErrorCode;
import com.sottie.app.user.model.User;
import com.sottie.app.user.repository.UserRepository;
import com.sottie.errors.CommonException;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CertificationService {

    private final UserRepository userRepository;

    @Value("4000534808510486")
    private String impKey;

    @Value("lV62ML1sZknl8MZrIYIGaHLMNYJCCGxgcO8sRgRXZUVEnb4VohTDNfuaH0WAbfCS2RiwroCKTam50bbv")
    private String impSecret;

    public String getToken() throws Exception {

        HttpsURLConnection conn = null;
        URL url = new URL("https://api.iamport.kr/users/getToken");

        conn = (HttpsURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        JsonObject json = new JsonObject();

        json.addProperty("imp_key", impKey);
        json.addProperty("imp_secret", impSecret);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

        bw.write(json.toString());
        bw.flush();
        bw.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

        Gson gson = new Gson();

        String response = gson.fromJson(br.readLine(), Map.class).get("response").toString();


        String token = gson.fromJson(response, Map.class).get("access_token").toString();

        br.close();
        conn.disconnect();

        return token;
    }

    public Certification getCertificationsInfo(String certifications, String accessToken) throws IOException, ParseException {
        HttpsURLConnection conn = null;

        JSONParser jsonParser = new JSONParser();

        Object obj = null;
        try {
            obj = jsonParser.parse(certifications);
        } catch (Exception e) {
        }

        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String,Object>>() {};

        Map<String, Object> returnData =  objectMapper.readValue(certifications, typeReference);

        URL url = new URL("https://api.iamport.kr/certifications/" + returnData.get("imp_uid"));

        conn = (HttpsURLConnection) url.openConnection();

        conn.setDoOutput(false);
        conn.setDoInput(true);
        conn.setInstanceFollowRedirects(false);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("charset", "utf-8");
        conn.setRequestProperty("Authorization", accessToken);
        conn.setUseCaches (false);

        int responseCode = conn.getResponseCode();
        System.out.println(responseCode);

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

        JSONParser parser = new JSONParser();

        JSONObject jsonCertification = (JSONObject) parser.parse(br.readLine());

        String response = jsonCertification.get("response").toString();

        jsonCertification = (JSONObject) parser.parse(response);

        Certification certification = null;
        if (jsonCertification.get("certified").equals(true)) {
            certification = new Certification(
                    (String) jsonCertification.get("name"),
                    null,
                    (String) jsonCertification.get("phone"),
                    null,
                    (String) jsonCertification.get("birthday"),
                    true
            );

            // TODO User 데이터 save 는 userService 쪽에서!
//            User user = certification.toUser();
//            userRepository.save(user);

        } else {
            throw CommonException.builder(UserErrorCode.USER_UNAUTHORIZED).build();
        }
        return certification;
    }
}

