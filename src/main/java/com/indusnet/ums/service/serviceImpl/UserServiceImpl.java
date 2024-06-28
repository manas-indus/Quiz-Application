package com.indusnet.ums.service.serviceImpl;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

import com.indusnet.ums.dto.UserModelDto;
import com.indusnet.ums.exception.CustomNotFoundException;
import com.indusnet.ums.exception.UnprocessableException;
import com.indusnet.ums.model.ChangePassword;
import com.indusnet.ums.util.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.indusnet.ums.common.ResponseModel;
import com.indusnet.ums.model.UserModel;
import com.indusnet.ums.repository.IUserServiceRepository;
import com.indusnet.ums.service.IUserService;
import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
	@Autowired
	Gson gson;
	
	@Autowired
	IUserServiceRepository repository;

	private final BCryptPasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JWTUtils jwtUtils;
	private final JavaMailSender javaMailSender;

	@Override
	public ResponseModel register(UserModel model) {
		try {
			if (repository.existsByEmail(model.getEmail())) {
				throw new CustomNotFoundException("Email is already in use!");
			}
			ResponseModel responseObj = null;
			String encodedPassword = passwordEncoder.encode(model.getPassword());
			Long currentTimeInMilli = Instant.now().toEpochMilli();

			UserModel saveModel = UserModel.builder()
					.email(model.getEmail())
					.name(model.getName())
					.password(encodedPassword)
					.dateCreated(currentTimeInMilli)
					.build();
			repository.save(saveModel);
			log.info(gson.toJson(saveModel));

			responseObj = ResponseModel.builder()
					.messageEn("User registered Successfully")
					.messageFr("User registered Successfully")
					.messageTypeId(1)
					.statusCode(HttpStatus.OK)
					.build();
			return responseObj;

		} catch (Exception e) {
			throw new UnprocessableException("Unexpected error during user registration");
		}
	}
	public ResponseModel login(UserModel model) {
		ResponseModel responseObj = null;
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(model.getEmail(), model.getPassword()));

			Optional<UserModel> userOptional = repository.findByEmail(model.getEmail());
			if (userOptional.isPresent()) {
			String jwt = jwtUtils.generateToken(model.getEmail());
			responseObj = ResponseModel.builder()
					.messageEn("User login Successfully")
					.messageFr("User login Successfully")
					.Token(jwt)
					.TokenExpirationTime("10min")
					.messageTypeId(1)
					.statusCode(HttpStatus.OK)
					.build();
			return responseObj;
			} else {
				throw new CustomNotFoundException("Invalid credentials");
			}
		} catch (Exception e) {
			throw new UnprocessableException("Unexpected error during user login");
		}
	}
	@Override
	public ResponseModel forgotPassword(UserModelDto userModelDto) {
		try{
			if (!repository.existsByEmail(userModelDto.getEmail())) {
				throw new CustomNotFoundException("invalid Credential");
			}
			if (sendOtpEmail(userModelDto.getEmail())) {
				return ResponseModel.builder()
						.messageEn("OTP is sent for forgot password")
						.messageFr("OTP sent successfully")
						.messageTypeId(1)
						.statusCode(HttpStatus.OK)
						.build();
			} else {
				throw new CustomNotFoundException("Problem occurred while sending OTP");
			}

		} catch (Exception e) {
            throw new UnprocessableException("error occurred");
        }
    }

	@Override
	public boolean sendOtpEmail(String email) {
		try {
			String otp = generateOtp();
			sendEmail(email, "OTP Verification", "Your OTP for registration is: " + otp);

			UserModel userModel = repository.findByEmail(email).orElse(new UserModel());
			userModel.setEmail(email);
			userModel.setOtp(otp);
			userModel.setCreatedTime(new Date(System.currentTimeMillis()));
			userModel.setExpirationTime(new Date(System.currentTimeMillis() + 5 * 60 * 1000)); // 5 minutes expiration
			repository.save(userModel);

			return true;
		} catch (Exception e) {
			log.error("Failed to send OTP email", e);
			return false;
		}
	}
	@Override
	public ResponseModel changePassword(ChangePassword changePassword) {
		try {
			
			String email = changePassword.getEmail();
			
			UserModel userModel = repository.findByEmail(email).orElse(null);
			
			if (userModel == null) {
				throw new CustomNotFoundException("user not found");
			}
			if (userModel.getOtp().equals(changePassword.getOtp()) &&
					changePassword.getNewPassword().equals(changePassword.getConfirmPassword())) {

				userModel.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
				repository.save(userModel);

				return ResponseModel.builder()
						.messageEn("Password reset successfully")
						.messageFr("success")
						.messageTypeId(1)
						.statusCode(HttpStatus.OK)
						.build();
			} else {
				throw new CustomNotFoundException("Invalid OTP or passwords do not match");
			}
		} catch (Exception e) {
			throw new UnprocessableException("error occurred");
		}
	}

	@Override
	public ResponseModel updateProfile(UserModel model) {
		try {
			UserModel existingUser = repository.findByEmail(model.getEmail()).orElse(null);
			if (existingUser == null) {
				throw new CustomNotFoundException("user not found");
			}
				existingUser.setName(model.getName());
				existingUser.setEmail(model.getEmail());
				existingUser.setPassword(passwordEncoder.encode(model.getPassword()));
				repository.save(existingUser);

			return ResponseModel.builder()
					.messageEn("Profile updated successfully")
					.messageFr("Profile updated successfully")
					.messageTypeId(1)
					.statusCode(HttpStatus.OK)
					.build();

			} catch (Exception ex) {
			throw new UnprocessableException("Failed to update profile");
		}
	}
	private String generateOtp() {
		Random random = new Random();
		return String.format("%04d", random.nextInt(10000));
	}

	private void sendEmail(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		javaMailSender.send(message);
	}
}











