package com.wandoujia.poker.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.wandoujia.poker.models.PlayerDataBean;
import com.wandoujia.poker.service.PokerService;

/**
 * @author chentian
 */
@Controller
@RequestMapping("/api/player")
public class PlayerController {
    @Autowired
    private PokerService pokerService;

    @RequestMapping(value = "/all")
    public @ResponseBody
    String getAll() {
        Map<String, PlayerDataBean> players = pokerService.getPlayerDataBeans();
        return new Gson().toJson(players);
    }

    @RequestMapping(value = "/search/{name:.+}")
    public @ResponseBody
    String getOne(@PathVariable("name") String name) {
        Map<String, PlayerDataBean> players = pokerService.getPlayerDataBeans();
        return new Gson().toJson(players.get(name));
    }

    @RequestMapping(value = "/ranking")
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
