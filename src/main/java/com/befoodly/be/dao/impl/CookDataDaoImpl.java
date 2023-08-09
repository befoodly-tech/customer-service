package com.befoodly.be.dao.impl;

import com.befoodly.be.dao.CookDataDao;
import com.befoodly.be.entity.CookDataEntity;
import com.befoodly.be.repository.CookDataRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CookDataDaoImpl implements CookDataDao {

    private final CookDataRepository cookDataRepository;

    @Override
    public void save(@NonNull CookDataEntity cookDataEntity) {
        cookDataRepository.saveAndFlush(cookDataEntity);
    }
}
