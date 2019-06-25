package com.loris.auth.model.node;
/// <summary>
/// 树实体
/// </summary>
public class TreeNode {
	private String parentId ;
	private String id ;
	private String text ;
	private String value ;
	private Integer checkstate ;
	private Boolean showcheck ;
	private Boolean complete ;
    /// <summary>
    /// 是否展开
    /// </summary>
	private Boolean isexpand ;
    /// <summary>
    /// 是否有子节点
    /// </summary>
	private Boolean hasChildren ;
    /// <summary>
    /// 图片
    /// </summary>
	private String img ;
    /// <summary>
    /// title
    /// </summary>
	private String title ;
    /// <summary>
    /// 级
    /// </summary>
	private Integer Level ;
    /// <summary>
    /// 自定义属性
    /// </summary>
	private String Attribute ;
    /// <summary>
    /// 自定义属性值
    /// </summary>
	private String AttributeValue ;
    /// <summary>
    /// 自定义属性A
    /// </summary>
	private String AttributeA ;
    /// <summary>
    /// 自定义属性值A
    /// </summary>
	private String AttributeValueA ;
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getCheckstate() {
		return checkstate;
	}
	public void setCheckstate(Integer checkstate) {
		this.checkstate = checkstate;
	}
	public Boolean getShowcheck() {
		return showcheck;
	}
	public void setShowcheck(Boolean showcheck) {
		this.showcheck = showcheck;
	}
	public Boolean getComplete() {
		return complete;
	}
	public void setComplete(Boolean complete) {
		this.complete = complete;
	}
	public Boolean getIsexpand() {
		return isexpand;
	}
	public void setIsexpand(Boolean isexpand) {
		this.isexpand = isexpand;
	}
	public Boolean getHasChildren() {
		return hasChildren;
	}
	public void setHasChildren(Boolean hasChildren) {
		this.hasChildren = hasChildren;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getLevel() {
		return Level;
	}
	public void setLevel(Integer level) {
		Level = level;
	}
	public String getAttribute() {
		return Attribute;
	}
	public void setAttribute(String attribute) {
		Attribute = attribute;
	}
	public String getAttributeValue() {
		return AttributeValue;
	}
	public void setAttributeValue(String attributeValue) {
		AttributeValue = attributeValue;
	}
	public String getAttributeA() {
		return AttributeA;
	}
	public void setAttributeA(String attributeA) {
		AttributeA = attributeA;
	}
	public String getAttributeValueA() {
		return AttributeValueA;
	}
	public void setAttributeValueA(String attributeValueA) {
		AttributeValueA = attributeValueA;
	}
}
