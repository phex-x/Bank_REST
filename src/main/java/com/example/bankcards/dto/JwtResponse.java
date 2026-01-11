package com.example.bankcards.dto;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;

    //getters
    public String getToken() { return this.token; }
    public String getType() { return this.type; }
    public Long getId() { return this.id; }

    //setters
    public void setToken(String token) { this.token = token; }
    public void setType(String type) { this.type = type; }
    public void setId(Long id) { this.id = id; }

    public JwtResponse(String token, String type, Long id) {
        this.token = token;
        this.type = type;
        this.id = id;
    }
}
