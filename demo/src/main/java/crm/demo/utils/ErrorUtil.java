package crm.demo.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorUtil {

    public ResponseEntity<Map<String, Object>> goodStatus(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", message);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Map<String, Object>> badStatus(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("message", message);
        return ResponseEntity.badRequest().body(response);
    }

}
