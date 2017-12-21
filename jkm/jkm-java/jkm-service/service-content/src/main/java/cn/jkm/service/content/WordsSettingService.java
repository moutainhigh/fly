package cn.jkm.service.content;

import cn.jkm.core.domain.mysql.content.SensitiveWords;
import cn.jkm.service.core.domain.ListResult;

import java.util.List;

/**
 * Created by zhong on 2017/7/17.
 */
public interface WordsSettingService {
    /**
     * 根据敏感词id删除敏感词
     * @param wordId
     * @return
     */
    public boolean del(String wordId);

    /**
     * 添加敏感词
     * @param words
     * @return
     */
    public boolean add(String words);
    /**
     * 批量添加敏感词
     * @param words
     * @return
     */
    public boolean batchAdd(List<String> words);

    /**
     * 批量删除敏感词
     * @param wordIds
     * @return
     */
    public boolean batchDel(List<String> wordIds);

    /**
     * 根据分页和条数，查询过滤敏感词
     * @param limit
     * @param page
     * @param words
     * @return
     */
    public List<SensitiveWords> find(int limit,int page,String words);

    /**
     * 根据关键字查询所有count
     * @param words
     * @return
     */
    public long findCount(String words);

    public ListResult<SensitiveWords> findListWithCount(int limit,int page,String words);

    /**
     * 清空所有敏感词
     */
    public void delAllWords();
}
