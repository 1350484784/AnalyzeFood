package com.cs.analyzefood.service.impl;

import com.cs.analyzefood.entity.Food;
import com.cs.analyzefood.mapper.ManageMapper;
import com.cs.analyzefood.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManageServiceImpl implements ManageService {

    @Autowired
    private ManageMapper manageMapper;

    @Override
    public List<Food> getAllFood() {
        return manageMapper.selectFood();
    }
}
