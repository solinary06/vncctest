package com.yeahmobi.vncctest.service;

import com.yeahmobi.vncctest.dao.UserDao;
import com.yeahmobi.vncctest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by steven.liu on 2016/4/15.
 */

@Controller
public class LoginService {

    @Autowired
    UserDao userDao;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView showHbasePage() {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public ModelAndView doLogin(@RequestParam("userName") String name, @RequestParam("password") String password) {

        ModelAndView view;
        if (userDao.selectByName(name, password) == null) {
            String message = "user/password is wrong or not exist!";
            view = new ModelAndView("login");
            view.addObject("msg", "" + message);

        } else {
            view = new ModelAndView("httpRequest");
        }
        return view;
    }

    @RequestMapping(value = "/doRegister", method = RequestMethod.POST)
    public ModelAndView doRegister(@RequestParam("registerUserName") String name, @RequestParam("registerPassword") String password) {

        ModelAndView view = new ModelAndView("login");
        User user = new User(0,name,password);
        userDao.insert(user);

        view.addObject("msg", "Register successfully.");
        return view;
    }


}
