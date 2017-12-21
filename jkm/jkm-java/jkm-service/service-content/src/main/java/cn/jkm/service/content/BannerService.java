package cn.jkm.service.content;

import cn.jkm.core.domain.mysql.content.SettingBanner;
import cn.jkm.service.core.domain.ListResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhong on 2017/7/17.
 */
public interface BannerService {

    /**
     * 新增主题
     */
    public boolean addBanner(SettingBanner settingBanner);
    /**
     * 新增主题
     */
    public boolean addBanner(Map request);

    /**
     * 根据bannerid删除主题
     */

    public boolean delBanner(String bannerId);

    /**
     * 修改主题
     */
    public boolean updateBanner(SettingBanner settingBanner);


    public SettingBanner findBannerById(String bannerId);

    /**
     * 获取闪屏列表
     */
    public List<SettingBanner> findSplash(int limit, int page);

    /**
     * 获取闪屏总条数
     * @return
     */
    public long findSplashCount();

    /**
     * 获取闪屏列表，带上条数
     * @param limit
     * @param page
     * @return
     */
    public ListResult<SettingBanner> findSplashWithCount(int limit, int page);
    /**
     * 获取banner列表
     */
    public List<SettingBanner> findBanners(int limit, int page);

    /**
     * 获取banner总条数
     * @return
     */
    public long findBannerCount();

    /**
     * 获取闪屏列表，带上条数
     * @param limit
     * @param page
     * @return
     */
    public ListResult<SettingBanner> findBannersWithCount(int limit, int page);
    /**
     * 逻辑删除banner主题
     * @param bannerId
     * @return
     */
    public boolean disableBanner(String bannerId);
}
