package cn.jkm.eb.service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.jkm.eb.facade.OrderFacade;
import cn.jkm.eb.facade.entities.Order;
import cn.jkm.eb.service.dao.OrderMapper;

@Service("orderService")
public class OrderService implements OrderFacade {
	@Autowired
	private OrderMapper orderMapper;

	public OrderService() {

	}

	@Transactional
	public int test() {
		/*Order order = new Order();

		order.setUserId("3b03e647-bd02-499b-9d81-2fa16f094e16");
		for(int i = 0; i < 100; i++) {
			order.setId(UUID.randomUUID().toString());
			order.setOrderNo(UUID.randomUUID().toString());
			orderMapper.insert(order);
		}
*/
		return 0;
	}

	/**
	 * 分页查询
	 */
	public void pageList() {
		//获取第1页，10条内容，默认查询总数count
		PageHelper.startPage(2, 20);
		//紧跟着的第一个select方法会被分页
		/*List<Order> list = orderMapper.selectAll();
		PageInfo<Order> page = new PageInfo(list);
		System.out.println("起始页：" + page.getPageNum());
		System.out.println("每页数：" + page.getPageSize());
		System.out.println("总页数：" + page.getPages());
		System.out.println("总记录：" + page.getTotal());
		for(Order order : list) {
			System.out.println(order.getOrderNo());
		}*/
	}

	public void selectOne() {
		/*Order order = new Order();
		order.setId("008cffbf-07d0-447d-b41c-669015c4ab97");
		order = orderMapper.selectOne(order);
		System.out.println("订单编号：" + order.getOrderNo());*/
	}
}
