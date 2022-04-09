package com.danim.orders.parser;

import com.danim.orders.beans.Orders;
import com.danim.orders.beans.OrdersDTO;
import com.danim.orders.beans.OrdersVO;
import com.danim.items.beans.ItemsDTO;
import com.danim.qna.service.QnaService;
import com.danim.shop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrdersParser {

    private final ShopService shopService;
    private final DecimalFormat formatter;
    private final SimpleDateFormat simpleDateFormat;
    private final SimpleDateFormat simpleDateFormatIncludeTime;

    @Autowired
    public OrdersParser(ShopService shopService, DecimalFormat formatter, SimpleDateFormat simpleDateFormat, SimpleDateFormat simpleDateFormatIncludeTime) {
        this.shopService = shopService;
        this.formatter = formatter;
        this.simpleDateFormat = simpleDateFormat;
        this.simpleDateFormatIncludeTime = simpleDateFormatIncludeTime;
    }

    // Orders DTO 를 Orders Entity 로 변환하는 메소드
    public Orders parseOrdersDTOtoEntity(OrdersDTO ordersDTO){
        // 파싱 정보를 저장할 아이템객체 생성
        Orders order = new Orders();

        /////////////// 주문 객체에 등록될 정보 ////////////////////////////
        // 이름
        order.setName(ordersDTO.getShippingName());
        // 배송지 우편번호
        order.setZipcode(ordersDTO.getShippingZipcode());
        // 배송지 주소
        order.setAddr(ordersDTO.getShippingAddr() + ", " + ordersDTO.getShippingAddrDetail());
        // 전화번호
        order.setMobile(ordersDTO.getShippingMobile1() + "-"
                + ordersDTO.getShippingMobile2() + "-" + ordersDTO.getShippingMobile3());
        // 요청사항
        order.setRequest(ordersDTO.getRequest());
        // 전체 금액
        order.setPrice(ordersDTO.getTotalCost());
        // 결제 정보
        order.setPayment(ordersDTO.getPayment());
        // 아이템 리스트
        order.setItemlist(ordersDTO.getItemlist());
        ///////////////////////////////////////////////////////////////

        return order;
    }

    // Orders Entity 를 Orders VO 로 변환하는 메소드
    public OrdersVO parseOrdersEntitytoVO(Orders order){
        // 파싱 정보를 저장할 아이템객체 생성
        OrdersVO ordersVO = new OrdersVO();

        ////////////////////////// 주문 VO 객체에 등록될 정보 ////////////////////////////
        // 주문 번호
        ordersVO.setOrderNum(order.getOrdernum());

        // 주문자 이름
        ordersVO.setName(order.getName());

        /////// 대표 상품명 ///////
        // order entity에 저장된 상품 리스트 (JSONString 타입)
        String orderEntityItemList = order.getItemlist();
        // JSONString to List<ItemsDTO>
        List<ItemsDTO> itemsList = shopService.getItemListFromJSONString(orderEntityItemList);
        // 상품 리스트중 대표상품
        ItemsDTO titleItem = itemsList.get(0);
        // 대표 상품명 저장
        ordersVO.setTitleItem(titleItem.getMfr() + " " + titleItem.getName());
        ///////////////////////

        // 상품 리스트 저장
        ordersVO.setItemsList(itemsList);

        // 썸네일 저장 (대표 상품의 대표 이미지)
        ordersVO.setThumbnail(titleItem.getPic().split("\\$")[0]);

        // 대표 상품 외 상품 갯수 저장
        ordersVO.setOthers((itemsList.size() - 1));

        // 포멧 처리된 가격
        String formattedPrice = formatter.format(Integer.parseInt(order.getPrice()));
        ordersVO.setPrice(formattedPrice); // 저장

        // 주문 일자 (yyyy-HH-dd 형식)
        String shortDate = simpleDateFormat.format(order.getOrderdate());
        ordersVO.setShortDate(shortDate); // 저장

        // 결제 방법
        ordersVO.setPayment(order.getPayment());

        // 주문 상태 HashMap
        Map<String, String> stateMap = new HashMap<>() {{
            put("101", "결제 확인");
            put("102", "상품 준비중");
            put("103", "배송중");
            put("201", "배송완료");
            put("202", "취소");
        }};
        // Entity에 저장된 주문 상태 값 확인 후 그에 맞는 상태 메세지 저장
        for (String key: stateMap.keySet()){
            String value = stateMap.get(key);
            if (key.equals(order.getState())) {
                ordersVO.setState(value);
                break;
            }
        }

        // 1:1 문의 상태 HashMap
        Map<String, String> qnaMap = new HashMap<>() {{
            put("00", "00"); // 없음
            put("01", "미답변");
            put("02", "답변완료");
        }};
        // Entity에 저장된 1:1 문의 상태 값 확인 후 그에 맞는 상태 메세지 저장
        for (String key: qnaMap.keySet()){
            String value = qnaMap.get(key);
            if (key.equals(order.getQna())) {
                ordersVO.setQna(value);
                break;
            }
        }

        // 주문 일자 (yyyy-HH-dd hh.mm.ss 형식)
        ordersVO.setDate(simpleDateFormatIncludeTime.format(order.getOrderdate()));

        // 우편 번호
        ordersVO.setZipcode(order.getZipcode());

        // 주소
        ordersVO.setAddr(order.getAddr());

        // 전화 번호
        ordersVO.setMobile(order.getMobile());

        // 운송장 번호
        String wabillNum = order.getWaybillnum();
        if (wabillNum == null) wabillNum = "-"; // null일 경우 - 로 변환
        ordersVO.setWaybillNum(wabillNum);

        // 요청 사항
        String request = order.getRequest();
        if (request == null) request = "-"; // null일 경우 - 로 변환
        ordersVO.setRequest(request);


        return ordersVO;
    }

    // Orders Entity List 를 Orders VO List 로 변환하는 메소드
    public List<OrdersVO> ordersListEntitytoVO(List<Orders> ordersList) {
        // 반환할 Orders VO List
        List<OrdersVO> ordersVOList = new ArrayList<>();

        // Orders Entity List가 null 일경우 null 반환
        if (ordersList == null) return null;

        // Orders Entity List 구성요소를 모두 VO로 변환
        for (Orders order : ordersList){
            ordersVOList.add(parseOrdersEntitytoVO(order));
        }

        // 변환된 리스트 반환
        return ordersVOList;
    }
}
