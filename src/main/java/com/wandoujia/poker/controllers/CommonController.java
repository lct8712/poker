package com.wandoujia.poker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.wandoujia.poker.service.PokerService;

/**
 * @author chentian
 */
@Controller
@RequestMapping("/api")
public class CommonController {
    @Autowired
    private PokerService pokerService;

    @RequestMapping(value = "/reload")
    public @ResponseBody
    String reload() {
        return new Gson().toJson(pokerService.reloadData());
    }
}
