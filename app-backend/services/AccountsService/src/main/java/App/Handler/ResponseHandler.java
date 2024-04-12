package App.Handler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("status", status.value());
        map.put("data", responseObj);

        ResponseEntity<Object> response = new ResponseEntity<Object>(map,status);
        log.info(response.getHeaders().toString());
        HttpHeaders headers = new HttpHeaders();
        headers.addAll(response.getHeaders());
        headers.add("Access-Control-Allow-Origin",  "*");
//        response.headers()
        return response;
    }
}