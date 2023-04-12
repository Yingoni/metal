package com.bs.metal.Interception;

import com.bs.metal.common.vo.ResultCodeEnum;
import com.bs.metal.common.vo.ResultVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/** token 拦截器
 * @auther Bongo
 * @create 2023/4/12 22:23
 */
@Slf4j
@Component
public class CheckTokenInterceptor implements HandlerInterceptor {
    //要在处理请求之前 所以在拦截器拦截前
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
            //从请求参数中获取token
        String token = request.getParameter("token");
        if (token ==null){
            //提示先登录
            doResponse(response,ResultVO.resultVO(ResultCodeEnum.USER_NO_LOGIN));
        }else {
            try {
            //验证token
            JwtParser parser = Jwts.parser();
            //解析token的SigningKey必须和生成token时设置密码一致
            parser.setSigningKey("Xyy123456");
           //如果token正确（密码  有效期内）则正常执行，否则抛出异常

                Jws<Claims> claimsJws = parser.parseClaimsJws(token);
            } catch (ExpiredJwtException e) {
                log.error("登录过期",e);
                doResponse(response,ResultVO.resultVO(ResultCodeEnum.USER_NOTIME_LOGIN));
            } catch (UnsupportedJwtException e) {
                log.error("token过期",e);
                doResponse(response,ResultVO.resultVO(ResultCodeEnum.TOKEN_NO));
            } catch (Exception e) {
                log.error("请先登录",e);
                doResponse(response,ResultVO.resultVO(ResultCodeEnum.USER_NO_LOGIN));
            }
            return true;

        }

        return HandlerInterceptor.super.preHandle(request,response,handler);
    }
    //响应数据
    private void doResponse(HttpServletResponse response, ResultVO resultVO) throws IOException {
        response.setContentType("application/json");//响应JSON字符串
        response.setCharacterEncoding("utf-8");//编码格式
        PrintWriter writer = response.getWriter(); //字符流
        String s = new ObjectMapper().writeValueAsString(resultVO);//转成字符串
        writer.print(s);//传入前端
        writer.flush();//关闭刷新流
        writer.close(); //关闭
    }
}

