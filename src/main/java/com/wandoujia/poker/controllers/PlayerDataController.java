package com.wandoujia.poker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wandoujia.poker.service.PokerService;

/**
 * @author chentian
 */
@Controller
@RequestMapping("/player")
public class PlayerDataController {
    @Autowired
    private PokerService pokerService;

}
