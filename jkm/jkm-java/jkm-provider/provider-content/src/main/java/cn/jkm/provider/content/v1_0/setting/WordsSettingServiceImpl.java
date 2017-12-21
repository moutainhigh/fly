package cn.jkm.provider.content.v1_0.setting;

import cn.jkm.core.domain.mongo.content.Expert;
import cn.jkm.core.domain.mysql.content.SensitiveWords;
import cn.jkm.core.domain.type.Status;
import cn.jkm.framework.core.utils.UUIDUtils;
import cn.jkm.framework.mysql.Jdbc;
import cn.jkm.framework.mysql.operation.Query;
import cn.jkm.service.content.WordsSettingService;
import cn.jkm.service.core.domain.ListResult;
import cn.jkm.service.core.exception.ProviderException;
import com.alibaba.dubbo.config.annotation.Service;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zhong on 2017/7/17.
 */
@Service(version = "1.0")
public class WordsSettingServiceImpl implements WordsSettingService {

    @Override
    public boolean del(String wordId) {
        return Jdbc.build().delete(SensitiveWords.class).delete(wordId) > 0;
    }

    @Override
    public boolean add(String word) {
        try {
            SensitiveWords words = new SensitiveWords();
            words.setId(words.uuid());
            words.setName(word);
            words.setStatus(Status.ACTIVE);
            return Jdbc.build().insert(words) > 0;
        }catch (Exception e){
            //throw new ProviderException("新增失败");
            return false;
        }
    }

    @Override
    public boolean batchAdd(List<String> words) {
        words.forEach(v -> this.add(v));
        return  true;
    }

    @Override
    public boolean batchDel(List<String> wordIds) {
        try {
            String ids = wordIds.stream().collect(Collectors.joining("','", "'", "'"));
            Jdbc.build().delete(SensitiveWords.class).where("id in (" + ids + ") ").delete();
        } catch (Exception e) {
            throw new ProviderException("删除失败");
        }
        return true;
    }

    @Override
    public List<SensitiveWords> find(int limit, int page, String words) {
        Query query = Jdbc.build().query(SensitiveWords.class).where("status=?", Status.ACTIVE.toString());
        if (words != null && !"".equals(words)) {
            query.where("name like ?", "%" + words + "%");
        }
        return query.page(limit, page);
    }

    @Override
    public long findCount(String words) {
        Query query = Jdbc.build().query(SensitiveWords.class).where("status=?", Status.ACTIVE.toString());
        if (words != null && !"".equals(words)) {
            query.where("name like ?", "%" + words + "%");
        }
        return query.count();
    }

    @Override
    public ListResult<SensitiveWords> findListWithCount(int limit, int page, String words) {
        return new ListResult<SensitiveWords>(findCount(words),find(limit,page,words));
    }

    @Override
    public void delAllWords() {
        Jdbc.build().delete(SensitiveWords.class).delete();
    }
}
