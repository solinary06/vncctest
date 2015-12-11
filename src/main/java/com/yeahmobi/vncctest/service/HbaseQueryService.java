package com.yeahmobi.vncctest.service;

import com.yeahmobi.vncctest.dao.HbaseDAO;
import com.yeahmobi.vncctest.util.TransactionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;

/**
 * Created by steven.liu on 2015/10/28.
 */
@Controller
public class HbaseQueryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HbaseQueryService.class);

    @RequestMapping(value = "/hbase", method = RequestMethod.GET)
    public ModelAndView showHbasePage() {
        return new ModelAndView("hbaseQuery");
    }

    @RequestMapping(value = "/queryHbase", method = RequestMethod.GET)
    public ModelAndView queryTransactionId(@RequestParam("queryRow") String row, @RequestParam("queryTable") String table) {
        ModelAndView view = new ModelAndView("hbaseQuery");
        HbaseDAO hbaseDAO = new HbaseDAO();
        String message;
        if(table.equals("")||table==null||table.equals("clicklog")) {
            message = hbaseDAO.findByTransactionId("clicklog", row);
        }else{
            message = hbaseDAO.queryRowKey(table, row).toString();
        }
        view.addObject("msg", "" + message);

        return view;
    }

    @RequestMapping(value = "/delRow", method = RequestMethod.POST)
    public ModelAndView delRow(@RequestParam("delRow") String row, @RequestParam("delTable") String table) {
        ModelAndView view = new ModelAndView("hbaseQuery");

        HbaseDAO hbaseDAO = new HbaseDAO();
        if (table.equals("clicklog")) {
            row = TransactionUtil.rowKey(TransactionUtil.unwrap(row));
        }
        String message = "Row：" + row + " deleted!";
        try {
            hbaseDAO.delRow(table, row);
        } catch (Exception e) {
            message = "Delete specified row failed!";
        }

        view.addObject("msg", "" + message);

        return view;
    }

    @RequestMapping(value = "/addRow", method = RequestMethod.POST)
    public ModelAndView addRow(@RequestParam("addRow") String row, @RequestParam("addColumnFamily") String columnFamily,
                               @RequestParam("addColumn") String column, @RequestParam("addValue") String value,
                               @RequestParam("addTable") String table) {
        ModelAndView view = new ModelAndView("hbaseQuery");
        HbaseDAO hbaseDAO = new HbaseDAO();
        if (table.equals("clicklog")) {
            row = TransactionUtil.rowKey(TransactionUtil.unwrap(row));
        }
        String message = "Row：" + row + " Added!";
        try {
            hbaseDAO.addRow(table, row, columnFamily, column, value);
        } catch (Exception e) {
            message = "Delete specified row failed!";
        }

        view.addObject("msg", "" + message);

        return view;
    }

}
