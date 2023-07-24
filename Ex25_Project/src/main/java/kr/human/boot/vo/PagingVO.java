package kr.human.boot.vo;

import java.util.List;

import lombok.Getter;
@Getter
public class PagingVO<T> {
	// 페이징한 데이터 목록
	private List<T> content;
	private int idx;
	private String name;
	private int count;
	private String userid;
	private int ref;
	
	// 넘겨받을 변수 4개
	private int totalCount; 	// 전체 개수
	private int currentPage;	// 현재 페이지
	private int sizeOfPage;		// 페이지당 글수
	private int sizeOfBlock;	// 하단 페이지 목록 개수
	
	// 계산해서 만들 변수
	private int totalPage;	// 전체 페이지수
	private int startNo;	// 시작 글번호
	private int endNo;		// 끝 글번호
	private int startPage;	// 시작 페이지 번호
	private int endPage;	// 끝 페이지 번호
	
	// 생성자에서 4개는 넘겨 받자
	public PagingVO(int totalCount, int currentPage, int sizeOfPage, int sizeOfBlock) {
		this.totalCount = totalCount;
		this.currentPage = currentPage;
		this.sizeOfPage = sizeOfPage;
		this.sizeOfBlock = sizeOfBlock;
		calc();
	}
	// 계산
	private void calc() {
		if(totalCount>0) {
			if(currentPage<=0) currentPage = 1;
			if(sizeOfPage<=0) sizeOfPage = 10;
			if(sizeOfBlock<=1) sizeOfBlock = 10;
			
			totalPage = (totalCount-1)/sizeOfPage + 1;
			if(currentPage>totalPage) currentPage = 1;
			
			startNo = (currentPage-1) * sizeOfPage + 1;
			endNo = startNo + sizeOfPage -1;
			if(endNo>totalCount) endNo = totalCount;
			
			startPage = (currentPage-1)/sizeOfBlock * sizeOfBlock + 1;
			endPage = startPage + sizeOfBlock - 1;
			if(endPage>totalPage) endPage = totalPage;
		}
	}
	public void setContent(List<T> content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "PaingVO [list=" + content + ", totalCount=" + totalCount + ", currentPage=" + currentPage + ", sizeOfPage="
				+ sizeOfPage + ", sizeOfBlock=" + sizeOfBlock + ", totalPage=" + totalPage + ", startNo=" + startNo
				+ ", endNo=" + endNo + ", startPage=" + startPage + ", endPage=" + endPage + "]";
	}
	
	// 메서드 2개를 추가해 보자(선택)
	// 페이지 상단에 "전체 : ??개(현재페이지/전체페이지 Page)"을 출력해주는 메서드
	public String getPageInfo() {
		return "전체 : " + totalCount + "개" + (totalCount>0 ? "(" + currentPage + "/ " + totalPage + "Page)" : "");
	}

	// 목록 상/하단에 페이지 번호를 출력해주는 메서드
	public String getBottomPage() {
		StringBuffer sb = new StringBuffer();
		// 글이 1개라도 존재해야 페이지 목록이 있다.
		if(totalCount>0) {
			// 페이지 시작
			sb.append("<ul class='pagination pagination-sm justify-content-center'>");
			// 이전 : 시작페이지 번호가 1보다 클때만 이전을 표시한다.
			if(startPage>1) {
				sb.append("<li class='page-item'>");
				sb.append("<a class='page-link' href='?p=" + (startPage-1) + "&s=" + sizeOfPage + "&b=" + sizeOfBlock + "' aria-label='Previous'>");
				sb.append("<span aria-hidden='true'>&laquo;</span>");
				sb.append("</a>");
				sb.append("</li>");
			}
			// 페이지 목록 : 시작 페이지 번호 ~ 끝 페이지 번호까지 출력 하는데 현재 페이지는 링크를 걸지 않는다.
			for(int i=startPage;i<=endPage;i++) {
				if(i==currentPage) {
					sb.append("<li class='page-link page-item active' aria-current='page'>" + i + "</li>");
				}else {
					sb.append("<li class='page-item'><a class='page-link' href='?p=" + i + "&s=" + sizeOfPage + "&b=" + sizeOfBlock + "'>" + i + "</a></li>");
				}
			}
			// 이후 : 끝페이지 번호가 전체 페이지 번호보다 적을 때만 이후를 표시한다.
			if(endPage<totalPage) {
				sb.append("<li class='page-item'>");
				sb.append("<a class='page-link' href='?p=" + (endPage+1) + "&s=" + sizeOfPage + "&b=" + sizeOfBlock + "' aria-label='Next'>");
				sb.append("<span aria-hidden='true'>&raquo;</span>");
				sb.append("</a>");
				sb.append("</li>");
			}
			
			// 페이지 종료
			sb.append("</ul>");
		}
		return sb.toString();
	}

}
