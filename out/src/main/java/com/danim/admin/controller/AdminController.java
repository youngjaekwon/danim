package com.danim.admin.controller;

import com.danim.common.paging.PageMaker;
import com.danim.files.beans.FilesEntity;
import com.danim.files.service.FilesService;
import com.danim.items.service.ItemsService;
import com.danim.member.beans.Member;
import com.danim.member.service.MemberService;
import com.danim.orders.beans.OrdersVO;
import com.danim.orders.service.OrdersService;
import com.danim.items.beans.ItemsDTO;
import com.danim.qna.beans.QnaVO;
import com.danim.qna.service.QnaService;
import com.danim.shop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final OrdersService ordersService;
    private final ShopService shopService;
    private final ItemsService itemsService;
    private final MemberService memberService;
    private final QnaService qnaService;
    private final FilesService filesService;

    @Autowired
    public AdminController(OrdersService ordersService, ShopService shopService, ItemsService itemsService, MemberService memberService, QnaService qnaService, FilesService filesService) {
        this.ordersService = ordersService;
        this.shopService = shopService;
        this.itemsService = itemsService;
        this.memberService = memberService;
        this.qnaService = qnaService;
        this.filesService = filesService;
    }

    @RequestMapping(value = "/orders", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView orders(HttpServletRequest httpServletRequest){
        ModelAndView mav = new ModelAndView("/admin/admin-orders"); // view 추가

        // paging 요소
        String state = httpServletRequest.getParameter("state"); // 주문 상태
        String qna = httpServletRequest.getParameter("qna"); // 1:1 문의 상태
        String sorting = httpServletRequest.getParameter("sort"); // 정렬 방법
        String keyword = httpServletRequest.getParameter("keyword"); // 검색 키워드
        String requestPage = httpServletRequest.getParameter("page"); // 요청된 페이지

        // Parameter 값 없는 경우 Defalt값 설정
        if (state == null) state = "%%";
        if (qna == null) qna = "%%";
        if (sorting == null) sorting = "ORDERNUM DESC";
        if (keyword == null || keyword.equals("")) keyword = "%%";
        if (requestPage != null && requestPage.equals("")) requestPage = "1";

        // paging 요소로 리스트 검색
        List<OrdersVO> totalList = ordersService.getList(state, qna, sorting, keyword);

        // 리스트 검색이 안될경우
        if (totalList == null) {
            totalList = new ArrayList<>();
        }

        int numPerPage = 10; // 한 페이지당 출력할 아이템 수: 주문리스트의 경우 10개
        int pagePerBlock = 6; // 하단 페이지 네비게이션 한 블럭 당 출력할 페이지 수: 주문 리스트의 경우 6개

        // 검색된 리스트, 요청된 페이지를 이용하여 페이지 생성
        PageMaker.makePage(mav, totalList, requestPage, numPerPage, pagePerBlock);

        // 체크할 filter 값 전달
        mav.addObject("state", state);
        mav.addObject("qna", qna);
        mav.addObject("sorting", sorting);
        if (!keyword.equals("%%"))
            mav.addObject("keyword", keyword);

        return mav;
    }

    @RequestMapping(value = "/items", method = {RequestMethod.GET})
    public ModelAndView items(HttpServletRequest httpServletRequest){
        ModelAndView mav = new ModelAndView("/admin/admin-items"); // view 추가

        // paging 요소
        String category = httpServletRequest.getParameter("category"); // 카테고리
        String stock = httpServletRequest.getParameter("stock"); // 재고 상태
        String sorting = httpServletRequest.getParameter("sort"); // 정렬 방법
        String keyword = httpServletRequest.getParameter("keyword"); // 검색 키워드
        String requestPage = httpServletRequest.getParameter("page"); // 요청된 페이지

        // Parameter 값 없는 경우 Defalt값 설정
        if (category == null) category = "%%";
        if (stock == null) stock = ">-1";
        if (sorting == null) sorting = "ITEMNUM";
        if (keyword == null || keyword.equals("")) keyword = "%%";
        if (requestPage != null && requestPage.equals("")) requestPage = "1";

        // paging 요소로 리스트 검색
        List<ItemsDTO> totalList = shopService.getList(category, stock, sorting, keyword);

        // 리스트 검색이 안될경우
        if (totalList == null) {
            totalList = new ArrayList<>();
        }

        int numPerPage = 10; // 한 페이지당 출력할 아이템 수: 주문리스트의 경우 10개
        int pagePerBlock = 6; // 하단 페이지 네비게이션 한 블럭 당 출력할 페이지 수: 주문 리스트의 경우 6개

        // 검색된 리스트, 요청된 페이지를 이용하여 페이지 생성
        PageMaker.makePage(mav, totalList, requestPage, numPerPage, pagePerBlock);

        // 체크할 filter 값 전달
        mav.addObject("category", category);
        mav.addObject("stock", stock);
        mav.addObject("sorting", sorting);
        if (!keyword.equals("%%"))
            mav.addObject("keyword", keyword);

        return mav;
    }

    @RequestMapping(value = "/items_reg", method = RequestMethod.GET)
    public String items_reg(){
        return "/admin/admin-items-reg";
    }

    @RequestMapping(value = "/items_update", method = RequestMethod.GET)
    public ModelAndView items_update(@RequestParam String itemnum){
        ModelAndView mav = new ModelAndView("/admin/admin-items-update");

        // 상품 번호를 통한 DTO 검색
        ItemsDTO item = itemsService.getDTO(itemnum);
        // 상품 번호를 통한 사진 파일 검색
        List<FilesEntity> pics = filesService.getList("ITEMNUM", itemnum);

        // 검색된 상품을 모델에 추가
        mav.addObject("item", item);
        // 검색된 사진 파일을 모델에 추가
        mav.addObject("pics", pics);
        // 외부사진 경로를 모델에 추가
        if (item.getPic() != null){
            List<String> exPics = Arrays.asList(item.getPic().split("\\$")) // item에 저장된 사진 array
                    .stream()
                    .filter(pic -> pic.startsWith("http")) // 사진들 중 http 로 시작하는 사진들은 외부 경로
                    .collect(Collectors.toList());
            mav.addObject("exPics", exPics);
        }

        return mav;
    }


    @RequestMapping(value = "/members", method = {RequestMethod.GET})
    public ModelAndView members(HttpServletRequest httpServletRequest){
        ModelAndView mav = new ModelAndView("/admin/admin-members"); // view 추가

        // paging 요소
        String state = httpServletRequest.getParameter("state"); // 회원 상태
        String sorting = httpServletRequest.getParameter("sort"); // 정렬 방법
        String keyword = httpServletRequest.getParameter("keyword"); // 검색 키워드
        String requestPage = httpServletRequest.getParameter("page"); // 요청된 페이지

        // Parameter 값 없는 경우 Defalt값 설정
        if (state == null) state = "%%";
        if (sorting == null) sorting = "MEMNUM DESC";
        if (keyword == null || keyword.equals("")) keyword = "%%";
        if (requestPage != null && requestPage.equals("")) requestPage = "1";

        // paging 요소로 리스트 검색
        List<Member> totalList = memberService.getList(state, sorting, keyword);

        // 리스트 검색이 안될경우
        if (totalList == null) {
            totalList = new ArrayList<>();
        }

        int numPerPage = 10; // 한 페이지당 출력할 아이템 수: 주문리스트의 경우 10개
        int pagePerBlock = 6; // 하단 페이지 네비게이션 한 블럭 당 출력할 페이지 수: 주문 리스트의 경우 6개

        // 검색된 리스트, 요청된 페이지를 이용하여 페이지 생성
        PageMaker.makePage(mav, totalList, requestPage, numPerPage, pagePerBlock);

        // 체크할 filter 값 전달
        mav.addObject("state", state);
        mav.addObject("sorting", sorting);
        if (!keyword.equals("%%"))
            mav.addObject("keyword", keyword);

        return mav;
    }

    @RequestMapping(value = "/qnas", method = {RequestMethod.GET})
    public ModelAndView qnas(HttpServletRequest httpServletRequest){
        ModelAndView mav = new ModelAndView("/admin/admin-qnas"); // view 추가

        // paging 요소
        String category = httpServletRequest.getParameter("category"); // 문의 종류
        String state = httpServletRequest.getParameter("state"); // 문의 상태
        String sorting = httpServletRequest.getParameter("sort"); // 정렬 방법
        String keyword = httpServletRequest.getParameter("keyword"); // 검색 키워드
        String requestPage = httpServletRequest.getParameter("page"); // 요청된 페이지

        // Parameter 값 없는 경우 Defalt값 설정
        if (category == null) category = "%%";
        if (state == null) state = "%%";
        if (sorting == null) sorting = "QNANUM DESC";
        if (keyword == null || keyword.equals("")) keyword = "%%";
        if (requestPage != null && requestPage.equals("")) requestPage = "1";

        // paging 요소로 리스트 검색
        List<QnaVO> totalList = qnaService.getList(category, state, sorting, keyword);

        // 리스트 검색이 안될경우
        if (totalList == null) {
            totalList = new ArrayList<>();
        }

        int numPerPage = 10; // 한 페이지당 출력할 아이템 수: 주문리스트의 경우 10개
        int pagePerBlock = 6; // 하단 페이지 네비게이션 한 블럭 당 출력할 페이지 수: 주문 리스트의 경우 6개

        // 검색된 리스트, 요청된 페이지를 이용하여 페이지 생성
        PageMaker.makePage(mav, totalList, requestPage, numPerPage, pagePerBlock);

        // 체크할 filter 값 전달
        mav.addObject("category", category);
        mav.addObject("state", state);
        mav.addObject("sorting", sorting);
        if (!keyword.equals("%%"))
            mav.addObject("keyword", keyword);

        return mav;
    }

    @RequestMapping(value = "/qna", method = RequestMethod.GET)
    public String qna(String qnanum, String ordernum, Model model, HttpSession session, RedirectAttributes attributes){
        // 요청된 QNA 검색
        QnaVO qna = null;
        if (qnanum != null){
            qna = qnaService.getQna(qnanum);
        } else if (ordernum != null){
            qna = qnaService.searchQna("QNANUM", qnanum);
        }
        // 로그인된 유저
        String memnum = (String) session.getAttribute("user");

        // 로그인된 유저 객체
        Member member = memberService.selectMember(memnum);

        model.addAttribute("qna", qna);
        model.addAttribute("pics", qna.getPics());
        model.addAttribute("member", member);
        model.addAttribute("comments", qna.getComments());
        model.addAttribute("role", member.getRole());

        return "admin/admin-qna-detail";
    }
}
