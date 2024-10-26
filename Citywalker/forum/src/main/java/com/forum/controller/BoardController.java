//package com.forum.controller;
//
//import com.xiaowu.common.Result;
//import com.xiaowu.common.exception.CommonException;
//import com.xiaowu.controller.request.CreateBoardParams;
//import com.xiaowu.entity.Board;
//import com.xiaowu.service.BoardService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiImplicitParams;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.constraints.NotNull;
//import java.util.List;
//
//@RestController
//@Slf4j
//@Validated
//@Api("BoardController")
//@RequestMapping("/board")
//public class BoardController {
//
//    @Autowired
//    private BoardService boardService;
//
//    @PostMapping(value = "/add", produces = "application/json")
//    @ApiOperation(value = "添加公告")
//    public Result<Boolean> add(@RequestBody CreateBoardParams params) {
//        try {
//            boardService.add(params);
//            return Result.success(true);
//        } catch (CommonException e) {
//            return Result.result(e.getCommonErrorCode());
//        }
//    }
//
//    @PutMapping(value = "/change", produces = "application/json")
//    @ApiOperation(value = "改公告")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "content", value = "content", required = true, paramType = "query", dataType = "String")
//    })
//    public Result<Boolean> change(@NotNull @RequestParam("id")Long id,
//                                  @NotNull @RequestParam("title")String title,
//                                 @NotNull @RequestParam("content")String content) {
//        try {
//            boardService.change(id,title,content);
//            return Result.success(true);
//        } catch (CommonException e) {
//            return Result.result(e.getCommonErrorCode());
//        }
//    }
//
//    @GetMapping(value = "/getById", produces = "application/json")
//    @ApiOperation(value = "根据id获取公告（好像没啥用")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "id", required = true, paramType = "query", dataType = "Long")
//    })
//    public Result<Board> get(@NotNull @RequestParam("id")Long id) {
//        try {
//            return Result.success(boardService.get(id));
//        } catch (CommonException e) {
//            return Result.result(e.getCommonErrorCode());
//        }
//    }
//
//    @GetMapping(value = "/get", produces = "application/json")
//    @ApiOperation(value = "获取公告列表")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "page", value = "page", required = true, paramType = "query", dataType = "Integer"),
//            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true, paramType = "query", dataType = "Integer")
//    })
//    public Result<List<Board>> getList(@NotNull @RequestParam("page")Integer page,
//                                       @NotNull @RequestParam("pageSize")Integer pageSize) {
//        try {
//            return Result.success(boardService.getList(page,pageSize));
//        } catch (CommonException e) {
//            return Result.result(e.getCommonErrorCode());
//        }
//    }
//
//    @DeleteMapping(value = "/del", produces = "application/json")
//    @ApiOperation(value = "删除公告")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id", value = "id", required = true, paramType = "query", dataType = "Long")
//    })
//    public Result<Boolean> del(@NotNull @RequestParam("id")Long id) {
//        try {
//            boardService.del(id);
//            return Result.success(true);
//        } catch (CommonException e) {
//            return Result.result(e.getCommonErrorCode());
//        }
//    }
//
//}
//
//
//
//
//
//
















