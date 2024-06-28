package com.indusnet.ums.service;

import com.indusnet.ums.common.ResponseModel;
import com.indusnet.ums.dto.UserModelDto;
import com.indusnet.ums.model.ChangePassword;
import com.indusnet.ums.model.UserModel;

public interface IUserService {
	ResponseModel register(UserModel model);
	ResponseModel login(UserModel model);
	ResponseModel updateProfile(UserModel model);
	ResponseModel changePassword(ChangePassword changePassword);
	ResponseModel forgotPassword(UserModelDto userModelDto);
	boolean sendOtpEmail(String email);
}
