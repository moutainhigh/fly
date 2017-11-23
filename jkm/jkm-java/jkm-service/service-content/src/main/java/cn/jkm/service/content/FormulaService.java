package cn.jkm.service.content;

import cn.jkm.core.domain.mongo.content.ContentFormula;

import java.util.List;

/**
 * @author zhong
 * @version V1.0
 * @project jkm-root
 * @package cn.jkm.service.content
 * @description //配方接口
 * @update 2017/7/21 16:23
 */
public interface FormulaService {

    /**
     * 添加配方
     * @param title     配方标题
     * @param info        配方详情
     * @param symptom      配方适应症状
     * @param expertId      配方推荐专家id
     * @param productInfos    配方对应产品id
     */
    public void formulaAdd(String title,String info,String symptom,String expertId,String productInfos);

    /**
     * 所有配方列表
     */
    public List<ContentFormula> formulaListAll(int limit, int page);

    /**
     * 所有配方数量
     */
    public long formulaCountAll();


    /**
     * 显示的配方数量
     */
    public long formulaCountShow();

    /**
     * 显示的配方列表
     */
    public List<ContentFormula> formulaListShow(int limit, int page);

    /**
     * 某专家显示的配方列表
     *
     * @param expertId
     * @param limit
     * @param page
     * @return
     */
    public List<ContentFormula> formulaListByExpertId(String expertId, int limit, int page);

    /**
     * 某专家显示的配方数量
     *
     * @param expertId
     * @return
     */
    public long formulaCountByExpertId(String expertId);


    /**
     * 隐藏配方
     */
    public void formulaHiddenById(String formulaId);

    /**
     * 删除配方
     */
    public void formulaDelById(String formulaId,String userId);

    /**
     * 根据配方id获取配方详情
     */
    public ContentFormula formulaGetById(String formulaId);

    /**
     * 配方观看一次
     */
    public void formulaBrowse(String formulaId);
    /**
     * 配方购买一次
     */
    public void formulaBuy(String formulaId);
    /**
     * 配方点赞一次
     */
    public void formulaPoint(String formulaId,String userId);
    /**
     * 配方收藏一次
     */
    public void formulaStore(String formulaId,String userId);

    /**
     * 配方取消收藏一次
     */
    public void formulaUnStore(String formulaId,String userId);

    /**
     * 配方添加一次评论
     */
    public void formulaCommentAddCount(String formulaId);

    /**
     * 配方回收站列表
     */
    public List<ContentFormula> formulaDelList(int limit, int page);
    /**
     * 配方回收站总数
     */
    public long formulaDelCount();

    /**
     * 物理删除单个配方
     */
    public void formulaPhysicalDel(String formulaId);

    /**
     * 物理批量删除配方
     */
    public void formulaPhysicalDel(String[] formulaIds);

    /**
     * 恢复删除的配方
     * @param formulaId
     */
    public void formulaRecoveryDel(String formulaId);
}
