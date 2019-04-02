package com.cs.analyzefood.control.inform;

import com.cs.analyzefood.entity.InformEvent;
import com.cs.analyzefood.service.InformService;
import com.cs.analyzefood.util.InformUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/inform")
public class InformControl {

    @Autowired
    private InformService informService;

    @RequestMapping("/produceInfo")
    @ResponseBody
    public ResponseEntity produceInfo(InformEvent informEvent){
        if (informEvent.getType() == 1){
            informEvent.setContent(InformUtil.INFORM_TYPE_1+informEvent.getContent());
        }
        informService.addInform(informEvent);

        return new ResponseEntity(true, HttpStatus.OK);
    }
}
