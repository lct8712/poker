package com.wandoujia.poker.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping(value = "/all", params = {"season"},
            produces = "application/json; charset=utf-8")
    public @ResponseBody
    String getAll(@RequestParam(value = "season", required = true) String season) {
        Map<String, PlayerDataBean> players = pokerService.getPlayerDataBeans(season);
        return new Gson().toJson(players.values());
    }

    @RequestMapping(value = "/search/{name:.+}", params = {"season"},
            produces = "application/json; charset=utf-8")
    public @ResponseBody
    String getOne(@PathVariable("name") String name,
                  @RequestParam(value = "season", required = true) String season) {
        Map<String, PlayerDataBean> players = pokerService.getPlayerDataBeans(season);
        return new Gson().toJson(players.get(name));
    }

    @RequestMapping(value = "/ranking", params = {"type", "season"},
            produces = "application/json; charset=utf-8")
    public @ResponseBody
    String ranking(@RequestParam(value = "type", required = true) String type,
                   @RequestParam(value = "season", required = true) String season) {
        try {
            List<PlayerDataBean> players = pokerService.getPlayerWithRanking(type, season);
            return new Gson().toJson(players);
        } catch (IllegalArgumentException e) {
            return "usage: type = " + e.getMessage();
        }
    }
}
