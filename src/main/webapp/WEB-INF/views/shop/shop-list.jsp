<%@ page import="com.danim.items.beans.ItemsDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<c:set var="path" value="${pageContext.request.contextPath}"/>--%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="/resources/css/shop-list.css">
    <!-- fontawesome v5 cdn -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />

    <title>다님 : 여행을 다니다</title>
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</head>

<body>
<%@include file ="../common/header.jsp" %>
<section class="main">
    <div class="main-inner">
        <div class="main-category">
            <ul>
                <li>◯<a href="#"> Carrier Bag</a></li>
                <li>◯<a href="#"> Hat</a></li>
                <li>◯<a href="#"> ACC</a></li>
            </ul>
        </div>
        <div class="main-items">
            <%
                List<ItemsDTO> itemList = (List<ItemsDTO>) request.getAttribute("itemList");
                for(int i = 0; i < itemList.size(); i++){
                    ItemsDTO item = itemList.get(i);
                    String itemnum = item.getItemnum();
                    if (i == 0 || i == 6 || i == 12) {
            %>
            <div class="main-items-line<%=i / 6 + 1%>">
                <%
                    }
                %>
                <a href="/shop/item?item=<%=itemnum%>" class="item">
                    <div class="item-img" id="img<%=itemnum%>"></div>
                    <script>$('#img<%=itemnum%>').css("background-image", "url('<%=item.getThumbnail()%>')")</script>
                    <div><%=item.getMfr()%> <%=item.getName()%></div>
                    <div><%=item.getFormattedPrice()%></div>
                </a>
                <%
                    if (i == 5 || i == 11 || i == itemList.size() - 1){
                %>
            </div>
            <%
                    }
                }
            %>
        </div>
        <div class="paging">
            <%
                String category = request.getParameter("category"); // 카테고리
                if (category == null) category = ""; // 없는경우 빈문자열로 변환
            %>
            <div class="page-prv">
                <a href="/shop/list?category=<%=category%>&page=${prevPage}" class="page-prv-btn">
                    <i class="fas fa-angle-double-left"></i>
                </a>
            </div>
            <div class="pages">
            <%
                int pageStart = (int) request.getAttribute("pageStart"); // 페이지 목록중 시작
                int pageEnd = (int) request.getAttribute("pageEnd"); // // 페이지 목록중 끝
                for (int i = pageStart; i <= pageEnd; i++){
            %>
                <a href="/shop/list?category=<%=category%>&page=<%=i%>"><%=i%></a>
            <%
                }
            %>
            </div>
            <div class="page-next">
                <a href="/shop/list?category=<%=category%>&page=${nextPage}" class="page-next-btn">
                    <i class="fas fa-angle-double-right"></i>
                </a>
            </div>
        </div>
    </div>
</section>
</body>

</html>
