package com.danim.common.paging;

import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public class PageMaker<T> {

    /*
     * 요청에 따라 페이지를 생성하는 메소드
     *
     * 인자 :
     * - ModelAndView
     * - totalList: Paging할 전체 리스트
     * - requestPage: 요청된 페이지 번호
     * - numPerPage: 한 페이지당 출력할 아이템 수
     * - pagePerBlock: 하단 페이지 네비게이션 한 블럭 당 출력할 페이지 수
     * */
    public static <T> void makePage(ModelAndView mav, List<T> totalList, String requestPage, int numPerPage, int pagePerBlock){
        ////////////// 페이지 구성 요소 ///////////////
        int totalRecords = totalList.size(); // 전체 아이템 수
        int nowPage = 1; // 현재 페이지
        if (requestPage != null) nowPage = Integer.parseInt(requestPage); // 요청된 페이지가 null이 아닐경우 현재페이지 갱신
        int totalPage = (int)Math.ceil((double)totalRecords / numPerPage); // 전체 페이지 (전체 아이템 수 / 페이지당 아이템 수)
        int totalBlock = (int)Math.ceil((double)totalPage / pagePerBlock); // 전체 블럭 (전체 페이지 수 / 블럭당 페이지 수)
        int nowBlock = (int)Math.ceil((double)nowPage / pagePerBlock); // 현재 블럭 (현재 페이지 / 블럭당 페이지 수)
        int pageStart = pagePerBlock * (nowBlock - 1) + 1; // 블럭에 표시될 페이지중 첫 페이지
        int pageEnd   = pageStart + pagePerBlock - 1; // 블럭에 표시될 페이지중 마지막 페이지
        if(pageEnd >= totalPage) pageEnd = totalPage; // 계산된 마지막 페이지가 전체 페이지 수 보다 클 경우, 마지막 페이지 = 전체 페이지 수
        int prevPage = 1; // 이전 블럭으로 이동 (1번 블럭일 경우 1페이지로 이동)
        if (nowBlock > 1) prevPage = pageStart - 1; // 2번 블럭 이상일 경우, 이전 블럭 마지막 페이지로 이동
        int nextPage = totalPage; // 다음 블럭으로 이동 (마지막 블럭일 경우 마지막 페이지로 이동)
        if (totalBlock > nowBlock) nextPage = pageEnd + 1; // 마지막 블럭이 아닐경우, 다음 블럭 첫 페이지로 이동
        int start = (nowPage * numPerPage) - numPerPage + 1; // 현재 페이지에 표시될 첫번째 아이템 번호
        int end = start + numPerPage - 1; // 현재 페이지에 표시될 마지막 아이템 번호
        if (end > totalRecords) end = totalRecords; // 계산된 마지막 아이템 번호가 전체 아이템 수 보다 클 경우, 마지막 아이템 번호 = 전체 아이템 수
        List<T> itemList = totalList.subList(start - 1, end); // 반환할 리스트 (첫 번째 아이템 인덱스 ~ 마지막 아이템 인덱스)

        // 생성된 페이지 요소를 Model에 추가
        mav.addObject("itemList", itemList); // 페이지에 표시될 아이템 리스트
        mav.addObject("prevPage", prevPage); // 이전 블럭으로 이동
        mav.addObject("nextPage", nextPage); // 다음 블럭으로 이동
        mav.addObject("pageStart", pageStart); // 블럭에 표시될 첫 번째 페이지
        mav.addObject("pageEnd", pageEnd); // 블럭에 표시될 마지막 페이지
    }
}
