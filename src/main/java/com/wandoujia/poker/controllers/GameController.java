package com.wandoujia.poker.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.google.gson.Gson;
import com.wandoujia.poker.models.ApiResult;
import com.wandoujia.poker.models.GameInfoBean;
import com.wandoujia.poker.service.PokerService;

/**
 * @author chentian
 */
@Controller
@RequestMapping("/api/game")
public class GameController {
    @Autowired
    private PokerService pokerService;

    @RequestMapping(value = "/all", produces = "application/json; charset=utf-8")
    public @ResponseBody
    String getAll() {
        List<GameInfoBean> games = pokerService.getGameInfoBeans();
        return new Gson().toJson(games);
    }

    @RequestMapping(value = "/search/{date:\\d+}", produces = "application/json; charset=utf-8")
    public @ResponseBody
    String getOne(@PathVariable("date") String date) {
        GameInfoBean gameInfoBean = pokerService.getGameInfoBean(date);
        return gameInfoBean == null ? "" : new Gson().toJson(gameInfoBean);
    }

    @RequestMapping(value = "/update", params = {"date", "content", "comment"},
            method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    String update(
            @RequestParam(value = "date", required = true) String date,
            @RequestParam(value = "content", required = true) String content,
            @RequestParam(value = "comment", required = false) String comment) {
        ApiResult result = pokerService.updateGame(date, content, comment);
        return new Gson().toJson(result);
    }
}
