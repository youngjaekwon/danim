<%@ page import="java.util.List" %>
<%@ page import="com.danim.items.beans.ItemsDTO" %>
<%@ page import="com.danim.files.beans.FilesEntity" %>
<%@ page import="com.danim.comments.beans.CommentsVO" %>
<%@ page import="com.danim.qna.beans.QnaVO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--<c:set var="path" value="${pageContext.request.contextPath}"/>--%>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <link rel="stylesheet" href="/resources/css/member-qna-detail.css">
  <!-- fontawesome v5 cdn -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />

  <title>다님 : 여행을 다니다</title>
  <script src="/resources/js/member-qna-detail.js" defer></script>
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
      <p>${qna.title}</p>
      <div class="board-header">
        <div>
          <div>주문번호</div>
          <div>${qna.ordernum}</div>
        </div>
        <div>
          <div>문의종류</div>
          <div>${qna.category}</div>
        </div>
        <div>
          <div>작성일</div>
          <div>${qna.qdate}</div>
        </div>
      </div>
      <div class="board-body">
        <div>내용</div>
        ${qna.txt}
      </div>
      <div class="board-pics">
        <div>사진</div>
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
              <a href="javascript:downloadFile('<%=pic.getFnum()%>')"><img src="<%=picPath%>" style="width: 150px;"/></a>
            </div>
            <div>
              <a href="javascript:downloadFile('<%=pic.getFnum()%>')" style="text-decoration: none; color: black"><%=pic.getOriginalFilename()%></a>
            </div>
          </div>
          <%
              }
            }
          %>
        </div>
        <form action="/files/download" method="post" name="downloadForm">
          <input type="hidden" name="fnum">
        </form>
      </div>
      <form name="regComment">
        <div class="comments-header">
          <label for="textBox">댓글작성</label>
          <div class="textLengthWrap">
            <p class="textCount">0자</p>
            <p class="textTotal">/100자</p>
          </div>
        </div>
        <div class="comments-textBox">
          <textarea name="txt" id="textBox" maxlength="100" placeholder="내용을 입력하세요."></textarea>
          <div>
            <button type="button" onclick="doRegComment()">작성</button>
          </div>
        </div>
        <input type="hidden" name="memnum" value="${member.memnum}"/>
        <input type="hidden" name="role" value="${role}"/>
        <input type="hidden" name="qnanum" value="${qna.qnanum}"/>
      </form>
      <div class="comments">
        <%
          List<CommentsVO> comments = (List<CommentsVO>) request.getAttribute("comments");
          // 사진 출력
          if (comments != null) {
            for (CommentsVO comment : comments) {
              String cnum = comment.getCnum();
        %>
        <div class="comment" id="<%=cnum%>">
          <div class="comment_name"><%=comment.getName()%></div>
          <div class="comment_txt"><%=comment.getTxt()%></div>
          <div class="comment_delete">
            <button data-index="<%=cnum%>" onclick="doDelComment(this.dataset.index)">삭제</button>
          </div>
          <div class="comment_date"><%=comment.getDate()%></div>
        </div>
        <%
            }
          }
        %>
      </div>
    </div>
  </div>
</section>
</body>

</html>
