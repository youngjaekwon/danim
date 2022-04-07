<%@ page import="java.util.List" %>
<%@ page import="com.danim.orders.beans.Orders" %>
<%@ page import="com.danim.orders.beans.OrdersVO" %>
<%@ page import="com.danim.items.beans.ItemsDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<c:set var="path" value="${pageContext.request.contextPath}"/>--%>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <link rel="stylesheet" href="/resources/css/member-orderlist.css">
  <!-- fontawesome v5 cdn -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />

  <title>다님 : 여행을 다니다</title>
  <script src="/resources/js/member-orderlist.js" defer></script>
  <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</head>

<body>
<%@include file ="../common/header.jsp" %>
<section class="main">
  <div class="main-inner">
    <div class="main-left">
      <div class="main-nav">
        <ul>
          <li><a href="/member/mypage">회원정보 수정</a></li>
          <li><a href="/member/orderList">주문내역</a></li>
          <li><a href="/member/qnaList">1:1 문의</a></li>
        </ul>
      </div>
    </div>
    <div class="main-right">
      <p>주문 내역</p>
      <div class="orders_header">
        <div class="orders_title">
          <div class="orders_header_num">주문번호</div>
          <div class="orders_header_title">상품명</div>
          <div class="orders_header_normal">합계 금액</div>
          <div class="orders_header_normal">주문 날짜</div>
          <div class="orders_header_normal">결제수단</div>
          <div class="orders_header_normal">주문 상태</div>
          <div class="orders_header_normal">QnA</div>
        </div>
      </div>
      <div class="orders_list">
        <%
          List<OrdersVO> itemList = (List<OrdersVO>) request.getAttribute("itemList");
          if (itemList.isEmpty()){
        %>
          <div class="emptyList">주문리스트가 비었습니다.</div>
        <%
          } else {
              for(int i = 0; i < itemList.size(); i++){
                OrdersVO order = itemList.get(i);
                String orderNum = order.getOrderNum();
        %>
        <div class="order">
          <a href="" class="item-img" id="img<%=orderNum%>"></a>
          <script>$('#img<%=orderNum%>').css("background-image", "url('<%=order.getThumbnail()%>')")</script>
          <div class="order_num"><a href="javascript:toggleOrderModal('<%=orderNum%>')"><%=orderNum%></a></div>
          <div class="order_title">
            <a href="javascript:toggleOrderModal('<%=orderNum%>')"><span><%=order.getTitleItem()%></span> <%if(order.getOthers() > 0) {%>외 <%=order.getOthers()%><%}%></a>
          </div>
          <div class="order_price"><%=order.getPrice()%></div>
          <div class="order_date"><%=order.getShortDate()%></div>
          <div class="order_payment"><%=order.getPayment()%></div>
          <div class="order_state"><a href="#"><%=order.getState()%></a></div>
          <div class="order_qna">
            <a href="/member/qna_reg?ordernum=<%=orderNum%>">문의하기</a>
          </div>
        </div>
        <%
              }
          }
        %>
      </div>
      <div class="paging">
        <%
          int pageStart = (int) request.getAttribute("pageStart"); // 페이지 목록중 시작
          int pageEnd = (int) request.getAttribute("pageEnd"); // // 페이지 목록중 끝
          if (pageEnd != 0){
        %>
        <div class="page-prv">
          <a href="/orderList?page=${prevPage}" class="page-prv-btn">
            <i class="fas fa-angle-double-left"></i>
          </a>
        </div>
        <div class="pages">
          <%
              for (int i = pageStart; i <= pageEnd; i++){
          %>
          <a href="/orderList?page=<%=i%>"><%=i%></a>
          <%
              }
          %>
        </div>
        <div class="page-next">
          <a href="/orderList?page=${nextPage}" class="page-next-btn">
            <i class="fas fa-angle-double-right"></i>
          </a>
        </div>
        <%} else {%>
        <div class="page-prv">
          <a href="" class="page-prv-btn">
            <i class="fas fa-angle-double-left"></i>
          </a>
        </div>
        <div class="pages">
          <a>1</a>
        </div>
        <div class="page-next">
          <a href="" class="page-next-btn">
            <i class="fas fa-angle-double-right"></i>
          </a>
        </div>
        <%}%>
      </div>
    </div>
  </div>
  <%
    for(int i = 0; i < itemList.size(); i++){
      OrdersVO order = itemList.get(i);
      String orderNum = order.getOrderNum();
  %>
  <div class="order_modal" id="order_modal<%=orderNum%>" style="display: none">
    <div class="order_modal_inside">
      <div class="order_modal_header">
        <div>주문 정보</div>
        <div class="order_modal_header_right">
          <%
            // 1 : 1 문의 여부 00: 없음, 01: 미답변, 02: 답변완료
            if (order.getState().equals("00")){
          %>
          <button type="button" onclick="location.href='/member/qna_reg?ordernum=<%=orderNum%>'">문의하기</button>
          <%
            } else {
          %>
          <button type="button" onclick="location.href='/member/qna?ordernum=<%=orderNum%>'">문의하기</button>
          <%
            }
          %>
          <a href="javascript:toggleOrderModal('<%=orderNum%>')"><i class="far fa-times-circle"></i></a>
        </div>
      </div>
      <div class="order_modal_body">
        <div class="order_info_table">
          <div class="order_info_table_header">
            <div>주문정보</div>
            <div>배송정보</div>
          </div>
          <div class="order_info_table_line1">
            <div class="order_info_table_line1_info">
              <div>
                <div>주문번호</div>
                <div><%=orderNum%></div>
              </div>
              <div>
                <div>주문일자</div>
                <div><%=order.getDate()%></div>
              </div>
            </div>
            <div class="order_info_table_line1_delivery">
              <div>
                배송주소
              </div>
              <div>
                <%=order.getZipcode()%> <%=order.getAddr()%>
              </div>
            </div>
          </div>
          <div class="order_info_table_line2">
            <div>
              <div>주문자</div>
              <div><%=order.getName()%></div>
            </div>
            <div>
              <div>연락처</div>
              <div>
                <%=order.getMobile()%>
              </div>
            </div>
          </div>
          <div class="order_info_table_line3">
            <div>
              <div>주문상태</div>
              <div>
                <%=order.getState()%>
              </div>
            </div>
            <div>
              <div>운송장번호</div>
              <div>
                <a href="#"><%=order.getWaybillNum()%></a>
              </div>
            </div>
          </div>
        </div>
        <div class="order_payment_table">
          <div>결제정보</div>
          <div>
            <div>
              <div>주문 금액</div>
              <div>
                <%=order.getPrice()%>
              </div>
            </div>
            <div>
              <div>결제수단</div>
              <div><%=order.getPayment().equals("card")?"카드결제":""%></div>
            </div>
          </div>
        </div>
      </div>
      <div class="order_text">
        <div>요청사항</div>
        <div>
          <a href=""><%=order.getRequest()%></a>
          <button>전체보기</button>
        </div>
      </div>
      <div class="order_items">
        <div>상품리스트</div>
        <div class="order_items_header">
          <div class="order_item_num">상품번호</div>
          <div class="order_item_category">카테고리</div>
          <div class="order_item_name">상품명</div>
          <div class="order_item_quantity">수량</div>
          <div class="order_item_price">가격</div>
          <div class="order_item_stock">재고</div>
        </div>
        <div class="order_items_list">
          <%
            List<ItemsDTO> orderItemsList = order.getItemsList();
            for (ItemsDTO item : orderItemsList){
          %>
          <div class="order_item">
            <div class="order_item_num"><%=item.getItemnum()%></div>
            <div class="order_item_category"><%=item.getCategory()%></div>
            <div class="order_item_name"><a href="/shop/item?item=<%=item.getItemnum()%>"><%=item.getMfr() + " " + item.getName()%></a></div>
            <div class="order_item_quantity"><%=item.getQuantity()%></div>
            <div class="order_item_price"><%=item.getFormattedPrice()%></div>
            <div class="order_item_stock"><%=item.getStock()%></div>
          </div>
          <%
            }
          %>
        </div>
      </div>
    </div>
  </div>
  <%
    }
  %>
</section>
<script>
  // filter 기본 체크
  $('[name=filter] [name=state]').val('${state}').prop("selected", true);
  $('[name=filter] [name=qna]').val('${qna}').prop("selected", true);
  $('[name=filter] [name=sort]').val('${sorting}').prop("selected", true);
  $('[name=orders_search] [name=keyword]').val('${keyword}');
</script>
</body>

</html>
