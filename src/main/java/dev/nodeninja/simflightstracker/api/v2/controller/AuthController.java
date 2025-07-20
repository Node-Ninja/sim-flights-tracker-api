package dev.nodeninja.simflightstracker.api.v2.controller;

import dev.nodeninja.simflightstracker.tracker.service.VatsimService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/oauth/callback")
public class AuthController {
    private final VatsimService vatsimService;

    @GetMapping
    public RedirectView patchAuthRecord(@RequestParam(required = false) String code, @RequestParam String state) {
        if (code==null || state==null) {
            return  new RedirectView("/oauth/complete?status=failed&state="+state);
        }

        var response = vatsimService.patchAuth(code, state);
        var status = response ? "success" : "failure";
        var redirectUri = "/oauth/complete?state=" + state + "&status=" + status;

        return new RedirectView(redirectUri);
    }
}
