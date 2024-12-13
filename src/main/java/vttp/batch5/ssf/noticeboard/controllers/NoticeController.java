package vttp.batch5.ssf.noticeboard.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vttp.batch5.ssf.noticeboard.models.Notice;
import vttp.batch5.ssf.noticeboard.services.NoticeService;

import java.util.Random;

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

    @GetMapping("/status")
    public ModelAndView getHealth() {
        ModelAndView mav = new ModelAndView();

        try {
            checkHealth();

            mav.setViewName("healthy");
            mav.setStatus(HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            mav.setViewName("unhealthy");
            mav.setStatus(HttpStatusCode.valueOf(503));
        }
        return mav;
    }

    private void checkHealth() throws Exception {
        // get random number between 0 and 10
        Random random = new Random();
        Integer value = random.nextInt(0, 10);
        // if random number is between 0 and 5
        // throw an exception
        // means there is an exception/error (simulating exception)
        if (value <= 5) {
            throw new Exception("Simulating error..." + value.toString());
        }
        // else do nothing
    }



}
