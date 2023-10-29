package com.xtx.reggie.controller;


import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xtx.reggie.common.R;
import com.xtx.reggie.entity.User;
import com.xtx.reggie.service.UserService;
import com.xtx.reggie.utils.SMSUtils;
import com.xtx.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author xtx
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        // 获取手机号
        String phone = user.getPhone();
        if (!StringUtils.isEmpty(phone)) {
            // 生成随机验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("验证码为：{}", code);
            // 发送短信
            //SMSUtils.sendMessage("瑞吉外卖", "", phone, code);
            // 需要将生成的验证码保存到session
            session.setAttribute(phone, code);
            return R.success("手机验证码发送成功");
        }
        return R.error("手机验证码发送失败");
    }

    /**
     * 移动端用户登录
     *
     * @param map
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session) {
        log.info(map.toString());
        // 获取手机号
        String phone = (String) map.get("phone");
        if (!StringUtils.isEmpty(phone)) {
            // 获取验证码
            String code = (String) map.get("code");
            log.info("验证码为：{}", code);
            // 从session中获取验证码
            String sessionCode = (String) session.getAttribute(phone);
            if (code != null && code.equals(sessionCode)) {
                LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(User::getPhone, phone);
                User user = userService.getOne(queryWrapper);
                if (user == null) {
                    user = new User();
                    user.setPhone(phone);
                    userService.save(user);
                }
                session.setAttribute("user", user.getId());
                return R.success(user);
            }
        }
        return R.error("移动端用户登录失败");
    }
}
