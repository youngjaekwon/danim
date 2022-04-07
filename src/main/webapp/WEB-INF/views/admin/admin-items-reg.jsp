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

  <link rel="stylesheet" href="/resources/css/admin-items-reg.css">
  <!-- fontawesome v5 cdn -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />

  <title>다님 : 여행을 다니다</title>
  <script src="/resources/js/admin-items-reg.js" defer></script>
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
      <p>상품 등록</p>
      <form action="/items/doReg" name="regItem" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
        <div class="board-header">
          <div>
            <label for="itemnum">상품번호</label>
            <input type="text" name="itemnum" class="itemInput" id="itemnum" value="자동생성" disabled />
          </div>
          <div>
            <label for="category">카테고리</label>
            <select name="category" class="itemInput" id="category">
              <option value="Film Camera">Film Camera</option>
              <option value="Film">Film</option>
            </select>
          </div>
          <div>
            <label for="mfr">제조사</label>
            <input type="text" name="mfr" class="itemInput" id="mfr" />
          </div>
          <div>
            <label for="name">상품명</label>
            <input type="text" name="name" class="itemInput" id="name" />
          </div>
          <div>
            <label for="info">설명</label>
            <input type="text" name="info" id="info" />
          </div>
          <div>
            <label for="price">가격</label>
            <input type="text" name="price" class="itemInput" id="price"
                   oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" />
          </div>
            <div>
                <label for="stock">재고</label>
                <input type="text" name="stock" class="itemInput" id="stock"
                       oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" />
            </div>
        </div>
        <div class="board-body">
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
