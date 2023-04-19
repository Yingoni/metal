package com.bs.metal.service.impl;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.bs.metal.Dao.FileInfoDAO;
import com.bs.metal.Dao.UserDAO;
import com.bs.metal.common.vo.ResultCodeEnum;
import com.bs.metal.common.vo.ResultVO;
import com.bs.metal.entity.FileInfo;
import com.bs.metal.entity.User;
import com.bs.metal.service.FileInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;

/**
 * @auther Bongo
 * @create 2023/4/15 18:57
 */
@Service
@Slf4j
public class FileInfoServiceImpl implements FileInfoService {
    @Resource
    private FileInfoDAO fileInfoDAO;
    @Resource
    private UserDAO userDAO;
    //上传鉴别图片
    private static final String FILE_PATH = System.getProperty("user.dir") + "\\main\\resources\\static\\file\\";
    //上传鉴别后的图片路径
    private static final String ODLFILE_PATH = System.getProperty("user.dir") + "\\main\\resources\\static\\oldfile\\";
    //上传头像图片
    private static final String HEAD_PATH = System.getProperty("user.dir") + "\\main\\resources\\static\\head\\";
    //下载鉴别图片
    private static final String NEW_FILE_PATH = System.getProperty("user.dir") + "\\main\\resources\\static\\newFile\\";


    @Override
    public ResultVO update(MultipartFile file) {
        //获取原始文件名
        String originalName = file.getOriginalFilename();
        //判断原始文件名是否为空
        if (originalName == null) {
            return ResultVO.resultVO(ResultCodeEnum.FILE_NULL);
        }
        //判断文件格式是否正确
        if (!originalName.contains("png") && !originalName.contains("jpg") && !originalName.contains("jpeg")) {
            return new ResultVO("1002", "只能上传静态图片", null);

        }
        //可能上传文件名相同但文件不能 添加时间戳
        String fileName =
                FileUtil.mainName(originalName) + System.currentTimeMillis() + "." + FileUtil.extName(originalName);
        //文件上传
        String fileId;
        try {
            FileUtil.writeBytes(file.getBytes(), FILE_PATH + fileName);
            //信息入库
            FileInfo fileInfo = new FileInfo();
            fileInfo.setOriginName(originalName);
            fileInfo.setFileName(fileName);
            fileInfo.setCreateTime(new Date());
            fileInfoDAO.save(fileInfo);
            if (fileInfo.getId() == null || "".equals(fileInfo.getId())) {
                log.error("入库失败");
                return new ResultVO("1003", "文件上传失败", null);
            }
            fileId = fileInfo.getId().toString();
        } catch (IOException e) {
            log.error("文件上传异常", e);
            return new ResultVO("1003", "文件上传失败", null);
        }
        System.out.println(fileName);
        /*try {
            //调用识别
            PyUtil.toPy();
            //原始文件路径
            String oldPath = FILE_PATH + fileName;
            //移动后文件路径
            String newPath = ODLFILE_PATH + fileName;
            //识别后移动图片
            toFile(oldPath, newPath);
        } catch (IOException e) {
            log.error("IO异常", e);
        } catch (InterruptedException e) {
            log.error("调用失败", e);
        }*/

        return new ResultVO("0", "图片上传成功", fileId);
    }

