package com.example.emos.wx.controller.form;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

//接受提交数据
@Data
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class SearchMonthCheckinForm {
    @NonNull
    @Range(min = 2000, max = 3000)
    private Integer year;

    @NotNull
    @Range(min = 1,max = 12)
    private Integer month;
}
