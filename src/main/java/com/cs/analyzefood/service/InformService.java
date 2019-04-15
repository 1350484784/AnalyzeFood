package com.cs.analyzefood.service;

import com.cs.analyzefood.entity.InformEvent;

import java.util.List;

public interface InformService {
    void addInform(InformEvent informEvent);

    List<InformEvent> getInformEvent(int roleId);

    void updateInformStatus(int id);
}
