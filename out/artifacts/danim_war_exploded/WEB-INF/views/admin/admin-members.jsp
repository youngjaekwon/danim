<%@ page import="java.util.List" %>
<%@ page import="com.danim.orders.beans.Orders" %>
<%@ page import="com.danim.orders.beans.OrdersVO" %>
<%@ page import="com.danim.shop.beans.ItemsDTO" %>
<%@ page import="com.danim.member.beans.Member" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<c:set var="path" value="${pageContext.request.contextPath}"/>--%>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

  <link rel="stylesheet" href="/resources/css/admin-members.css">
  <!-- fontawesome v5 cdn -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />

  <title>다님 : 여행을 다니다</title>
  <script src="/resources/js/admin-members.js" defer></script>
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
          <li><a href="#">상품 관리</a></li>
          <li><a href="/admin/members">회원 관리</a></li>
          <li><a href="#">1:1 문의</a></li>
        </ul>
      </div>
    </div>
    <div class="main-right">
      <p>회원 관리</p>
      <div class="members_header">
        <div class="members_header_top">
          <div class="members_filter">
            <form action="/admin/members" method="GET" name="filter">
              <label for="state">회원 상태 :</label>
              <select name="state" id="state">
                <option value="%%" selected>전체</option>
                <option value="0">일반회원</option>
                <option value="1">관리자</option>
              </select>
              <label for="sort">정렬 방법 :</label>
              <select name="sort" id="sort">
                <option value="MEMNUM">회원번호 오름차순</option>
                <option value="MEMNUM DESC" selected>회원번호 내림차순</option>
              </select>
              <input type="hidden" name="page" />
              <input type="hidden" name="keyword" />
            </form>
          </div>
          <div class="members_search">
            <form action="/admin/members" name="members_search">
              <input type="text" name="keyword" placeholder="검색어 입력">
              <input type="submit" value="검색">
            </form>
            <form action="/admin/members" name="reset">
              <input type="submit" value="초기화">
            </form>
          </div>
        </div>
        <div class="members_title">
          <div class="members_num">회원번호</div>
          <div class="members_email">이메일</div>
          <div class="members_normal">이름</div>
          <div class="members_normal">닉네임</div>
          <div class="members_normal">주소</div>
          <div class="members_normal">전화번호</div>
          <div class="members_normal">회원상태</div>
        </div>
      </div>
      <div class="members_list">
        <%
          List<Member> itemList = (List<Member>) request.getAttribute("itemList");
          if (itemList.isEmpty()){
        %>
        <div class="emptyList">주문리스트가 비었습니다.</div>
        <%
        } else {
          for(int i = 0; i < itemList.size(); i++){
            Member member = itemList.get(i);
            String memnum = member.getMemnum();
        %>
        <div class="member">
          <div class="members_num"><%=memnum%></div>
          <div class="members_email"><a href="javascript:toggleMemberModal('<%=memnum%>')"><%=member.getEmail()%></a></div>
          <div class="members_normal"><a href="javascript:toggleMemberModal('<%=memnum%>')"><%=member.getName()%></a></div>
          <div class="members_normal"><%=member.getNickname()%></div>
          <div class="members_normal"><%=member.getAddr()%></div>
          <div class="members_normal"><%=member.getMobile()%></div>
          <div class="members_normal"><%=member.getIsAdmin() == 0 ? "일반회원" : "관리자"%></div>
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
  <%
    for(int i = 0; i < itemList.size(); i++){
      Member member = itemList.get(i);
      String memnum = member.getMemnum();
  %>
  <div class="member_modal" id="member_modal<%=memnum%>" style="display: none">
    <div class="member_modal_inside">
      <div class="member_modal_header">
        <div>회원정보</div>
        <div class="member_modal_header_right">
          <button >게시글 관리</button>
          <button onclick="location.href='/admin/orders?keyword=<%=memnum%>'">주문 관리</button>
          <button >1:1문의 관리</button>
          <a href="javascript:toggleMemberModal('<%=memnum%>')"><i class="far fa-times-circle"></i></a>
        </div>
      </div>
      <div class="member_modal_body">
        <div class="member_info_table">
          <div class="member_info_table_header">
          </div>
          <div class="member_info_table_line1">
            <div class="member_info_table_line1_info">
              <div>
                <div>회원번호</div>
                <div><%=memnum%></div>
              </div>
              <div>
                <div>이메일</div>
                <div><%=member.getEmail()%></div>
              </div>
            </div>
            <div class="member_info_table_line1_addr">
              <div>
                주소
              </div>
              <div>
                <%=member.getZipcode()%> <%=member.getAddr()%>
              </div>
            </div>
          </div>
          <div class="member_info_table_line2">
            <div>
              <div>이름</div>
              <div><%=member.getName()%></div>
            </div>
            <div>
              <div>전화번호</div>
              <div>
                <%=member.getMobile()%>
              </div>
            </div>
          </div>
          <div class="member_info_table_line3">
            <div>
              <div>닉네임</div>
              <div>
                <%=member.getNickname()%>
                <button>변경</button>
              </div>
            </div>
            <div>
              <div>회원상태</div>
              <div>
                <%=member.getRole().equals("ROLE_MEMBER") ? "일반회원" : "관리자"%>
                <button>변경</button>
              </div>
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
  $('[name=filter] [name=state]').val('${state}').prop("selected", true);
  $('[name=filter] [name=sort]').val('${sorting}').prop("selected", true);
  $('[name=members_search] [name=keyword]').val('${keyword}');
</script>
</body>

</html>
