package com.example.emos.wx.controller.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

//接收移动端提交的注册请求--用表单类来封装数据
@Data
@ApiModel("RegisterForm 是整个APi中其中的一个Model")
public class RegisterForm {
    @NotBlank(message = "注册码不能为空")
    @Pattern(regexp = "^[0-9]{6}$", message = "注册码必须是6位数字")
    private String registerCode;
    @NotBlank(message = "微信临时授权不能为空")
    private String code;
    @NotBlank(message = "昵称不能为空")
    private String nickname;
    @NotBlank(message = "头像不能为空")
    private String photo;
}
