package cn.jkm.eb.service.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.jkm.eb.facade.MsgFacade;
/**
 * rocketmq消息db操作类
 * @author zhengzb
 *
 */
@Service
public class MsgService implements MsgFacade {
	@Autowired
	/*private MsgLogMapper msgLogMapper;*/
	@Override
	public void insertMsg() {
//		MsgLog msgLog = new MsgLog();
//		msgLog.setMsgId("aaaaa001");
//		msgLog.setMsgContent("支付成功消息");
//		msgLog.setMsgStatus(1);//1表示消息发送成功，但未消费
//		msgLog.setAddTime(new Date());
//		msgLog.setUpdateTime(new Date());
//		msgLogMapper.insert(msgLog);
		
	}
	@Override
	public void pageList() {
		//获取第1页，10条内容，默认查询总数count
		PageHelper.startPage(2, 10);
		//紧跟着的第一个select方法会被分页
		/*List<MsgLog> list = msgLogMapper.selectAll();
		PageInfo<MsgLog> page = new PageInfo<MsgLog>(list);
		System.out.println("起始页：" + page.getPageNum());
		System.out.println("每页数：" + page.getPageSize());
		System.out.println("总页数：" + page.getPages());
		System.out.println("总记录：" + page.getTotal());
		for(MsgLog msg : page.getList()) {
			System.out.println(msg.getId());
		}
*/
	}

}
