package com.wandoujia.poker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
public class SeasonController {

    @Autowired
    private PokerService pokerService;

    @RequestMapping(value = "/season/list", produces = MediaType.MEDIA_TYPE_JSON)
    public @ResponseBody
    String list() {
        return new Gson().toJson(pokerService.getSeasonDurationBeans());
    }

    @RequestMapping(value = "/season/update", params = {"content"},
            method = RequestMethod.POST, produces = MediaType.MEDIA_TYPE_JSON)
    public @ResponseBody
    String update(
            @RequestParam(value = "content", required = true) String content) {
        ApiResult result = pokerService.updateSeasonDuration(content);
        return new Gson().toJson(result);
    }
}