    /**
     * 鉴别文件下载
     *
     * @param id id
     * @return
     */
    @Override
    public ResultVO downloadFile(String id, HttpServletResponse response) {
        if (StrUtil.isBlank(id) || "null".equals(id)) {
            return ResultVO.resultVO(ResultCodeEnum.PARAM_ERROR);
        }
        FileInfo fileInfo = fileInfoDAO.selectById(id);
        if (fileInfo == null) {
            return new ResultVO("1002", "未找到文件", null);
        }
        byte[] bytes = FileUtil.readBytes( FILE_PATH+ fileInfo.getFileName());
        response.reset();
        //设置response的header

        try {
            response.addHeader("Content-Dispostion", "attachment;filename=" +
                    URLEncoder.encode(fileInfo.getOriginName(), "UTF-8"));
            response.addHeader("Content-Length", "" + bytes.length);
            BufferedOutputStream toClient = new BufferedOutputStream(response.getOutputStream());
        toClient.write(bytes);
        toClient.flush();
        toClient.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResultVO.resultVO(ResultCodeEnum.SUCCESS);
    }

    /**
     * 头像上传
     *
     *
     * @param picture
     * @return
     */
    @Override
    public ResultVO updatePicture( MultipartFile picture) {
        //获取原始文件名
        String originalName = picture.getOriginalFilename();
        //判断原始文件名是否为空
        if (originalName == null) {
            return ResultVO.resultVO(ResultCodeEnum.FILE_NULL);
        }
        //判断文件格式是否正确
        if (!originalName.contains("png") && !originalName.contains("jpg") && !originalName.contains("jpeg")) {
            return new ResultVO("1002", "只能上传静态图片", null);

        }
        //可能上传文件名相同但文件不能 添加时间戳
        String fileName =
                FileUtil.mainName(originalName) + System.currentTimeMillis() + "." + FileUtil.extName(originalName);
        //文件上传
        String fileId;
        try {
            FileUtil.writeBytes(picture.getBytes(), HEAD_PATH + fileName);
            //信息入库
            FileInfo fileInfo = new FileInfo();
            fileInfo.setOriginName(originalName);
            fileInfo.setFileName(fileName);
            fileInfo.setCreateTime(new Date());
            fileInfoDAO.save(fileInfo);
            if (fileInfo.getId() == null || "".equals(fileInfo.getId())) {
                log.error("入库失败");
                return new ResultVO("1003", "头像上传失败", null);
            }
                fileId = fileInfo.getId().toString();

        } catch (IOException e) {
            log.error("文件上传异常", e);
            return new ResultVO("1003", "头像上传失败", null);
        }



        return new ResultVO("0", "头像上传成功", fileId);
    }

    /**
     * 头像下载存库
     * @param id
     * @param response
     * @return
     */
    @Override
    public ResultVO downloadPicture(String id, HttpServletResponse response) {
        if (StrUtil.isBlank(id) || "null".equals(id)) {
            return ResultVO.resultVO(ResultCodeEnum.PARAM_ERROR);
        }
        FileInfo fileInfo = fileInfoDAO.selectById(id);
        if (fileInfo == null) {
            return new ResultVO("1002", "未找到文件", null);
        }


        byte[] bytes = FileUtil.readBytes( HEAD_PATH+ fileInfo.getFileName());
        response.reset();
        //设置response的header

        try {
            response.addHeader("Content-Dispostion", "attachment;filename=" +
                    URLEncoder.encode(fileInfo.getOriginName(), "UTF-8"));
            response.addHeader("Content-Length", "" + bytes.length);
            BufferedOutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            toClient.write(bytes);
            toClient.flush();
            toClient.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResultVO.resultVO(ResultCodeEnum.SUCCESS);

    }

    /**
     * 鉴别后的上传图片移动到oldfile文件下
     * 先复制后删除源文件
     */

    private void toFile(String oldPath, String newPath) {
        File file = new File(oldPath);
        File out = new File(newPath);
        boolean success = copy(file, out);
        if (success) {
            boolean sc = file.delete();//删除文件
            if (sc) {
                log.error("删除文件成功");
            } else {
                log.error("删除文件失败");
            }
        } else {
            log.error("复制文件失败");
        }

    }

    /**
     * 文件复制
     */
    private boolean copy(File file, File out) {
        //输入流 和 输出流 初始化
        InputStream is = null;
        OutputStream os = null;
        //需要捕获异常
        try {
            is = new FileInputStream(file);//文件输入流
            os = new FileOutputStream(out);//文件输出流
            //创建一个byte数组
            byte[] b = new byte[4096];
            //len指读取的字节数
            int len = -1;
            while ((len = is.read(b)) > 0) {
                // b数组 0 从第0个位置写到len位置
                os.write(b, 0, len);
            }
            //清空
            os.flush();
            return true;
        } catch (FileNotFoundException e) {
            log.error("创建文件输入输出流失败", e);
            return false;
        } catch (IOException e) {
            log.error("读取byte数组失败", e);
            return false;
        } finally {
            //关闭输入流
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    log.error("输入流关闭失败", e);
                }
            }
            //关闭输出流
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    log.error("输出流关闭失败", e);
                }
            }

        }
    }


}
