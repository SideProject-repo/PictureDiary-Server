package com.example.picturediary.domain.user.service;

import com.example.picturediary.common.enums.ErrorCodes;
import com.example.picturediary.common.exception.customerror.CustomError;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

@Service
public class AppleTokenService
{
    private static final String APPLE_URL = "https://appleid.apple.com/auth/keys";

    private ObjectMapper objectMapper = new ObjectMapper();

    public String getUserIdFromSocialToken(String socialToken)
    {
        authorizeAppleToken(socialToken);
        return getUserIdFromToken(socialToken);
    }

    private String getUserIdFromToken(String token)
    {
        try {
            String[] chunks = token.split("\\.");

            Base64.Decoder decoder = Base64.getUrlDecoder();

            String payload = new String(decoder.decode(chunks[1]));

            JsonNode jsonResponse = objectMapper.readTree(payload);
            String userId = jsonResponse.get("email").toString();

            if (StringUtils.isEmpty(userId))
                throw new CustomError(ErrorCodes.JSON_PARSING_ERROR);

            return userId;
        }
        catch (Exception e) {
            throw new CustomError(ErrorCodes.JSON_PARSING_ERROR);
        }
    }

    private String authorizeAppleToken(String socialToken)
    {
        try {
            URL url = new URL(APPLE_URL);
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
