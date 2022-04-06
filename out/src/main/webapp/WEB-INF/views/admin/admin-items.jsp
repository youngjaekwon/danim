<%@ page import="java.util.List" %>
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

  <link rel="stylesheet" href="/resources/css/admin-items.css">
  <!-- fontawesome v5 cdn -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />

  <title>다님 : 여행을 다니다</title>
  <script src="/resources/js/admin-items.js" defer></script>
  <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</head>

<body>
<%@include file ="../common/header.jsp" %>
<section class="main">
  <div class="main-inner">
    <div class="main-left">
      <div class="main-nav">
        <ul>
          <li><a href="/admin/orders">주문 관리</a></li>
          <li><a href="/admin/items">상품 관리</a></li>
          <li><a href="/admin/members">회원 관리</a></li>
          <li><a href="#">1:1 문의</a></li>
        </ul>
      </div>
    </div>
    <div class="main-right">
      <p>상품 관리</p>
      <div class="items_header">
        <div class="items_header_top">
          <div class="items_filter">
            <form action="/admin/items" method="GET" name="filter">
              <label for="category">카테고리 :</label>
              <select name="category" id="category">
                <option value="%%" selected>전체</option>
                <option value="Film Camera">Film Camera</option>
                <option value="Film">Film</option>
              </select>
              <label for="stock">재고 상태 :</label>
              <select name="stock" id="stock">
                <option value=">-1" selected>전체</option>
                <option value="<5">5개 미만</option>
                <option value="=0">없음</option>
              </select>
              <label for="sort">정렬 방법 :</label>
              <select name="sort" id="sort">
                <option value="ITEMNUM" selected>상품번호 오름차순</option>
                <option value="ITEMNUM DESC">상품번호 내림차순</option>
                <option value="CATEGORY">카테고리 오름차순</option>
                <option value="CATEGORY DESC">카테고리 내림차순</option>
                <option value="MFR">제조사 오름차순</option>
                <option value="MFR DESC">제조사 내림차순</option>
                <option value="NAME">상품명 오름차순</option>
                <option value="NAME DESC">상품명 내림차순</option>
                <option value="TO_NUMBER(PRICE)">가격 오름차순</option>
                <option value="TO_NUMBER(PRICE) DESC">가격 내림차순</option>
                <option value="TO_NUMBER(STOCK)">재고 오름차순</option>
                <option value="TO_NUMBER(STOCK) DESC">재고 내림차순</option>
              </select>
              <input type="hidden" name="page" />
              <input type="hidden" name="keyword" />
            </form>
          </div>
          <div class="items_search">
            <form action="/admin/items" name="items_search">
              <input type="text" name="keyword" placeholder="검색어 입력">
              <input type="submit" value="검색">
            </form>
            <form action="/admin/items" name="reset">
              <input type="submit" value="초기화">
            </form>
          </div>
        </div>
        <div class="items_title">
          <div class="items_num">상품번호</div>
          <div class="items_normal">카테고리</div>
          <div class="items_normal">제조사</div>
          <div class="items_name">상품명</div>
          <div class="items_normal">설명</div>
          <div class="items_normal">가격</div>
          <div class="items_normal">재고</div>
        </div>
      </div>
      <div class="items_list">
        <%
          List<ItemsDTO> itemList = (List<ItemsDTO>) request.getAttribute("itemList");
          if (itemList.isEmpty()){
        %>
        <div class="emptyList">회원리스트가 비었습니다.</div>
        <%
        } else {
          for(int i = 0; i < itemList.size(); i++){
            ItemsDTO itemsDTO = itemList.get(i);
            String itemnum = itemsDTO.getItemnum();
        %>
        <div class="item">
          <div class="items_num"><%=itemnum%></div>
          <div class="items_normal"><%=itemsDTO.getCategory()%></div>
          <div class="items_normal"><a href="javascript:toggleItemModal('<%=itemnum%>')"><%=itemsDTO.getMfr()%></a></div>
          <div class="items_name"><a href="javascript:toggleItemModal('<%=itemnum%>')"><%=itemsDTO.getName()%></a></div>
          <div class="items_normal"><%=itemsDTO.getInfo() != null?itemsDTO.getInfo():"-"%></div>
          <div class="items_normal"><%=itemsDTO.getFormattedPrice()%></div>
          <div class="items_normal"><%=itemsDTO.getStock()%></div>
        </div>
        <%
            }
          }
        %>
      </div>
      <div class="board-footer">
        <a href="/admin/items_reg">상품등록</a>
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
  <%
    for(int i = 0; i < itemList.size(); i++){
      ItemsDTO itemsDTO = itemList.get(i);
      String itemnum = itemsDTO.getItemnum();
  %>
  <div class="item_modal" id="item_modal<%=itemnum%>" style="display: none">
    <div class="item_modal_inside">
      <div class="item_modal_header">
        <div>상품정보</div>
        <div class="item_modal_header_right">
          <a href="javascript:toggleItemModal('<%=itemnum%>')"><i class="far fa-times-circle"></i></a>
        </div>
      </div>
      <div class="item_modal_body">
        <div class="item_info_table">
          <div class="item_info_table_header">
          </div>
          <div class="item_info_table_line1">
            <div class="item_info_table_line1_info">
              <div>
                <div>상품번호</div>
                <div><%=itemnum%></div>
              </div>
              <div>
                <div>카테고리</div>
                <div><%=itemsDTO.getCategory()%></div>
              </div>
            </div>
            <div class="item_info_table_line1_addr">
              <div>
                상품명
              </div>
              <div>
                <%=itemsDTO.getName() + " " + itemsDTO.getInfo()%>
              </div>
            </div>
          </div>
          <div class="item_info_table_line2">
            <div>
              <div>제조사</div>
              <div><%=itemsDTO.getMfr()%></div>
            </div>
            <div>
              <div>가격</div>
              <div>
                <%=itemsDTO.getFormattedPrice()%>
              </div>
            </div>
          </div>
          <div class="item_info_table_line3">
            <div>
              <div>재고</div>
              <div>
                <%=itemsDTO.getStock()%>
              </div>
            </div>
            <div id="correction">
              <button type="button" onclick="location.href='/admin/items_update?itemnum=<%=itemnum%>'">수정</button>
              <button>상품 삭제</button>
            </div>
          </div>
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
  $('[name=filter] [name=category]').val('${category}').prop("selected", true);
  $('[name=filter] [name=stock]').val('${stock}').prop("selected", true);
  $('[name=filter] [name=sort]').val('${sorting}').prop("selected", true);
  $('[name=members_search] [name=keyword]').val('${keyword}');
</script>
</body>

</html>
