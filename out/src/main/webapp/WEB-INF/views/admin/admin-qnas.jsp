<%@ page import="java.util.List" %>
<%@ page import="com.danim.qna.beans.QnaVO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<c:set var="path" value="${pageContext.request.contextPath}"/>--%>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <link rel="stylesheet" href="/resources/css/admin-qnas.css">
  <!-- fontawesome v5 cdn -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />

  <title>다님 : 여행을 다니다</title>
  <script src="/resources/js/admin-qnas.js" defer></script>
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
          <li><a href="/admin/qnas">문의 관리</a></li>
        </ul>
      </div>
    </div>
    <div class="main-right">
      <p>문의 관리</p>
      <div class="qnas_header">
        <div class="qnas_header_top">
          <div class="qnas_filter">
            <form action="/admin/qnas" method="GET" name="filter">
              <label for="category">문의 종류 :</label>
              <select name="category" id="category">
                <option value="%%" selected>전체</option>
                <option value="shipping">배송</option>
                <option value="payment">결제</option>
                <option value="item">상품</option>
              </select>
              <label for="state">1:1문의 상태 :</label>
              <select name="state" id="state">
                <option value="%%" selected>전체</option>
                <option value="00">답변 대기</option>
                <option value="01">답변 완료</option>
              </select>
              <label for="sort">정렬 방법 :</label>
              <select name="sort" id="sort">
                <option value="QNANUM">문의번호 오름차순</option>
                <option value="QNANUM DESC" selected>문의번호 내림차순</option>
                <option value="QDATE">문의일 오름차순</option>
                <option value="QDATE DESC">문의일 내림차순</option>
                <option value="STATE">문의상태 오름차순</option>
                <option value="STATE DESC">문의상태 내림차순</option>
                <option value="CATEGORY">문의종류 오름차순</option>
                <option value="CATEGORY DESC">문의종류 내림차순</option>
              </select>
              <input type="hidden" name="keyword" value="${keyword}"/>
              <input type="hidden" name="page"/>
            </form>
          </div>
          <div class="qnas_search">
            <form action="/admin/qnas" name="qnas_search">
              <input type="text" name="keyword" placeholder="검색어를 입력해주세요.">
              <input type="submit" value="검색">
            </form>
            <form action="/admin/qnas" name="reset">
              <input type="submit" value="초기화">
            </form>
          </div>
        </div>
        <div class="qnas_title">
          <div class="qnas_header_num">문의번호</div>
          <div class="qnas_header_normal">고객명</div>
          <div class="qnas_header_title">제목</div>
          <div class="qnas_header_normal">종류</div>
          <div class="qnas_header_normal">상태</div>
          <div class="qnas_header_normal">문의일</div>
        </div>
      </div>
      <div class="qnas_list">
        <%
          List<QnaVO> itemList = (List<QnaVO>) request.getAttribute("itemList");
          if (itemList.isEmpty()){
        %>
          <div class="emptyList">문의리스트가 비었습니다.</div>
        <%
          } else {
              for(int i = 0; i < itemList.size(); i++){
                QnaVO qna = itemList.get(i);
                String qnanum = qna.getQnanum();
        %>
        <div class="qna">
          <div class="qna_num"><a href=""><%=qnanum%></a></div>
          <div class="qna_member"><a href=""><%=qna.getUserName()%></a></div>
          <div class="qna_title">
            <a href=""><%=qna.getTitle() + " [" + qna.getCommentsNum() + "]"%></a>
          </div>
          <div class="qna_category"><%=qna.getCategory()%></div>
          <div class="qna_state"><%=qna.getState()%></div>
          <div class="qna_date"><%=qna.getQdate()%></div>
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
</section>
<script>
  // filter 기본 체크
  $('[name=filter] [name=state]').val('${state}').prop("selected", true);
  $('[name=filter] [name=category]').val('${category}').prop("selected", true);
  $('[name=filter] [name=sort]').val('${sorting}').prop("selected", true);
  $('[name=qnas_search] [name=keyword]').val('${keyword}');
</script>
</body>

</html>
