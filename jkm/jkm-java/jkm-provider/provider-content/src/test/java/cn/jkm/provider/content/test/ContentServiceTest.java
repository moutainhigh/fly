package cn.jkm.provider.content.test;

import cn.jkm.provider.content.BasicTest;
import cn.jkm.provider.content.content.v1_0.ContentPostServiceImpl;
import cn.jkm.service.content.ContentPostService;

/**
 * Created by admin on 2017/7/17.
 */
public class ContentServiceTest extends BasicTest{

    ContentPostService contentService = new ContentPostServiceImpl();
//    @Test
//    public void testAddContentBase(){
//        for (int i=0 ; i<20;i++) {
//            ContentBase contentBase = new ContentBase();
//            contentBase.setTitle("帖子"+i);
//            contentBase.setType((short) 1);
//            contentBase.setChannelId("sdfsdf");                //内容所属频道栏目id
//            contentBase.setCommentNum(0);                //评论数
//            contentBase.setBrowseNum(0);                //浏览数
//            contentBase.setPointNum(0);                //点赞数
//            contentBase.setCollectionNum(0);            //收藏数
//            contentBase.setPublishUserId("XXX");            //内容发布人
//            contentBase.setContentStatus((short) 3);            //内容状态 1.待提交；2.待审核；3.显示；4.置顶；5.隐藏；6.删除；
//            contentBase.setPicUrl("http://127.0.0.1:8080/xxx");                    //内容标题图片地址
//            contentBase.setIsTop((short) 0);                    //是否置顶
////        private Integer topOrder;				//置顶顺序
//            contentBase.setDetail("帖子"+i+"的详情");                    //内容详情
//            contentBase.setPostFormulaInfo("123");            //内容中关联的配方id
//            contentBase.setPostProductionInfo("456");        //内容中关联的产品id
//            contentBase.setHandleUserId("xxx");            //内容审核人
////        private Long handleTime;				//审核时间
////        private String expertId;				//专家文章专家id
////        private String formulaInfo;				//配方详情
////        private String formulaSymptom;			//配方适应证
////        private Integer formulaBuyNum;			//配方购买量
////        private String formulaProductionInfo;	//配方包含的商品信息
////        private String formulaExpertId;			//配方专家id
//            contentBase.setComplaintCount(0);            //投诉次数
//            contentBase.setComplaintHandleCount(0);    //已处理次数
//            contentBase.setCreateAt(System.currentTimeMillis());
//
//            contentService.addContentBase(contentBase);
//        }
//
//    }

//    @Test
//    public void testFindContents(){
//        List<ContentBase> contentBases = contentService.findContentsByCondition(new ContentBase());
//        for (ContentBase content:contentBases) {
//            System.out.println(content);
//        }
//    }
}
