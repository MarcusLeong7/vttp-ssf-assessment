package vttp.batch5.ssf.noticeboard.controllers;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.services.NoticeService;
import vttp.batch5.ssf.noticeboard.utils.DateConvertor;

import java.io.StringReader;
import java.util.Date;

// Use this class to write your request handlers
@Controller
@RequestMapping
public class NoticeController {

    @Autowired
    private NoticeService noticeSvc;

    @GetMapping(path = {"/", "index.html"})
    public String noticeForm(Model model) {
        model.addAttribute("notice", new Notice());
        return "notice";
    }

    @PostMapping("/notice")
    public String noticeSubmit(@Valid @ModelAttribute("notice") Notice notice,
                               BindingResult bindingResult, Model model) {

        // For debugging
        if (bindingResult.hasErrors()) {
            System.out.print(">>> Binding Error");
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println(" - " + error.getDefaultMessage());
            });
            model.addAttribute("notice", notice); // Retain the original object with errors
            return "notice";
        }
        try {
            String id = noticeSvc.postJsonPayload(notice);
            System.out.println(id);
            model.addAttribute("noticeid", id);
            return "view2";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error, please try again");
            return "view3";
        }

    }


}
