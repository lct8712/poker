package com.wandoujia.poker.controllers;

import com.google.gson.Gson;
import com.wandoujia.poker.models.PlayerDataBean;
import com.wandoujia.poker.service.PokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author chentian
 */
@Controller
@RequestMapping("/api/player")
public class PlayerController {
    @Autowired
    private PokerService pokerService;

    @RequestMapping(value = "/all", produces = "application/json; charset=utf-8")
    public @ResponseBody
    String getAll() {
        Map<String, PlayerDataBean> players = pokerService.getPlayerDataBeans();
        return new Gson().toJson(players.values());
    }

    @RequestMapping(value = "/search/{name:.+}", produces = "application/json; charset=utf-8")
    public @ResponseBody
    String getOne(@PathVariable("name") String name) {
        Map<String, PlayerDataBean> players = pokerService.getPlayerDataBeans();
        return new Gson().toJson(players.get(name));
    }

    @RequestMapping(value = "/ranking", produces = "application/json; charset=utf-8")
    public @ResponseBody
    String ranking(HttpServletRequest request) {
        try {
            List<PlayerDataBean> players = pokerService.getPlayerWithRanking(request.getParameter("type"));
            return new Gson().toJson(players);
        } catch (IllegalArgumentException e) {
            return "usage: type = " + e.getMessage();
        }
    }
}
