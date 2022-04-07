<%@ page import="java.util.List" %>
<%@ page import="com.danim.items.beans.ItemsDTO" %>
<%@ page import="com.danim.files.beans.FilesEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<c:set var="path" value="${pageContext.request.contextPath}"/>--%>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <link rel="stylesheet" href="/resources/css/admin-items-update.css">
  <!-- fontawesome v5 cdn -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />

  <title>다님 : 여행을 다니다</title>
  <script src="/resources/js/admin-items-update.js" defer></script>
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
          <li><a href="/admin/qnas">1:1 문의</a></li>
        </ul>
      </div>
    </div>
    <div class="main-right">
      <p>상품 수정</p>
      <form action="/items/doUpdate" name="updateItem" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
        <div class="board-header">
          <div>
            <label for="itemnum">상품번호</label>
            <input type="text" name="itemnum" class="itemInput" id="itemnum" value="${item.itemnum}" readonly />
          </div>
          <div>
            <label for="category">카테고리</label>
            <select name="category" class="itemInput" id="category">
              <option value="Film Camera">Film Camera</option>
              <option value="Film">Film</option>
            </select>
            <script>
              $('#category').val('${item.category}').prop("selected", true);
            </script>
          </div>
          <div>
            <label for="mfr">제조사</label>
            <input type="text" name="mfr" class="itemInput" id="mfr" value="${item.mfr}" />
          </div>
          <div>
            <label for="name">상품명</label>
            <input type="text" name="name" class="itemInput" id="name" value="${item.name}" />
          </div>
          <div>
            <label for="info">설명</label>
            <input type="text" name="info" id="info" value="${item.info}"/>
          </div>
          <div>
            <label for="price">가격</label>
            <input type="text" name="price" class="itemInput" id="price" value="${item.price}"
                   oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" />
          </div>
            <div>
                <label for="stock">재고</label>
                <input type="text" name="stock" class="itemInput" id="stock" value="${item.stock}"
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
            <%
              List<FilesEntity> pics = (List<FilesEntity>) request.getAttribute("pics");
              // 사진 출력
              if (pics != null) {
                for (FilesEntity pic : pics) {
                  String picPath = "/resources/upload/" + pic.getStoredFileName();
            %>
            <div id="<%=pic.getStoredFileName()%>" class="localPic">
              <div>
                <img src="<%=picPath%>" style="width: 150px;">
              </div>
              <div>
                <%=pic.getOriginalFilename()%>
                <button data-index='<%=pic.getStoredFileName()%>' class='pic-remove' type="button" onclick="removePic(this.dataset.index)">X</button>
              </div>
            </div>
            <%
                }
              }
            %>
            <%
              List<String> exPics = (List<String>) request.getAttribute("exPics");
              // 사진 출력
              if (exPics != null) {
                int count = 1;
                for (String exPic : exPics) {
            %>
            <div id="<%=exPic%>" class="externalPic">
              <div>
                <img src="<%=exPic%>" style="width: 150px;">
              </div>
              <div>
                <%="외부사진" + count++%>
                <button data-index='<%=exPic%>' class='pic-remove' type="button" onclick="removePic(this.dataset.index)">X</button>
              </div>
            </div>
            <%
                }
              }
            %>
          </div>
        </div>
        <input type="hidden" name="localPics">
        <input type="hidden" name="externalPics">
        <div class="board-footer">
          <a href="javascript:updateForm()">수정하기</a>
          <a href="/admin/items">수정취소</a>
        </div>
      </form>
    </div>
  </div>
</section>
</body>

</html>
