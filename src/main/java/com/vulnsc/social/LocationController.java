package com.vulnsc.social;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;

class LocationRequest implements Serializable {
    public String pet;
}

@RestController
@EnableAutoConfiguration
public class LocationController {
    @Value("${app.secret}")
    private String secret;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/location", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    Location getLocations(@RequestHeader(value = "x-auth-token") String token, @RequestBody LocationRequest input) {
        User.assertAuth(secret, token);
        String locations = "";
        try {
            URL url = new URL(Location.getLocation(input.pet));
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            String content = IOUtils.toString(inputStream);
            System.out.println("Getting data from : " + Location.getLocation(input.pet));
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            System.out.println("Found this content: "+content);
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            locations = content;
        } catch(Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage() + input.pet);
        } finally {
            return Location.populate(locations);
        }
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    class BadRequest extends RuntimeException {
        public BadRequest(String exception) {
            super(exception);
        }
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    class ServerError extends RuntimeException {
        public ServerError(String exception) {
            super(exception);
        }
    }
}

