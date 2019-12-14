<%-- 
    Document   : survey
    Created on : Dec 12, 2019, 12:47:53 AM
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
            <div class="login"><a class="linkdangnhap" href="#">Đăng nhập / Đăng ký</a></div>
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
            <div class="listproduct" id="listproduct">
                <div class="surveyform">
                    <form action="SurveyServlet" method="POST" class="formsurvey">
                        <div class="skintype">
                            <p style="margin-bottom: 10px;">Skin Type</p>
                            <x:forEach var="skintype" select="$listSkintype" varStatus="counter">
                                <x:if select="$skintype/skinTypeName != 'Mọi loại da'">
                                    <p>
                                        <input class="" type="radio" name="skintype" value="<x:out select="$skintype/skinTypeId"/>">
                                        <label><x:out select="$skintype/skinTypeName"/></label>
                                    </p>
                                </x:if>
                            </x:forEach>
                        </div>
                        <div class="concern">
                            <p style="margin-bottom: 10px;">Concern</p>
                            <x:forEach var="concern" select="$listConcern" varStatus="counter">
                                <p>
                                    <input class="" type="checkbox" name="concern" value="<x:out select="$concern/concernId"/>">
                                    <label><x:out select="$concern/concernName"/></label>
                                </p>
                            </x:forEach>
                        </div>
                        <div style="width: 100%;float: left">
                            <p style="font-size: 13px;margin-top: 50px;">Limit Money</p>
                            <input class="input" name="limit" type="number" required>
                            <input class="buton" type="submit" value="Submit">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="footer"><img style="height: 30px" src="img/dathongbao.png"/>
        <p>© 2017 - Bản quyền của Công Ty Cao Văn Phú</p>
        <p>Giấy chứng nhận ĐKKD số 0314414731 do Sở Kế hoạch và Đầu tư Tp. HCM cấp ngày 19/05/2017</p>
    </div>
</body></html>

