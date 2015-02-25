package com.wandoujia.poker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.wandoujia.poker.models.ApiResult;
import com.wandoujia.poker.service.PokerService;
import com.wandoujia.poker.util.MediaType;

/**
 * @author chentian
 */
@Controller
@RequestMapping("/api")
public class CommonController {

    @Autowired
    private PokerService pokerService;

    @RequestMapping(value = "/reload", produces = MediaType.MEDIA_TYPE_JSON)
    public @ResponseBody
    String reload() {
        ApiResult result = new ApiResult(pokerService.reloadData());
        return new Gson().toJson(result);
    }
}
