package cn.jkm.provider.content.v1_0.setting;

import cn.jkm.core.domain.mysql.content.SettingBanner;
import cn.jkm.core.domain.type.BannerType;
import cn.jkm.core.domain.type.EffectType;
import cn.jkm.core.domain.type.Status;
import cn.jkm.framework.mysql.Jdbc;
import cn.jkm.framework.mysql.utils.ClassUtils;
import cn.jkm.service.content.BannerService;
import cn.jkm.service.core.domain.ListResult;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by zhong on 2017/7/17.
 */
@Service(version = "1.0")
public class BannerServiceImpl implements BannerService {

    @Override
    public boolean addBanner(Map request) {

        SettingBanner settingBanner = new SettingBanner();
        settingBanner.setName(formatString(request.get("name")));
        settingBanner.setType(BannerType.valueOf(formatString(request.get("bannerType"))));
        settingBanner.setStatus(Status.ACTIVE);
        //settingBanner.setShowTime(formatLong(request.get("showTime")));
        settingBanner.setEndTime(formatLong(request.get("endTime")));
        settingBanner.setBannerImages(formatString(request.get("bannerImages")));
        settingBanner.setBeginTime(formatLong(request.get("beginTime")));
        //settingBanner.setEffectTime(formatLong(request.get("effectTime")));
        //settingBanner.setEffectType(EffectType.valueOf(formatString(request.get("effectType"))));
        settingBanner.setId(settingBanner.uuid());
        int row = Jdbc.build().insert(settingBanner);
        return row == 1;
    }

    @Override
    public boolean addBanner(SettingBanner settingBanner) {
        int row = 0;
        if(StringUtils.isEmpty(settingBanner.getId())){
            settingBanner.setId(UUID.randomUUID().toString());
            Jdbc.build().insert(settingBanner);
        }
        return row == 1;
    }

    @Override
    public boolean delBanner(String bannerId) {
        int row = Jdbc.build().delete(SettingBanner.class).delete(bannerId);
        return row == 1;
    }

    @Override
    public boolean disableBanner(String bannerId) {
        int row = Jdbc.build().update(SettingBanner.class).where("id=?", bannerId).set("status", Status.DELETE.name());
        return row > 0;
    }

    @Override
    public boolean updateBanner(SettingBanner settingBanner) {
        int row = 0;
        if (settingBanner.getId() == null || "".equals(settingBanner.getId())) {
            settingBanner.setId(settingBanner.uuid());
            row = Jdbc.build().insert(settingBanner);
        } else {
            HashMap map = new HashMap<>();
            map.put("images", settingBanner.getBannerImages());
            map.put("begin_time", settingBanner.getBeginTime());
            //map.put("effect_time", settingBanner.getEffectTime());
            //map.put("effect_type", settingBanner.getEffectType().name());
            map.put("end_time", settingBanner.getEndTime());
            map.put("name", settingBanner.getName());
            //map.put("show_time", settingBanner.getShowTime());
            map.put("type", settingBanner.getType().name());
            map.put("update_at", System.currentTimeMillis());
            map.put("status", settingBanner.getStatus().name());
            row = Jdbc.build().update(SettingBanner.class).where("id=?", settingBanner.getId()).set(map);
        }
        return row == 1;
    }

    @Override
    public SettingBanner findBannerById(String bannerId) {

        SettingBanner banner = Jdbc.build().query(SettingBanner.class).find(bannerId);
        return banner;
    }


    @Override
    public List<SettingBanner> findSplash(int limit, int page) {

        List<SettingBanner> splash = Jdbc.build()
                .query(SettingBanner.class)
                .where(" type=? and status = ? ", BannerType.SPLASH.name(), Status.ACTIVE.name())
                .order(" create_at desc ")
                .page(limit, page);
        return splash;
    }

    @Override
    public long findSplashCount() {
        Long count = Jdbc.build()
                .query(SettingBanner.class)
                .where(" type=? and status = ? ", BannerType.SPLASH.name(), Status.ACTIVE.name())
                .order(" create_at desc ")
                .count();
        return count;
    }

    @Override
    public List<SettingBanner> findBanners(int limit, int page) {
        List<SettingBanner> banners = Jdbc.build()
                .query(SettingBanner.class)
                .where(" type=? and status = ? ", BannerType.BANNER.name(), Status.ACTIVE.name())
                .order(" create_at desc ")
                .page(limit, page);
        return banners;
    }

    @Override
    public long findBannerCount() {
        Long count = Jdbc.build()
                .query(SettingBanner.class)
                .where(" type=? and status = ?", BannerType.BANNER.name(), Status.ACTIVE.name())
                .order(" create_at desc ")
                .count();
        return count;
    }

    @Override
    public ListResult<SettingBanner> findSplashWithCount(int limit, int page) {
        return new ListResult<SettingBanner>(findSplashCount(),findSplash(limit,page));
    }

    @Override
    public ListResult<SettingBanner> findBannersWithCount(int limit, int page) {
        return new ListResult<SettingBanner>(findBannerCount(),findBanners(limit,page));
    }

    public String formatString(Object obj) {
        if (null == obj) {
            return null;
        }
        return String.valueOf(obj);
    }
    public Long formatLong(Object obj) {
        if (null == obj) {
            return null;
        }
        return Long.parseLong(String.valueOf(obj));
    }
}
