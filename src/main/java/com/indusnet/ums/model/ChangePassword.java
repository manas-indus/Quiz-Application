package com.indusnet.ums.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePassword {
     private String email;
     private String newPassword;
     private String confirmPassword;
     private String otp;
}
