package com.bs.metal.controller;

import com.bs.metal.common.vo.ResultVO;
import com.bs.metal.service.FileInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件上传
 *
 * @auther Bongo
 * @create 2023/4/15 20:42
 */
@RestController
@Slf4j
@RequestMapping("/file")
public class FileInfoController {
    @Resource
    private FileInfoService fileInfoService;

    /**
     * 鉴别图片上传
     * @param file
     * @return
     */

    @PostMapping("/update")
    public ResultVO update(@RequestParam("image")MultipartFile file){
        ResultVO resultVO = fileInfoService.update(file);
        return resultVO;

    }

    /**
     * 头像上传
     */

    @PostMapping("/update/picture")
    public ResultVO updatePicture(
                                  @RequestParam("picture")MultipartFile picture){
        ResultVO resultVO = fileInfoService.updatePicture(picture);
        return resultVO;}
    /**
     * 下载文件
     */

    @GetMapping("/download/{id}")
    public ResultVO  download(@PathVariable String id, HttpServletResponse response){
      ResultVO resultVO =  fileInfoService.downloadFile(id,response);
      return resultVO;
    }

    /**
     * 头像下载
     * @param id
     * @param response
     * @return
     */

    @GetMapping("/downloadPicture/{id}")
    public ResultVO  downloadPicture(@PathVariable String id, HttpServletResponse response){
        ResultVO resultVO =  fileInfoService.downloadPicture(id,response);
        return resultVO;
    }
}
