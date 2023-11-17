<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<title>자바코딩즈 상품 관리</title>
	<link rel="stylesheet" href="/resources/css/manage_products.css" />
	<script src="https://code.jquery.com/jquery-3.7.1.js"
	        integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>

	<%--  Web Components --%>
	<script type="module" src="https://1.www.s81c.com/common/carbon/web-components/tag/v2/latest/data-table.min.js"></script>
	<script type="module" src="https://1.www.s81c.com/common/carbon/web-components/tag/v2/latest/button.min.js"></script>
	<script type="module" src="https://1.www.s81c.com/common/carbon/web-components/tag/v2/latest/checkbox.min.js"></script>
	<%-- Fragment CSS --%>
	<link rel="stylesheet" href="/views/merchandiser/fragments/init.css" />
	<link rel="stylesheet" href="/views/merchandiser/fragments/header.css" />
	<link rel="stylesheet" href="/views/merchandiser/fragments/footer.css" />
	
	<script src="/resources/scripts/notice.js"></script>
</head>
<body>
<%@ include file="/views/merchandiser/fragments/header.jsp" %>
<main>
<form action="noticeProc?flag=insert" name="noticeForm" method="post">
<table>

	<thead>
		<tr>
			<th colspan="3">공지사항등록</th>
		</tr>
	</thead>
	
	<tbody>
		<tr>
			<td class="col1">번호</td>
			<td class="col2">
			<input type="text" id="notice_id" name="notice_id" class="chk1" title="번호">
			</td>
		</tr>
		<tr>
			<td class="col1">제목</td>
			<td class="col2">
			<input type="text" id="label" name="label" class="chk1" title="제목">
			</td>
		</tr>
		<tr>
			<td class="col1">작성자</td>
			<td class="col2">
			<input type="text" id="author_id" name="author_id" title="작성자" value="admin" readonly="readonly">${author_id}
			</td>
		</tr>
		
		<tr>
			<td class="col1">내용</td>
			<td class="col2">
			<textarea class="chk1" id="content" title="공지사항 내용" rows="10" cols="30" name="content"></textarea>
		</td>
		</tr>
	
	</tbody>
	<tfoot>
		<tr>
			<td colspan="3">
				<button class="submit1" type="button">저장</button>
			</td>
	
		</tr>
	</tfoot>

</table>

</form>
</main>
<%@ include file="/views/customer/fragments/footer.jsp" %>
</body>
</html>