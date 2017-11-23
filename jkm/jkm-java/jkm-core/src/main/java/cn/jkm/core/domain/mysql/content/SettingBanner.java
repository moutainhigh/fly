package cn.jkm.core.domain.mysql.content;

import cn.jkm.core.domain.mysql.MySqlBasic;
import cn.jkm.core.domain.type.BannerType;
import cn.jkm.core.domain.type.EffectType;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by zhong on 2017/7/14.
 * 闪屏banner主题表
 */
@Entity
@Table(name = "tb_setting_banner", catalog = "jkm")
public class SettingBanner extends MySqlBasic<SettingBanner> {

	@Column(name = "name", length = 64,columnDefinition = "varchar(64) COMMENT '主题名称'")
	private String name;
	@Column(name = "begin_time", nullable = false, length = 19,columnDefinition = "BIGINT(20) COMMENT '生效时间'")
	private Long beginTime;
	@Column(name = "end_time", nullable = false, length = 19,columnDefinition = "BIGINT(20) COMMENT '隐藏时间'")
	private Long endTime;
//	@Column(name = "show_time",columnDefinition = "BIGINT(20) COMMENT '显示时长'")
//	private Long showTime;
//	@Column(name = "effect_time",columnDefinition = "BIGINT(20) COMMENT '特效时长'")
//	private Long effectTime;
	@Column(name = "images", length = 512,columnDefinition = "varchar(1024) COMMENT '图片地址组'")
	private String bannerImages;
//	@Column(name = "effect_type",columnDefinition = "varchar(32) COMMENT '特效方式'")
//	@Enumerated(EnumType.STRING)
//	private EffectType effectType;
	@Column(name = "type",columnDefinition = "varchar(32) COMMENT '类型'")
	@Enumerated(EnumType.STRING)
	private BannerType type;

	public SettingBanner() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	/*public Long getShowTime() {
		return showTime;
	}

	public void setShowTime(Long showTime) {
		this.showTime = showTime;
	}

	public Long getEffectTime() {
		return effectTime;
	}

	public void setEffectTime(Long effectTime) {
		this.effectTime = effectTime;
	}*/

	public String getBannerImages() {
		return bannerImages;
	}

	public void setBannerImages(String bannerImages) {
		this.bannerImages = bannerImages;
	}

	/*public EffectType getEffectType() {
		return effectType;
	}

	public void setEffectType(EffectType effectType) {
		this.effectType = effectType;
	}
*/
	public BannerType getType() {
		return type;
	}

	public void setType(BannerType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "SettingBanner{" +
				"name='" + name + '\'' +
				", beginTime=" + beginTime +
				", endTime=" + endTime +
//				", showTime=" + showTime +
//				", effectTime=" + effectTime +
				", bannerImages='" + bannerImages + '\'' +
//				", effectType=" + effectType +
				", type=" + type +
				'}';
	}
}