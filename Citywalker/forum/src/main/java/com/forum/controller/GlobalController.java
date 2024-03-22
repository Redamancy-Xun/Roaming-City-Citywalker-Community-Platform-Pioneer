package com.forum.controller;

import com.forum.annotation.Auth;
import com.forum.common.Result;
import com.forum.exception.MyException;
import com.forum.controller.request.ContactRequest;
import com.forum.controller.response.SentenceResponse;
import com.forum.service.GlobalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@Validated
@Api("GlobalController")
public class GlobalController {

    @Autowired
    private GlobalService globalService;

    @PostMapping(value = "/uploadImage", produces = "application/json")
    @ApiOperation(value = "上传图片 file:图片文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "图片文件", required = true, paramType = "query", dataType = "MultipartFile")})
    public Result uploadImage(@RequestBody MultipartFile file) {
        try {
            return Result.success(globalService.uploadImage(file));
        } catch (Exception e) {
            if (e instanceof MyException) {
                return Result.result(((MyException) e).getEnumExceptionType());
            }
            return Result.fail(e.getMessage());
        }
    }

    @PostMapping(value = "/uploadImages", produces = "application/json")
    @ApiOperation(value = "上传多个图片 files:图片文件数组")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "files", value = "图片文件数组", required = true, paramType = "query", dataType = "List<MultipartFile>")})
    public Result uploadImages(@RequestBody MultipartFile[] files) {
        try {
            for (MultipartFile file : files){
                globalService.uploadImage(file);
            }
            return Result.success("上传成功");
        } catch (Exception e) {
            if (e instanceof MyException) {
                return Result.result(((MyException) e).getEnumExceptionType());
            }
            return Result.fail(e.getMessage());
        }
    }

    @GetMapping(value = "/oneSentence", produces = "application/json")
    @ApiOperation(value = "获取一言，type:a动画,b漫画,c游戏,d文学,e原创,f来自网络,g其他,h影视,i诗词,j网易云,k哲学,l抖机灵，不传或瞎传随机发送一言")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type",value = "类型", paramType = "query", dataType = "String")})
    public Result getOneSentence(@RequestParam("type") String type){
        try {
            return Result.success(globalService.getOneSentence(type));
        } catch (Exception e) {
            if (e instanceof MyException) {
                return Result.result(((MyException) e).getEnumExceptionType());
            }
            return Result.fail(e.getMessage());
        }
    }

    @Auth
    @PostMapping(value = "/contact" , produces = "application/json")
    @ApiOperation(value = "联系我们（发送邮件给客服）")
    public Result contact(@RequestBody ContactRequest contactRequest){
        try{
            return Result.success(globalService.contact(contactRequest));
        }catch (Exception e){
            if(e instanceof MyException){
                return Result.result(((MyException) e).getEnumExceptionType());
            }
            return Result.fail(e.getMessage());
        }
    }

}
