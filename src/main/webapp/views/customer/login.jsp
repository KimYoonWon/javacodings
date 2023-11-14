<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html>
<head>
	<!-- Javascript API and Frameworks -->
	<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
	<link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/>
	<script type="text/javascript" src="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>

	<script src="/resources/scripts/login.js"></script>
	
	<script type="module" src="https://1.www.s81c.com/common/carbon/web-components/tag/v2/latest/button.rtl.min.js"></script>
	<script type="module" src="https://1.www.s81c.com/common/carbon/web-components/tag/v2/latest/form-group.min.js"></script>

	<link rel="stylesheet" href="/views/customer/fragments/init.css" />
	<link rel="stylesheet" href="/views/customer/fragments/header.css" />
	<link rel="stylesheet" href="/views/customer/fragments/footer.css" />
	<link rel="stylesheet" href="/views/customer/login.css" />

	<title>자바코딩즈</title>
	<link rel="stylesheet" href="/resources/css/landing.css" />
	<script src="/resources/scripts/landing.js"></script>
</head>
<body>
<%@ include file="/views/customer/fragments/header.jsp" %>
<main>
	<div class="loginform">
	<h1 class="login_text">LOGIN</h1>
	
	<form action="/actions/login" method="post" name="topForm" class="login_form">
		<ul id="login_input">
			<li><input type="text" name="member_id" id="user" class="chkt"  title="아이디" placeholder="user_id"></li>
			<li><input type="password" name="password" id="pass" class="chkt" title="비밀번호" placeholder="password"></li>
		</ul>
		<button type="button" id="submitTop">로그인</button>
	</form>
	
	<div><a href="/join" class="col1">회원가입</a><a href="#" class="col2">아이디/비밀번호 찾기</a></div>
	</div>
</main>
<%@ include file="/views/customer/fragments/footer.jsp" %>
</body>
</html>