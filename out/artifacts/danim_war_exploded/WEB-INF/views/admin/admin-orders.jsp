<%@ page import="java.util.List" %>
<%@ page import="com.danim.orders.beans.Orders" %>
<%@ page import="com.danim.orders.beans.OrdersVO" %>
<%@ page import="com.danim.shop.beans.ItemsDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<c:set var="path" value="${pageContext.request.contextPath}"/>--%>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <link rel="stylesheet" href="/resources/css/admin-orders.css">
  <!-- fontawesome v5 cdn -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />

  <title>다님 : 여행을 다니다</title>
  <script src="/resources/js/admin-orders.js" defer></script>
  <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</head>

<body>
<%@include file ="../common/header.jsp" %>
<section class="main">
  <div class="main-inner">
    <div class="main-left">
      <div class="main-nav">
        <ul>
          <li><a href="#">주문 관리</a></li>
          <li><a href="#">상품 관리</a></li>
          <li><a href="#">회원 관리</a></li>
          <li><a href="#">1:1 문의</a></li>
        </ul>
      </div>
    </div>
    <div class="main-right">
      <p>주문 관리</p>
      <div class="orders_header">
        <div class="orders_header_top">
          <div class="orders_filter">
            <form action="/admin/orders" method="GET" name="filter">
              <label for="state">주문 상태 :</label>
              <select name="state" id="state">
                <option value="%%" selected>전체</option>
                <option value="10_">진행중인 주문</option>
                <option value="20_">종료된 주문</option>
                <option value="101">결제 확인</option>
                <option value="102">상품 준비중</option>
                <option value="103">배송중</option>
                <option value="201">배송 완료</option>
                <option value="202">취소</option>
              </select>
              <label for="qna">1:1문의 상태 :</label>
              <select name="qna" id="qna">
                <option value="%%" selected>전체</option>
                <option value="01">미답변</option>
                <option value="02">답변 완료</option>
              </select>
              <label for="sort">정렬 방법 :</label>
              <select name="sort" id="sort">
                <option value="ORDERNUM">주문번호 오름차순</option>
                <option value="ORDERNUM DESC" selected>주문번호 내림차순</option>
                <option value="PRICE">합계금액 오름차순</option>
                <option value="PRICE DESC">합계금액 내림차순</option>
                <option value="ORDERDATE">주문일 오름차순</option>
                <option value="ORDERDATE DESC">주문일 내림차순</option>
              </select>
              <input type="hidden" name="keyword" value="${keyword}"/>
              <input type="hidden" name="page"/>
            </form>
          </div>
          <div class="orders_search">
            <form action="/admin/orders" name="orders_search">
              <input type="text" name="keyword" placeholder="주문번호 / 고객명 / 주소">
              <input type="submit" value="검색">
            </form>
            <form action="/admin/orders" name="reset">
              <input type="submit" value="초기화">
            </form>
          </div>
        </div>
        <div class="orders_title">
          <div class="orders_header_num">주문번호</div>
          <div class="orders_header_normal">고객명</div>
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
          <div class="order_num"><a href="#"><%=orderNum%></a></div>
          <div class="order_member"><a href="#"><%=order.getName()%></a></div>
          <div class="order_title">
            <a href="javascript:toggleOrderModal(<%=orderNum%>)"><span><%=order.getTitleItem()%></span> <%if(order.getOthers() > 0) {%>외 <%=order.getOthers()%><%}%></a>
          </div>
          <div class="order_price"><%=order.getPrice()%></div>
          <div class="order_date"><%=order.getShortDate()%></div>
          <div class="order_payment"><%=order.getPayment()%></div>
          <div class="order_state"><a href="#"><%=order.getState()%></a></div>
          <div class="order_qna">
            <%if (order.getQna().equals("00")) { %>
            <span><%="-"%></span>
            <%} else {%>
            <a href="#">
              <%=order.getQna()%>
            </a>
            <%}%>
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
          <a href="javascript:paging('${prevPage}')" class="page-prv-btn">
            <i class="fas fa-angle-double-left"></i>
          </a>
        </div>
        <div class="pages">
          <%
              for (int i = pageStart; i <= pageEnd; i++){
          %>
          <a href="javascript:paging('<%=i%>')"><%=i%></a>
          <%
              }
          %>
        </div>
        <div class="page-next">
          <a href="javascript:paging('${nextPage}')" class="page-next-btn">
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
  <div class="order_modal" id="order_modal" style="display: none">
    <div class="order_modal_inside">
      <div class="order_modal_header">
        <div>주문 정보</div>
        <div class="order_modal_header_right">
          <button type="button">인쇄하기</button>
          <a href="javascript:toggleOrderModal()"><i class="far fa-times-circle"></i></a>
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
                <div>1</div>
              </div>
              <div>
                <div>주문일자</div>
                <div>2022.02.22 22:22:22</div>
              </div>
            </div>
            <div class="order_info_table_line1_delivery">
              <div>
                배송주소
                <button>변경</button>
              </div>
              <div>
                대구 달서구 달구벌대로309길 5 701호
              </div>
            </div>
          </div>
          <div class="order_info_table_line2">
            <div>
              <div>주문자</div>
              <div><a href="#">김강우</a></div>
            </div>
            <div>
              <div>연락처</div>
              <div>
                010-9268-1241
                <button>변경</button>
              </div>
            </div>
          </div>
          <div class="order_info_table_line3">
            <div>
              <div>주문상태</div>
              <div>
                <select name="state" id="order_state">
                  <option value="" selected>결제 확인</option>
                  <option value="">상품 준비중</option>
                  <option value="">배송중</option>
                  <option value="">배송 완료</option>
                  <option value="">취소</option>
                </select>
              </div>
            </div>
            <div>
              <div>운송장번호</div>
              <div>
                <a href="#">210085720596</a>
                <button>변경</button>
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
                1,220,000
                <button>변경</button>
              </div>
            </div>
            <div>
              <div>결제수단</div>
              <div>네이버 페이</div>
            </div>
          </div>
        </div>
      </div>
      <div class="order_text">
        <div>요청사항</div>
        <div>
          <a href="">미안하다 이거 보여주려고 어그로끌었다.. 나루토 사스케 싸움수준 ㄹㅇ실화냐? 진짜 세계관최강자들의 싸움이다.. 그찐따같던 나루토가 맞나? 진짜 나루토는
            전설이다..진짜옛날에
            맨날나루토봘는데
            왕같은존재인 호카게 되서
            세계최강 전설적인 영웅이된나루토보면 진짜내가다 감격스럽고 나루토 노래부터 명장면까지 가슴울리는장면들이 뇌리에 스치면서 가슴이 웅장해진다.. 그리고 극장판 에 카카시앞에 운석날라오는 거대한
            걸
            사스케가 갑자기 순식간에
            나타나서 부숴버리곤 개간지나게 나루토가 없다면 마을을 지킬 자는 나밖에 없다 라며 바람처럼 사라진장면은 진짜 나루토처음부터 본사람이면 안울수가없더라 진짜 너무 감격스럽고 보루토를 최근에
            알았는데
            미안하다..
            지금20화보는데 진짜 나루토세대나와서 너무 감격스럽고 모두어엿하게 큰거보니 내가 다 뭔가 알수없는 추억이라해야되나 그런감정이 이상하게 얽혀있다.. 시노는 말이많아진거같다
            좋은선생이고..그리고
            보루토왜욕하냐 귀여운데
            나루토를보는것같다 성격도 닮았어 그리고버루토에 나루토사스케 둘이싸워도 이기는 신같은존재 나온다는게 사실임?? 그리고인터닛에 쳐봣는디 이거 ㄹㅇㄹㅇ 진짜팩트냐?? 저적이 보루토에 나오는
            신급괴물임?ㅡ
            나루토사스케
            합체한거봐라 진짜 ㅆㅂ 이거보고 개충격먹어가지고 와 소리 저절로 나오더라 ;; 진짜 저건 개오지는데.. 저게 ㄹㅇ이면 진짜 꼭봐야돼 진짜 세계도 파괴시키는거아니야 .. 와 진짜
            나루토사스케가
            저렇게
            되다니 진짜
            눈물나려고했다.. 버루토그라서 계속보는중인데 저거 ㄹㅇ이냐..? 하.. ㅆㅂ 사스케 보고싶다.. 진짜언제 이렇게 신급 최강들이 되었을까 옛날생각나고 나 중딩때생각나고 뭔가 슬프기도하고
            좋기도하고
            감격도하고
            여러가지감정이 복잡하네.. 아무튼 나루토는 진짜 애니중최거명작임..
          </a>
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
          <div class="order_item">
            <div class="order_item_num">19</div>
            <div class="order_item_category">Film Camera</div>
            <div class="order_item_name"><a href="">스메나 SMENA 35</a></div>
            <div class="order_item_quantity">1</div>
            <div class="order_item_price">169,000</div>
            <div class="order_item_stock">1</div>
          </div>
          <div class="order_item">
            <div class="order_item_num">219</div>
            <div class="order_item_category">Film Camera</div>
            <div class="order_item_name"><a href="">캐논 Canon Autoboy 3</a></div>
            <div class="order_item_quantity">1</div>
            <div class="order_item_price">249,000</div>
            <div class="order_item_stock">1</div>
          </div>
          <div class="order_item">
            <div class="order_item_num">413</div>
            <div class="order_item_category">Film</div>
            <div class="order_item_name"><a href="">Kodak 코닥 포트라 Portra 160/36</a></div>
            <div class="order_item_quantity">2</div>
            <div class="order_item_price">37,000</div>
            <div class="order_item_stock">50</div>
          </div>
          <div class="order_item">
            <div class="order_item_num">402</div>
            <div class="order_item_category">Film</div>
            <div class="order_item_name"><a href="">Kodak 코닥 컬러플러스 200/36</a></div>
            <div class="order_item_quantity">1</div>
            <div class="order_item_price">9,900</div>
            <div class="order_item_stock">50</div>
          </div>
        </div>
      </div>
    </div>
  </div>
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
