package com.csbaic.jd.controller.app;

import com.csbaic.jd.dto.News;
import com.csbaic.jd.dto.Option;
import com.csbaic.jd.service.IOptionService;
import com.csbaic.web.annotation.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/options")
@Api(value = "配置項", tags = "配置項")
@ResponseResult
public class OptionController {

    @Autowired
    private IOptionService optionService;



    /**
     * 獲取配置項
     * @return  配置項列表
     */
    @ApiOperation("獲取配置項")
    @GetMapping("/app")
    public List<Option> getAppOptions(){
        return optionService.getAppOptions();
    }




}
