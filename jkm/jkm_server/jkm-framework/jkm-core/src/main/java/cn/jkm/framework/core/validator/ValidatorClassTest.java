package cn.jkm.framework.core.validator;

/**
 * Created by werewolf on 16/11/24.
 */

@NotNull(name = {"name1"}, message = "名称1不能为空", match = {"type", "1"})
@NotNull(name = {"name2","name1"}, message = "名称2,1不能为空", match = {"type", "2"})
@NotNull(name = {"type"}, message = "type不能为空")
public class ValidatorClassTest {


}
