package com.bs.metal.service.impl;

import cn.hutool.core.util.StrUtil;
import com.bs.metal.Dao.UserFileInfoDAO;
import com.bs.metal.common.vo.ResultCodeEnum;
import com.bs.metal.common.vo.ResultVO;
import com.bs.metal.entity.UserFileInfo;
import com.bs.metal.service.UserFileInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @auther Bongo
 * @create 2023/4/17 13:13
 */
@Service
@Slf4j
public class UserFileInfoServiceImpl implements UserFileInfoService {
    @Resource
    private UserFileInfoDAO userFileInfoDAO;

    /**
     * 更新或保存
     * @param userId  userid
     * @param fileId fuleid
     * @return
     */
    @Override
    public ResultVO saveUserFileInfo(Integer userId, Integer fileId) {
        //先去数据库查询看是否有值
        UserFileInfo userFileInfo = userFileInfoDAO.selectByUserId(userId);
        List<Long> list = new ArrayList<>();
        if (userFileInfo == null) {
            UserFileInfo ufi = new UserFileInfo();
            ufi.setUserId(userId);
            ufi.setFiles(fileId.toString());
            list.add(Long.valueOf(fileId));
            int row = userFileInfoDAO.saveUserFileInfo(ufi);
            if (!(row > 0)) {
                return ResultVO.resultVO(ResultCodeEnum.ERROR);
            }
        } else {
            String fi = userFileInfo.getFiles();
            String substring = fi.substring(1, fi.length()-1);
            String[] files = substring.split(", ");
            for (int i = 0; i < files.length; i++) {
                list.add(Long.valueOf(files[i]));
            }
            list.add(Long.valueOf(fileId));
            userFileInfo.setFiles(list.toString());
            int rows = userFileInfoDAO.updateByUserId(userFileInfo);
            if (!(rows > 0)) {
                return ResultVO.resultVO(ResultCodeEnum.ERROR);
            }
        }

        return ResultVO.resultVO(ResultCodeEnum.SUCCESS);
    }

    /**
     * 根据用户id 查询全部图片
     * @param userId
     * @return
     */
    @Override
    public ResultVO selectFilesByUserId(Integer userId) {
        if (userId==null ){
            return ResultVO.resultVO(ResultCodeEnum.USER_NO_LOGIN);
        }else {
            String file = userFileInfoDAO.selectFilesByUserId(userId);
            if (!StrUtil.isBlank(file)){
                List<Long> list = new ArrayList<>();
                String substring = file.substring(1, file.length()-1);
                String[] files = substring.split(", ");
                for (int i = 0; i < files.length; i++) {
                    list.add(Long.valueOf(files[i]));
                }
                return ResultVO.resultVO(ResultCodeEnum.SUCCESS,list);
            }else{
                return new ResultVO("3001","未识别过图片",null);
            }
        }

    }

    /**
     * 查取文件检测时间
     * @param fileid
     * @return
     */
    @Override
    public ResultVO selectFileTimes(Integer fileid) {
        if (fileid ==null ){
            return new ResultVO("3001","未识别过图片",null);
        }else {
            Date time = userFileInfoDAO.selectFilesTime(fileid);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String times = sdf.format(time);
            if (!StrUtil.isBlank(times)){
                return ResultVO.resultVO(ResultCodeEnum.SUCCESS,times);
            }else{
                return new ResultVO("3003","为找到匹配的时间",null);
            }
        }
    }
}
