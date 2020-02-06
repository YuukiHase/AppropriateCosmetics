<%-- 
    Document   : listproduct
    Created on : Dec 11, 2019, 2:51:14 PM
    Author     : Admin
--%>

<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Cosmetics</title>
        <link rel="stylesheet" href="css/main.css"/>
        <link rel="icon" href="img/icon.jpeg">
        <c:set var="productdoc" value="${sessionScope.LISTPRODUCTDOC}" />
        <x:set var="listProduct" select="$productdoc//product" />
        <script>
            var list = new Array();
            var pageList = new Array();
            var currentPage = 1;
            var numberPerPage = 15;
            var numberOfPages = 0;
            function makeList() {
            <x:forEach var="productdetail" select="$listProduct" varStatus="counter">
                product1 = new Object();
                product1.id = "<x:out select="$productdetail/productId"/>";
                product1.name = "<x:out select="$productdetail/name"/>";
                product1.price = <x:out select="$productdetail/price"/>;
                product1.imageLink = "<x:out select="$productdetail/imageLink"/>";
                product1.brand = "<x:out select="$productdetail/brand"/>";
                product1.categoryName = "<x:out select="$productdetail/categoryName"/>";
                list.push(product1);
            </x:forEach>
                numberOfPages = getNumberOfPages();
            }
            function getNumberOfPages() {
                return Math.ceil(list.length / numberPerPage);
            }

            function nextPage() {
                currentPage += 1;
                loadList();
            }

            function previousPage() {
                currentPage -= 1;
                loadList();
            }

            function firstPage() {
                currentPage = 1;
                loadList();
            }

            function lastPage() {
                currentPage = numberOfPages;
                loadList();
            }

            function loadList() {
                var begin = ((currentPage - 1) * numberPerPage);
                var end = begin + numberPerPage;

                pageList = list.slice(begin, end);
                drawList();
                check();
            }

            function check() {
                document.getElementById("next").disabled = currentPage == numberOfPages ? true : false;
                document.getElementById("previous").disabled = currentPage == 1 ? true : false;
                document.getElementById("first").disabled = currentPage == 1 ? true : false;
                document.getElementById("last").disabled = currentPage == numberOfPages ? true : false;
            }

            function drawList() {
                var productlistx = document.getElementById("listproduct");
                productlistx.innerHTML = "";
                for (r = 0; r < pageList.length; r++) {
                    var div = document.createElement("div");
                    div.setAttribute("class", "product");
                    var img = document.createElement("img");
                    img.setAttribute("class", "imgproduct");
                    img.setAttribute("src", pageList[r].imageLink);
                    var pname = document.createElement("p");
                    pname.setAttribute("class", "productname");
                    pname.innerHTML = pageList[r].name;
                    var pcate = document.createElement("p");
                    pcate.setAttribute("class", "productcate");
                    pcate.innerHTML = pageList[r].categoryName;
                    var pbrand = document.createElement("p");
                    pbrand.setAttribute("class", "productbrand");
                    pbrand.innerHTML = pageList[r].brand;
                    var pprice = document.createElement("p");
                    pprice.setAttribute("class", "productprice");
                    pprice.innerHTML = pageList[r].price.toLocaleString('de-DE', {
                        minimumFractionDigits: 0
                    }) + "đ"
                            ;
                    var linkDetail = document.createElement("a");
                    linkDetail.setAttribute("style", "color:black");
                    linkDetail.setAttribute("href", "DetailServlet?productId=" + pageList[r].id);
                    linkDetail.appendChild(pname);
                    div.appendChild(img);
                    div.appendChild(pname);
                    div.appendChild(pcate);
                    div.appendChild(pbrand);
                    div.appendChild(pprice);
                    linkDetail.appendChild(div)
                    productlistx.appendChild(linkDetail);
                    document.getElementById("paging").value = currentPage + "/" + numberOfPages;
                }
            }
            function load() {
                makeList();
                loadList();
            }

            window.onload = load;
        </script>
    </head>
    <div class="header">
        <div class="container">
            <div style="float: right"><img alt="a" src="img/phone.png" class="himg"/><a href="#" class="hlink">0523527327</a></div>
            <div style="float: right"><img alt="a" src="img/facb.png" class="himg"/><a href="#" class="hlink">&nbsp;&nbsp;BestComestic</a></div>
            <div style="float: left"><a href="#" class="hlink">Destination to find the best Cosmetics in the world</a></div>
        </div>
    </div>
    <div class="body">
        <c:set var="concernList" value="${sessionScope.CONCERNSLIST}" />
        <x:set var="listConcern" select="$concernList//concern" />
        <c:set var="skintypeList" value="${sessionScope.SKINTYPELIST}" />
        <x:set var="listSkintype" select="$skintypeList//skintype" />
        <c:set var="categoryList" value="${sessionScope.CATEGORYLIST}" />
        <x:set var="listCategory" select="$categoryList//category" />
        <div class="top">
            <div class="logo">
                <p id="logoname">Cosmetics</p>
            </div>
            <div class="search">
                <form style="line-height: 85px;">
                    <input class="textsearch" placeholder="Tìm kiếm: kem, sữa dưỡng da,..."/>
                    <input class="buttonsearch" type="button" value="Tìm Kiếm">
                </form>
            </div>
            <div class="login">
                <c:if test="${sessionScope.USERNAME==null}">
                    <a class="linkdangnhap" href="login.html">Đăng nhập / Đăng ký</a>
                </c:if>
                <c:if test="${sessionScope.USERNAME!=null}">
                    <a href="survey.jsp">hi, <c:out value="${sessionScope.USERNAME}"/></a>&nbsp;&nbsp;&nbsp;<a class="linkdangnhap" href="LogoutServlet">Đăng Xuất</a>
                </c:if>
            </div>
        </div>
        <div class="menu">
            <ul style="padding-left: 185px">
                <li><a href="index.jsp">Home</a></li>
                <li class="dropdown"><a href="javascript:void(0)" class="dropbtn">Category</a>
                    <div class="dropdown-content">
                        <x:forEach var="category" select="$listCategory" varStatus="counter">
                            <a href="ListServlet?categoryId=<x:out select="$category/categoryId"/>"><x:out select="$category/categoryName"/></a>
                        </x:forEach>
                    </div>
                </li>
                <li class="dropdown"><a href="javascript:void(0)" class="dropbtn">Skin Type</a>
                    <div class="dropdown-content">
                        <x:forEach var="skintype" select="$listSkintype" varStatus="counter">
                            <a href="ListServlet?skintypeId=<x:out select="$skintype/skinTypeId"/>"><x:out select="$skintype/skinTypeName"/></a>
                        </x:forEach>
                    </div>
                </li>
                <li class="dropdown"><a href="javascript:void(0)" class="dropbtn">Skin Concern</a>
                    <div class="dropdown-content">
                        <x:forEach var="concern" select="$listConcern" varStatus="counter">
                            <a href="ListServlet?concernId=<x:out select="$concern/concernId"/>"><x:out select="$concern/concernName"/></a>
                        </x:forEach>
                    </div>
                </li>
            </ul>
        </div>
    </div>
    <div class="main">
        <div class="mainlist">
            <div id="listproduct">
            </div>
            <div style="float: left;width: 100%;padding-top: 40px;padding-bottom: 20px">
                <input class="buttonPage" type="button" id="first" onclick="firstPage()" value="first" />
                <input class="buttonPage" type="button" id="previous" onclick="previousPage()" value="previous" />
                <input style="text-align: center" type="text" readonly="true" id="paging"/>
                <input class="buttonPage" type="button" id="next" onclick="nextPage()" value="next" />
                <input class="buttonPage" type="button" id="last" onclick="lastPage()" value="last" />
            </div>
        </div>
    </div>
    <div class="footer"><img style="height: 30px" src="img/dathongbao.png"/>
        <p>© 2017 - Bản quyền của Công Ty Cao Văn Phú</p>
        <p>Giấy chứng nhận ĐKKD số 0314414731 do Sở Kế hoạch và Đầu tư Tp. HCM cấp ngày 19/05/2017</p>
    </div>
</body>
</html>

