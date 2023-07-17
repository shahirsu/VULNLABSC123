package com.vulnsc.social;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class User {
    public String id, username, hashedPassword;

    public User(String id, String username, String hashedPassword) {
        this.id = id;
        this.username = username;
        this.hashedPassword = hashedPassword;
    }

    public String token(String secret) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        String jws = Jwts.builder().setSubject(this.username).signWith(key).compact();
        return jws;
    }

    public static void assertAuth(String secret, String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
            Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Unauthorized(e.getMessage());
        }
    }

    public static User fetch(String un) {
        // By Default set User to null
        Statement stmt = null;
        User user = null;
        try {
            Connection cxn = Postgres.connection();
            stmt = cxn.createStatement();
            System.out.println("Opened database successfully");
            //Only static strings are passed to this function as username
            String query = "select * from users where username = '" + un + "' limit 1";
            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                String user_id = rs.getString("user_id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                user = new User(user_id, username, password);
            }
            cxn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            // If user not found in DB, create a new temp user with static values
            user = new User("9999","not_found","pa");
        } finally {
            return user;
        }
    }
}
