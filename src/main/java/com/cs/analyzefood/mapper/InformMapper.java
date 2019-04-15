package com.cs.analyzefood.mapper;

import com.cs.analyzefood.entity.InformEvent;

import java.util.List;

public interface InformMapper {
    void insertInform(InformEvent informEvent);

    List<InformEvent> selectEventsByRoleId(int roleId);

    void updateInformStatus(int id);
}
