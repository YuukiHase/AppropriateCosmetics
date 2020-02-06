<%-- 
    Document   : detail
    Created on : Dec 12, 2019, 8:43:30 PM
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
        <c:set var="productdetail" value="${sessionScope.DETAILPRODUCT}" />
        <x:set var="product" select="$productdetail//product" />
        <c:set var="listtop5productsamecate" value="${sessionScope.TOP5SAMECATE}" />
        <x:set var="topsame" select="$listtop5productsamecate//product" />
        <c:set var="listtop5product" value="${sessionScope.TOP5PRODUCT}" />
        <x:set var="top5product" select="$listtop5product//product" />
        <c:set var="listproductconcern" value="${sessionScope.DETAILPRODUCTCONCERNS}" />
        <x:set var="listconcern2" select="$listproductconcern//concern" />
        <c:set var="listproductskintype" value="${sessionScope.DETAILPRODUCTSKINTYPES}" />
        <x:set var="listskintype2" select="$listproductskintype//skintype" />
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

            <div class="listproduct">
                <div class="imgdetail"> <img style="width: 400px;" src="<x:out select="$product/imageLink"/>"/> </div>
                <div class="detail">
                    <div class="detailname"><x:out select="$product/name"/></div>
                    <div style="float: left;width: 100%">
                        <div style="float: left;width: 50%">
                            <p class="textdetail">category: <x:out select="$product/categoryName"/></p>
                            <p class="textdetail">Thương hiệu: <x:out select="$product/brand"/></p>

                        </div>
                        <div style="float: left;width: 50%">
                            <p class="textdetail">Đơn vị: <x:out select="$product/volume"/></p>

                        </div>
                    </div>
                    <div style="float: left;width: 100%">
                        <p class="textdetail">xuất xứ: <x:out select="$product/origin"/></p>
                        <p class="textdetail">Loại da:
                            <x:forEach var="skintypes" select="$listskintype2" varStatus="counter">
                                <x:out select="$skintypes/skinTypeName"/> ,
                            </x:forEach>
                        </p>
                        <p class="textdetail">TRIỆU CHỨNG: 
                            <x:forEach var="concerns" select="$listconcern2" varStatus="counter">
                                <x:out select="$concerns/concernName"/> ,
                            </x:forEach>
                        </p>
                    </div>
                    <div style="float: left;width: 100%">
                        <p style="font-size: 19px" class="textdetail">Mô Tả Sản Phẩm</p>
                        <p><x:out select="$product/detail"/></p>
                    </div>
                    <div style="float: left;width: 100%">
                        <p style="font-size: 19px"><x:out select="$product/price"/>đ</p>
                        <a href="<x:out select="$product/productLink"/>"><input type="button" class="buton" value="Đến trang bán hàng"></a>
                    </div>
                </div>
                <div id="listproduct" style="float: left;width: 100%">
                    <p style="margin-top: 30px;text-align: left;font-weight: bold">RECOMMEND TOP 5 PRODUCT SAME CATEGORY</p>
                    <x:forEach var="products" select="$topsame" varStatus="counter">
                        <a style="color: black" href="DetailServlet?productId=<x:out select="$products/productId"/>">
                            <div class="product"><img class="imgproduct" src="<x:out select="$products/imageLink"/>"/>
                                <p class="productname"><x:out select="$products/name"/></p>
                                <p class="productcate"><x:out select="$products/categoryName"/></p>
                                <p class="productbrand"><x:out select="$products/brand"/></p>
                                <p class="productprice"><x:out select="$products/price"/>đ</p>
                            </div>
                        </a>
                    </x:forEach>
                </div>
                <div id="listproduct" style="float: left;width: 100%">
                    <p style="margin-top: 30px;text-align: left;font-weight: bold">RECOMMEND TOP 5 PRODUCT</p>
                    <x:forEach var="products" select="$top5product" varStatus="counter">
                        <a style="color: black" href="DetailServlet?productId=<x:out select="$products/productId"/>">
                            <div class="product"><img class="imgproduct" src="<x:out select="$products/imageLink"/>"/>
                                <p class="productname"><x:out select="$products/name"/></p>
                                <p class="productcate"><x:out select="$products/categoryName"/></p>
                                <p class="productbrand"><x:out select="$products/brand"/></p>
                                <p class="productprice"><x:out select="$products/price"/>đ</p>
                            </div>
                        </a>
                    </x:forEach>
                </div>
            </div>
        </div>
    </div>
    <div class="footer"><img style="height: 30px" src="img/dathongbao.png"/>
        <p>© 2017 - Bản quyền của Công Ty Cao Văn Phú</p>
        <p>Giấy chứng nhận ĐKKD số 0314414731 do Sở Kế hoạch và Đầu tư Tp. HCM cấp ngày 19/05/2017</p>
    </div>
</body>
</html>


