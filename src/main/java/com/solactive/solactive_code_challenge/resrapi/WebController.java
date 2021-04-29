package com.solactive.solactive_code_challenge.resrapi;


import com.solactive.solactive_code_challenge.models.SampleResponse;
import org.springframework.web.bind.annotation.*;


@RestController
public class WebController {

    @RequestMapping("/sample")
    public SampleResponse Sample(@RequestParam(value = "name",
            defaultValue = "Robot") String name) {
        SampleResponse response = new SampleResponse();
        response.setId(1);
        response.setMessage("Your name is "+name);
        return response;
    }

}
