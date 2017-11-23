package com.xinfang.app.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinfang.app.dao.AreaDao;
import com.xinfang.app.dao.CityDao;
import com.xinfang.app.dao.ProvinceDao;
import com.xinfang.app.model.Area;
import com.xinfang.app.model.City;
import com.xinfang.app.model.Province;


@Service
public class CityService {

    @Autowired
    private CityDao cityDao;
    @Autowired
    private ProvinceDao provinceDao;
    @Autowired
    private AreaDao areaDao;

    public List<Province> getAllProvince() {
        return provinceDao.findAll();
    }

    public List<City> getCityByProvinceId(String id) {
        return cityDao.getCityByProvinceId(id);
    }

    public List<Area> getAreaByCityId(String id) {
        return areaDao.getAreaByCityId(id);
    }
}
