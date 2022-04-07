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

  <link rel="stylesheet" href="/resources/css/member-qna-reg.css">
  <!-- fontawesome v5 cdn -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />

  <title>다님 : 여행을 다니다</title>
  <script src="/resources/js/member-qna-reg.js" defer></script>
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
      <p>문의 등록</p>
      <form action="/qna/doReg" name="regQNA" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
        <div class="board-header">
          <div>
            <label for="ordernum">주문번호</label>
            <input type="text" name="ordernum" class="itemInput" id="ordernum" value="${ordernum}" readonly />
          </div>
          <div>
            <label for="category">문의종류</label>
            <select name="category" class="itemInput" id="category">
              <option value="none" selected>선택</option>
              <option value="shipping">배송</option>
              <option value="payment">결제</option>
              <option value="item">상품</option>
            </select>
          </div>
          <div>
            <label for="title">제목</label>
            <input type="text" name="title" class="itemInput" id="title" />
          </div>
          <input type="hidden" name="memnum" value="${user}">
        </div>
        <div class="board-body">
          <div class="board-body-header">
            <div>내용</div>
            <div class="textLengthWrap">
              <p class="textCount">0자</p>
              <p class="textTotal">/500자</p>
            </div>
          </div>
          <textarea name="txt" id="textBox" maxlength="500" placeholder="내용을 입력하세요."></textarea>
        </div>
        <div class="board-pics">
          <div>사진</div>
          <label for="file-input" id="upload">사진 업로드</label>
          <input type="file" id="file-input" accept="image/jpeg, image/jpg, image/gif, image/png" multiple hidden />
          <input type="file" name="files" id="file-input-copy" multiple hidden />
          <div class="uploaded">업로드된 파일</div>
          <div id="preview">
          </div>
        </div>
        <div class="board-footer">
          <a href="javascript:isValidForm()">등록하기</a>
          <a href="/admin/items">등록취소</a>
        </div>
      </form>
    </div>
  </div>
</section>
</body>

</html>
