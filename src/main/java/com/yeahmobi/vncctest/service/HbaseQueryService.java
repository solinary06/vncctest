package com.yeahmobi.vncctest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by steven.liu on 2015/10/28.
 */
@Controller
public class HbaseQueryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HbaseQueryService.class);

    @RequestMapping(value = "/hbase", method = RequestMethod.GET)
    public ModelAndView forwardToConvCsvUpload() {
        LOGGER.warn("forward　to conv csv Upload!!");
        return new ModelAndView("hbaseQuery");
    }

}
