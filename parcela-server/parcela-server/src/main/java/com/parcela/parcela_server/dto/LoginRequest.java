package com.parcela.parcela_server.dto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
public class LoginRequest {
    private Long customerId;
    private String password;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
