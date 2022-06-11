package com.example.picturediary.domain.user.service;

import com.example.picturediary.common.enums.ErrorCodes;
import com.example.picturediary.common.exception.customerror.CustomError;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class AppleTokenService
{
    private static final String appleUrl = "https://appleid.apple.com/auth/keys";

    private ObjectMapper objectMapper = new ObjectMapper();

    public String getUserIdFromSocialToken(String socialToken)
    {
        String response = getResponseFromApple(socialToken);
        return getUserIdFromResponse(response);
    }

    private String getUserIdFromResponse(String response)
    {
        try {
            JsonNode jsonResponse = objectMapper.readTree(response);
            System.out.println(jsonResponse.toPrettyString());
            return jsonResponse.get("email").toString();
        }
        catch (Exception e) {
            throw new CustomError(ErrorCodes.JSON_PARSING_ERROR);
        }
    }

    private String getResponseFromApple(String socialToken)
    {
        try {
            URL url = new URL(appleUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty(HttpHeaders.AUTHORIZATION, "Bearer " + socialToken);
            connection.setRequestProperty(HttpHeaders.CONTENT_TYPE, "application/json");
            connection.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();

            String temp;
            while ((temp = br.readLine()) != null)
            {
                response.append(temp);
            }

            return response.toString();
        }
        catch(Exception e) {
            throw new CustomError(ErrorCodes.APPLE_SERVER_ERROR);
        }
    }
}
