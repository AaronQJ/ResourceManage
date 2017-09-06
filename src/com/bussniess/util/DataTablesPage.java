package com.bussniess.util;

import java.util.List;

public class DataTablesPage<T> {

	/*
	服务器返回的数据的格式 :
	{
		"sEcho":1,
		"iTotalRecords":26,        //总数据条数
		"iTotalDisplayRecords":26,
		"aaData":[      //一页数据
				{"userId":"001","account":"dandan1","username":"dandan1","gender":"male"},
				{"userId":"002","account":"dandan2","username":"dandan2","gender":"male"},
				{"userId":"003","account":"dandan3","username":"dandan3","gender":"male"}
		]
	}
	*/

	
	private int iDisplayStart; //每页开始下标
	private int iDisplayLength;//每页长度
	private int iTotalRecords;//总条数
	private int iTotalDisplayRecords;//每页显示条数
	private int totalPage;//总页数
	private int currentPage;//当前页
	private List<T> data; //每页的数据
	


	 private boolean isFirstPage;    //是否为第一页
     private boolean isLastPage;        //是否为最后一页
     private boolean hasPreviousPage;    //是否有前一页
     private boolean hasNextPage;        //是否有下一页
    
    
	
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getIDisplayStart() {
		return iDisplayStart;
	}

	public void setIDisplayStart(int iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}

	public int getIDisplayLength() {
		return iDisplayLength;
	}

	public void setIDisplayLength(int iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}


	public int getITotalRecords() {
		return iTotalRecords;
	}

	public void setITotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public int getITotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setITotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
	
	public static  int getCurrPageIndex(int currentPage,int pageNum){
		int index = 0;
			
		return index;
	}
	
	  public void init(){
	         this.isFirstPage = isFirstPage();
	         this.isLastPage = isLastPage();
	         this.hasPreviousPage = isHasPreviousPage();
	         this.hasNextPage = isHasNextPage();
	     }
	  
	  public boolean isFirstPage() {
	         return currentPage == 1;    // 如是当前页是第1页
	     }
	     public boolean isLastPage() {
	         return currentPage == totalPage;    //如果当前页是最后一页
	     }
	     public boolean isHasPreviousPage() {
	         return currentPage != 1;        //只要当前页不是第1页
	     }
	     public boolean isHasNextPage() {
	         return currentPage != totalPage;    //只要当前页不是最后1页
	     }
	    
	     public static int countTotalPage(final int pageSize,final int totalRecord){
	         int totalPage = totalRecord % pageSize == 0 ? totalRecord/pageSize : totalRecord/pageSize+1;
	         return totalPage;
	     }
	    
	    
	     public static int countOffset(final int pageSize,final int currentPage){
	         final int offset = pageSize*(currentPage-1);
	         return offset;
	     }
	    
	    
	     public static int countCurrentPage(int page){
	         final int curPage = (page==0?1:page);
	         return curPage;
	     }
	
	
	
	
	
	
	
	


}

