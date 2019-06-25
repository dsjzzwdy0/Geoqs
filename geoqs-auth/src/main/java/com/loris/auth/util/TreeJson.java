package com.loris.auth.util;

import java.util.List;
import java.util.stream.Collectors;
import com.loris.auth.model.node.TreeNode;

public class TreeJson {
	/**
	 * 转换树Json
	 * @param list 数据源
	 * @param ParentId 父节点
	 * @return
	 */
    public static String TreeToJson(List<TreeNode> list, String ParentId)
    {
        StringBuilder sb = new StringBuilder();
        List<TreeNode> nodes = list.stream().filter(t->t.getParentId().equals(ParentId)).collect(Collectors.toList());
        sb.append("[");
        if (nodes.size() > 0)
        {
            for(TreeNode entity : nodes)
            {
                sb.append("{");
                sb.append("\"id\":\"" + entity.getId() + "\",");                    
                sb.append("\"text\":\"" + entity.getText().replaceAll("&nbsp;", "") + "\",");
                sb.append("\"value\":\"" + entity.getValue() + "\",");
                if (entity.getAttribute() != null && entity.getAttribute() != "")
                {
                    sb.append("\"" + entity.getAttribute() + "\":\"" + entity.getAttributeValue() + "\",");
                }
                if (entity.getAttributeA() != null && entity.getAttributeA()!="")
                {
                    sb.append("\"" + entity.getAttributeA() + "\":\"" + entity.getAttributeValueA() + "\",");
                }
                if (entity.getTitle() != null && entity.getTitle().replaceAll("&nbsp;", "")!="")
                {
                    sb.append("\"title\":\"" + entity.getTitle().replaceAll("&nbsp;", "")+ "\",");
                }
                if (entity.getImg() != null && entity.getImg().replaceAll("&nbsp;", "")!="")
                {
                    sb.append("\"img\":\"" + entity.getImg().replaceAll("&nbsp;", "")+ "\",");
                }
                if (entity.getCheckstate() != null)
                {
                    sb.append("\"checkstate\":" + entity.getCheckstate() + ",");
                }
                if (entity.getParentId() != null)
                {
                    sb.append("\"parentnodes\":\"" + entity.getParentId() + "\",");
                }
                if (entity.getLevel() != null)
                {
                    sb.append("\"Level\":" + entity.getLevel().toString() + ",");
                }
                if (entity.getShowcheck() != null)
                {
                	sb.append("\"showcheck\":" + entity.getShowcheck().toString() + ",");
                }
                if (entity.getIsexpand() != null)
                {
                	sb.append("\"isexpand\":" + entity.getIsexpand().toString() + ",");
                }
                if (entity.getComplete()!= null)
                {
                    sb.append("\"complete\":" + entity.getComplete().toString() + ",");
                }
                if (entity.getHasChildren()!= null)
                {
                	sb.append("\"hasChildren\":" + entity.getHasChildren().toString() + ",");
                }
                sb.append("\"ChildNodes\":" + TreeToJson(list, entity.getId()) + "");
                sb.append("},");
            }
            sb = sb.delete(sb.length() - 1, sb.length());
        }
         sb.append("]");
         return sb.toString();
//        String strJson="";
//		try {
//			strJson = new String(sb.toString().getBytes("iso8859-1"),"UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		};
//        return strJson;
    }
}
