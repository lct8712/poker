package com.wandoujia.poker.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

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

    @RequestMapping(value = "/zip", produces = MediaType.MEDIA_TYPE_ZIP)
    public void zip(HttpServletResponse response) {
        response.setContentType(MediaType.MEDIA_TYPE_ZIP);
        response.addHeader("Content-Disposition", "attachment; filename=\"data.zip\"");
        response.addHeader("Content-Transfer-Encoding", "binary");

        ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
        try {
            pokerService.compressAllData(outputBuffer);
            response.getOutputStream().write(outputBuffer.toByteArray());
            response.getOutputStream().flush();
            outputBuffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
