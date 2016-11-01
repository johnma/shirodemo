package com.iware.webshiro.controller.api;


import com.iware.webshiro.common.ResultParamError;
import com.iware.webshiro.controller.IdentityController;
import com.iware.webshiro.domain.Identity;
import com.iware.webshiro.enums.EnumCode;
import com.iware.webshiro.enums.ResponeVo;
import com.iware.webshiro.form.LoginForm;
import com.iware.webshiro.form.RegistrationForm;
import com.iware.webshiro.service.IdentityService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

/**
 * Created by Mahone  on 2016/10/31.
 * @description:使用了@RestController就不需要使用@ResponseBody，会直接以json格式返回数据，适合在全部返回接口的类里面使用
 */
@RestController
@RequestMapping("/api/identity")
public class ApiIdentityController {

    @Autowired
    private IdentityService identityService;

    private static final Logger logger = LoggerFactory.getLogger(IdentityController.class);

    /**
     * @description:注册接口
     * @param registration
     * @param result
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value ="/register")
    public ResponeVo registerTest(@RequestBody @Valid RegistrationForm registration, BindingResult result, Model model) {
        logger.info("Entering Register");
        if(logger.isDebugEnabled()){
            logger.info("registration---->"+registration);
        }
        String message = ResultParamError.dealCommonValidateError(result);
        if(StringUtils.isNotBlank(message)){
            return new ResponeVo(EnumCode.SYSTEM_PARAM_ERROR_CODE.getCode(),EnumCode.SYSTEM_PARAM_ERROR_CODE.getMessage(),message);
        }
        Identity identity = this.identityService.registerIdentity(registration);
        if(null ==identity)
            return  new ResponeVo<Identity>(EnumCode.SYSTEM_FAIL_CODE.getCode(),EnumCode.SYSTEM_FAIL_CODE.getMessage(),identity);
        return  new ResponeVo<Identity>(EnumCode.SYSTEM_SUCCESS_CODE.getCode(),EnumCode.SYSTEM_SUCCESS_CODE.getMessage(),identity);
    }

    /**
     * @description:授权登陆
     * @param loginForm
     * @param result
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = { "/authenticate" })
    public ResponeVo register(@RequestBody @Valid LoginForm loginForm, BindingResult result, Model model) {
        logger.info("Entering Authenticate");
        String message = ResultParamError.dealCommonValidateError(result);
        if(StringUtils.isNotBlank(message)){
            return new ResponeVo(EnumCode.SYSTEM_PARAM_ERROR_CODE.getCode(),EnumCode.SYSTEM_PARAM_ERROR_CODE.getMessage(),message);
        }
        UsernamePasswordToken token = new UsernamePasswordToken(loginForm.getUsername(), loginForm.getPassphrase());
        // Remember Me built-in, just do this
        // TODO: Make this a user option instead of hard coded in.
        token.setRememberMe(true);
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(token);
            logger.info("AUTH SUCCESS");
            return new ResponeVo<Identity>(EnumCode.SYSTEM_SUCCESS_CODE.getCode(),"登陆成功");
        } catch (AuthenticationException ae) {
            logger.info("AUTH MSSG: " + ae.getMessage());
        }
        return  new ResponeVo(EnumCode.USER_LOGIN_FAIL.getCode(),EnumCode.USER_LOGIN_FAIL.getMessage());
    }


    /**
     * @description:注销
     * @param locale
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = {"/unauthorized"})
    public ResponeVo unauthorized(Locale locale, Model model){
        try {
            logger.debug("enter into unauthorized");
            // This gets the current subject from shiro
            Subject currentUser = SecurityUtils.getSubject();
            logger.debug("exit unauthorized");
            currentUser.logout(); //removes all identifying information and invalidates their session too.
            return new ResponeVo<Identity>(EnumCode.SYSTEM_SUCCESS_CODE.getCode(), "注销成功");
        }catch (Exception e){
            e.printStackTrace();
            return new ResponeVo<Identity>(EnumCode.USER_LOGOUT_FAIL.getCode(),EnumCode.USER_LOGOUT_FAIL.getMessage());
        }
    }

}
