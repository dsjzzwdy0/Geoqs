package com.loris.common.constant.tips;

/**
 * 返回给前台的成功提示
 *
 * @author fengshuonan
 * @date 2016年11月12日 下午5:05:22
 */
public class SuccessTip extends Tip{
	private Object result;
	public SuccessTip(){
		super.code = 200;
		super.message = "操作成功";
	}
	public SuccessTip(Object data){
		super.code = 200;
		super.message = "操作成功";
		this.result=data;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
}
