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

  <link rel="stylesheet" href="/resources/css/member-qnalist.css">
  <!-- fontawesome v5 cdn -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />

  <title>다님 : 여행을 다니다</title>
  <script src="/resources/js/member-qnalist.js" defer></script>
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
      <p>1:1 문의</p>
      <div class="list-title">
        <span class="title-num">주문번호</span>
        <span class="title-title">제목</span>
        <span class="title-category">종류</span>
        <span class="title-state">상태</span>
        <span class="title-date">등록일</span>
      </div>
      <div class="list-items">
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
        <div class="item">
          <div class="item-num">
            <a href="/member/qna?qnanum=<%=qna.getQnanum()%>"><%=qna.getOrdernum()%></a>
          </div>
          <div class="item-info">
            <a href="/member/qna?qnanum=<%=qna.getQnanum()%>"><%=qna.getTitle() + " [" + qna.getCommentsNum() + "]"%></a>
          </div>
          <div class="item-category">
            <%=qna.getCategory()%>
          </div>
          <div class="item-state">
            <div><%=qna.getState()%></div>
          </div>
          <div class="item-date"><%=qna.getQdate()%></div>
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
          <a href="/member/qnaList?page=${prevPage}" class="page-prv-btn">
            <i class="fas fa-angle-double-left"></i>
          </a>
        </div>
        <div class="pages">
          <%
              for (int i = pageStart; i <= pageEnd; i++){
          %>
          <a href="/member/qnaList?page=<%=i%>"><%=i%></a>
          <%
              }
          %>
        </div>
        <div class="page-next">
          <a href="/member/qnaList?page=${nextPage}" class="page-next-btn">
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
</body>

</html>
