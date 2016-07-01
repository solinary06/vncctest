package com.yeahmobi.vncctest.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by steven.liu on 2016/6/28.
 */
@Controller
public class HomeService {
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView showDataPage() {
        return new ModelAndView("home");
    }
}
