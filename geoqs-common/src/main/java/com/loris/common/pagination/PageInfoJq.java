package com.loris.common.pagination;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * 分页结果的封装(for Jqgrid)
 *
 * @author fengshuonan
 * @Date 2017年1月22日 下午11:06:41
 */
public class PageInfoJq<T> {

    // 结果集
    private List<T> rows;
    // 总页数
    private long total;
    //当前页数
    private long page;
	//总条数
    private long records;
   //耗时，毫秒
    private long costtime;

    public PageInfoJq(Page<T> page,long costTime) {
        this.rows = page.getRecords();
        this.records = page.getTotal();
        this.page=page.getCurrent();
        this.total=page.getPages();
        this.costtime=costTime;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
    public long getPage() {
		return page;
	}

	public void setPage(long page) {
		this.page = page;
	}

	public long getRecords() {
		return records;
	}

	public void setRecords(long records) {
		this.records = records;
	}

	public long getCosttime() {
		return costtime;
	}

	public void setCosttime(long costtime) {
		this.costtime = costtime;
	}

}
