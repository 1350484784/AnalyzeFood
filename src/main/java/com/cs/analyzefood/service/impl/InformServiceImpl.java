package com.cs.analyzefood.service.impl;

import com.cs.analyzefood.entity.InformEvent;
import com.cs.analyzefood.mapper.InformMapper;
import com.cs.analyzefood.service.InformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class InformServiceImpl implements InformService {

    @Autowired
    private InformMapper informMapper;

    @Override
    public void addInform(InformEvent informEvent) {
        informEvent.setCreateTime(new Date());
        informMapper.insertInform(informEvent);
    }
}
