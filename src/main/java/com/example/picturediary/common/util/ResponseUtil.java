package com.example.picturediary.common.util;

import com.example.picturediary.common.enums.ErrorCodes;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@UtilityClass
public class ResponseUtil
{
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void doResponse(HttpServletResponse response, ErrorCodes errorCodes) throws IOException
    {
        // json response 를 직접 만들어서 전달
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        String jsonStr = objectMapper.writeValueAsString(errorCodes);

        PrintWriter out = response.getWriter();
        out.print(jsonStr);
        out.flush();
        out.close();
    }
}
