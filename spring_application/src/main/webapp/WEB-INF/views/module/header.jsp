<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="decorator"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><decorator:title default="HVACEMS" /></title>

<!-- Google Font: Source Sans Pro -->
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
<!-- Flaticon -->
<link rel='stylesheet'
	href='https://cdn-uicons.flaticon.com/uicons-regular-rounded/css/uicons-regular-rounded.css'>
<!-- Font Awesome Icons -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/bootstrap/plugins/fontawesome-free/css/all.min.css">
<!-- Theme style -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/bootstrap/dist/css/adminlte.min.css">
<decorator:head />
</head>
<body class="hold-transition sidebar-mini">
	<div class="wrapper">

		<!-- Content Header (Page header) -->
		<!-- Navbar -->
		<nav class="main-header navbar navbar-expand navbar-white navbar-light">
			<!-- Left navbar links -->
			<ul class="navbar-nav pageNames" style="font-size: 25px; font-weight: bold;">
			</ul>

			<!-- Right navbar links -->
			<ul class="navbar-nav ml-auto">
				<!-- Navbar Search -->

				<!-- Messages Dropdown Menu -->

				<!-- Notifications Dropdown Menu -->
				<li class="nav-item dropdown">
				<a class="nav-link" data-toggle="dropdown" href="#">
					<i class="far fa-bell"></i>
					<span class="badge badge-danger navbar-badge">${alarmCount }</span>
				</a>
					<div class="dropdown-menu dropdown-menu-lg dropdown-menu-right">
						<span class="dropdown-header">${alarmCount } Notifications</span>
						<div class="dropdown-divider"></div>
						<a href="#" class="dropdown-item dropdown-footer" style="text-align: right;">전체삭제</a>
						<c:if test="${empty alarmList }">
							<div class="dropdown-divider"></div>
							<strong>알림이 없습니다.</strong>
						</c:if>
						<c:forEach items="${alarmList }" var="alarm">
							<div class="dropdown-divider"></div>
							<a href="#" class="dropdown-item"> <i class="fas fa-bullhorn mr-2"></i>
								${alarm.hvacNum }구역 ${alarm.breakName } 
								<span class="float-right text-muted text-sm">
									<fmt:formatDate value="${alarm.alarmDate }" pattern="yyyy-MM-dd HH:mm:ss" />
								</span>
							</a>
						</c:forEach>
					</div></li>
				<li class="nav-item">
					<button class="btn fullscreenBtn" data-widget="fullscreen" data-auto-collapse-size="768">
						<i class="fas fa-expand-arrows-alt"></i>
					</button>
				</li>
				<li class="nav-item">
					<a class="nav-link" data-widget="pushmenu" href="#" role="button">
						<i class="fas fa-bars"></i>
					</a>
				</li>
			</ul>
		</nav>
		<!-- /.navbar -->

		<!-- Main Sidebar Container -->
		<aside class="main-sidebar sidebar-dark-primary" style="background-color: #3C6562; font-color: white;">
			<!-- Brand Logo -->
			<a href="home.do" class="brand-link "> 
				<img src="<%=request.getContextPath()%>/resources/img/HVACEMSLogo.png" alt="AdminLTE Logo" class="brand-image img-circle elevation-3" style="opacity: .9">
				<span class="font-weight-light">&nbsp;&nbsp;&nbsp;HVACEMS</span>
			</a>

			<!-- Sidebar -->
			<div class="sidebar">
				<!-- Sidebar user panel (optional) -->

				<!-- SidebarSearch Form -->

				<!-- Sidebar Menu -->
				<ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="true">
					<!-- Add icons to the links using the .nav-icon class
               with font-awesome or any other icon font library -->
					<c:forEach items="${dataMap.menuList }" var="menu">
						<li class="nav-item">
							<a class="nav-link mainMenu" href="#" onclick="pageName('${menu.menuName}');goPage('<%=request.getContextPath()%>${menu.menuUrl}','${menu.menuCode}');" style="font-size: 20px;" >
								&nbsp;<i class="${menu.menuIcon }"></i>&nbsp;&nbsp;<p>${menu.menuName}</p>
							</a>
							<ul class="nav nav-treeview">
								<c:forEach items="${menu.submenuList }" var="sub">
									<li class="nav-item">
										<a class="nav-link" href="javascript:pageName('${sub.menuName }');goPage('<%=request.getContextPath()%>${sub.menuUrl}','${sub.menuCode}');">
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<p>${sub.menuName }</p>
										</a>
									</li>
								</c:forEach>
							</ul>
						</li>
					</c:forEach>
				</ul>
				
				<!-- /.sidebar-menu -->
			</div>
			<!-- /.sidebar -->
		</aside>