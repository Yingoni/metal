package com.bs.metal.controller;

import com.bs.metal.common.vo.ResultVO;
import com.bs.metal.service.UserFileInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @auther Bongo
 * @create 2023/4/17 13:19
 */
@RestController
public class UserFileInfoController {
    @Resource
    private UserFileInfoService userFileInfoService;

    /**
     * 用户和图片进行 关联
     * @param userId 用户id
     * @param fileId 图片 id
     * @return
     */
    @GetMapping("/metal/selectFileId")
    public ResultVO  saveAndUpdateFiles(@RequestParam("userId") Integer userId,@RequestParam("fileId") Integer fileId){
        ResultVO resultVO = userFileInfoService.saveUserFileInfo(userId,fileId);
        return resultVO;

    }

    @GetMapping("/metal/history")
    public ResultVO selectUserFiles(@RequestParam("userId") Integer userId){
        ResultVO resultVO = userFileInfoService.selectFilesByUserId(userId);
        return resultVO;
    }

@GetMapping("/metal/time")
    public ResultVO selectTimes(@RequestParam("fileid") Integer fileid){
        ResultVO resultVO = userFileInfoService.selectFileTimes(fileid);
        return resultVO;

}
}
