<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	function getAjaxJson(action, data, fn) {
		const ajax = new XMLHttpRequest();

		ajax.onreadystatechange = function() {
			if (ajax.readyState == 4 && ajax.status == 200) {
				if (fn != "") {
					window[fn](JSON.parse(ajax.responseText));
				} else {
					alert(ajax.responseText);
				}
			}
		};
		ajax.open("post", action, true);
		ajax.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		ajax.send(data);
	}
	function gettotalAttendance(slCode, stCode) {
		const data = "slCode=" + encodeURIComponent(slCode) + "&stCode="
				+ encodeURIComponent(stCode);
		getAjaxJson("getTA", data, "totalAttendance");
	}
	function totalAttendance(data) {
		let tr = createTrTd("tr");
		for (idx = 0; idx < 4; idx++) {
			let td = createTrTd("td");
			td.innerHTML = (idx == 0) ? data[0] : (idx == 1) ? data[1]
					: (idx == 2) ? data[2] : data[3];
			tr.appendChild(td);
		}
		attendance1.appendChild(tr);
	}
	function search(slCode, stCode) {
		const date = document.getElementsByName("date")[0].value;
		const data = "date=" + encodeURIComponent(date) + "&slCode="
				+ encodeURIComponent(slCode) + "&stCode="
				+ encodeURIComponent(stCode);
		getAjaxJson("getTH", data, "attendList");
	}
	function attendList(data) {
		let td, tr;
		for (idx = 0; idx < data.length; idx++) {
			tr = createTrTd("tr");
			for (colIdx = 0; colIdx < 2; colIdx++) {
				td = createTrTd("td");
				td.innerHTML = (colIdx == 0) ? data[idx].thDate
						: data[idx].thName;
				tr.appendChild(td);
			}
			attendance2.appendChild(tr);
		}
	}

	function createTrTd(Trd) {
		const trd = document.createElement(Trd);
		return trd;
	}
</script>
<style>
#everythings {
	position: absolute;
	width: 100%;
	height: 100%;
}

#top {
	width: 100%;
	height: 5%;
	background-color: #BDBDBD;
}

#academy {
	text-align: center;
	float: left;
	width: 5%;
	height: 100%;
	color: #FF00DD;
	font-size: 19pt;
}

#loginInfo {
	padding: 0.4% 1%;
	text-align: right;
	float: left;
	width: 86.5%;
	height: 60%;
}

#logOut {
	padding: 0.2% 0%;
	margin: 0.1% 0%;
	background-color: #EAEAEA;
	border: 1px solid #000000;
	text-align: center;
	float: left;
	width: 5%;
	height: 63%;
}

#first {
	clear: both;
	float: right;
	width: 39%;
	margin: 50px 350px;
	text-align: center;
}

#middle {
	position: absolute;
	top: 40%;
	left: 50%;
	transform: translate(-50%, -50%);
	clear: both;
	float: right;
	width: 20%;
	margin: 0px 700px 0px 0px;
}

#bottom {
	clear: both;
	float: right;
	margin: 0px 200px;
}

table {
	
}

table.type02 {
	position: absolute;
	top: 20%;
	left: 50%;
	transform: translate(-50%, -50%);
	width: 30%;
	border-collapse: collapse;
	text-align: center;
	line-height: 1.5;
	margin: 20px 10px;
}

table.type02 td {
	width: 200px;
	padding: 10px;
	vertical-align: top;
	border: 1px solid #ccc;
}

table.type02 th {
	text-align: center;
	width: 150px;
	padding: 10px;
	font-weight: bold;
	vertical-align: top;
	border: 1px solid #ccc;
}

table.type01 {
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	width: 50%;
	border-collapse: collapse;
	text-align: left;
	line-height: 1.5;
	margin: 20px 10px;
}

table.type01 th {
	width: 150px;
	padding: 10px;
	font-weight: bold;
	vertical-align: top;
	border: 1px solid #ccc;
}

table.type01 td {
	width: 350px;
	padding: 10px;
	vertical-align: top;
	border: 1px solid #ccc;
}

#yellow {
	background-color: yellow
}
</style>
</head>
<body
	onLoad="gettotalAttendance('${slInfo.slCode}','${accessInfo[0].stCode}')">
	<div id="everythings">
		<div id="top">
			<div id="academy">KSWL</div>
			<div id="loginInfo">[${accessInfo[0].stName}]님 환영합니다! 당신은
				[학생]입니다.</div>
			<div type="button" id="logOut"
				onClick="accessOut('${check}','${accessInfo[0].stCode}')">로그아웃</div>
			<input type="hidden" name="slCode" value="${slInfo.slCode}" />
		</div>
		<div id="first">
			<table class="type02">
				<thead>
					<tr>
						<th colspan="4">나의 출석현황</th>
					</tr>
					<tr id="yellow">
						<th>출석</th>
						<th>결석</th>
						<th>지각</th>
						<th>조퇴</th>
					</tr>
				</thead>
				<tbody id="attendance1">
				</tbody>
			</table>
		</div>
		<div id="middle">
			<span>날짜선택</span> <span><input name="date" type="month"></span>
			<span><input type="button" value="조회"
				onClick="search('${slInfo.slCode}','${accessInfo[0].stCode}')"></span>
		</div>
		<div id="bottom">
			<table class="type01">
				<thead>
					<tr>
						<th>날짜</th>
						<th>출결상태</th>
					</tr>
				</thead>
				<tbody id="attendance2">

				</tbody>
			</table>

		</div>
	</div>
</body>
</html>