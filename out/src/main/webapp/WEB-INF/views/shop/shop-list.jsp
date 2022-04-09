<%@ page import="com.danim.items.beans.ItemsDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <script src="/resources/js/shop-list.js" defer></script>
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</head>

<body>
<%@include file ="../common/header.jsp" %>
<section class="main">
    <div class="main-inner">
        <form action="/shop/list" method="get" name="filter">
            <div class="main-category">
                <ul>
                    <li><input type="checkbox" name="category" id="ALL" class="filterCheckBox" value="%%" onchange="listAll()"/><label for="ALL">전체</label></li>
                    <li><input type="checkbox" name="category" id="FC" class="filterCheckBox" value="Film Camera" onchange="filtering()"/><label for="FC">Film Camera</label></li>
                    <li><input type="checkbox" name="category" id="Film" class="filterCheckBox" value="Film" onchange="filtering()"/><label for="Film">Film</label></li>
                </ul>
            </div>
            <div class="items_filter">
                <select name="sort" id="sort">
                    <option value="ITEMNUM" selected>상품번호 오름차순</option>
                    <option value="ITEMNUM DESC">상품번호 내림차순</option>
                    <option value="TO_NUMBER(PRICE)">낮은 가격순</option>
                    <option value="TO_NUMBER(PRICE) DESC">높은 가격순</option>
                    <option value="MFR">제조사 오름차순</option>
                    <option value="MFR DESC">제조사 내림차순</option>
                    <option value="NAME">상품명 오름차순</option>
                    <option value="NAME DESC">상품명 내림차순</option>
                </select>
                <label for="sort">정렬 방법 :</label>
                <input type="hidden" name="page"/>
                <input type="hidden" name="keyword"/>
            </div>
        </form>
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
                <a href="javascript:paging('${prevPage}')" class="page-prv-btn">
                    <i class="fas fa-angle-double-left"></i>
                </a>
            </div>
            <div class="pages">
            <%
                int pageStart = (int) request.getAttribute("pageStart"); // 페이지 목록중 시작
                int pageEnd = (int) request.getAttribute("pageEnd"); // // 페이지 목록중 끝
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
        </div>
    </div>
</section>
<script>
    // filter 기본 체크
    let category =  '${category}';
    console.log(category);
    if (category != '' && category != '[]'){
        category = category.substring(1, category.length - 1).split(',');
        for (let i = 0; i < category.length; i++) {
            const checkboxs = document.getElementsByClassName('filterCheckBox');
            Array.prototype.forEach.call(checkboxs, checkbox => {
                if (checkbox.value == category[i].trim()){
                    checkbox.checked = true;
                }
            })
        }
    } else if (category == '[]'){
        document.getElementById('ALL').checked = true;
    }
    $('[name=filter] [name=sort]').val('${sorting}').prop("selected", true);
    $('[name=members_search] [name=keyword]').val('${keyword}');
</script>
</body>

</html>
