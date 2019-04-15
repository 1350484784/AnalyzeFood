package com.cs.analyzefood.service.impl;

import com.cs.analyzefood.entity.InformEvent;
import com.cs.analyzefood.mapper.InformMapper;
import com.cs.analyzefood.service.InformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class InformServiceImpl implements InformService {

    @Autowired
    private InformMapper informMapper;

    @Override
    public void addInform(InformEvent informEvent) {
        informEvent.setCreateTime(new Date());
        informMapper.insertInform(informEvent);
    }

    @Override
    public List<InformEvent> getInformEvent(int roleId) {
        return informMapper.selectEventsByRoleId(roleId);
    }

    @Override
    public void updateInformStatus(int id) {
        informMapper.updateInformStatus(id);
    }
}
