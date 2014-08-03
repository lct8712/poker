package com.wandoujia.poker.controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
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

    @RequestMapping(value = "/all")
    public @ResponseBody
    String getAll(HttpServletResponse response) {
        List<GameInfoBean> games = pokerService.getGameInfoBeans();
        return new Gson().toJson(games);
    }
}
