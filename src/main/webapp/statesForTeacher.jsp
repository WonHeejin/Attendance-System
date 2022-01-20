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
	function search(slCode) {
		const date = document.getElementsByName("date")[0].value;
		alert(date);
		alert(date + ":" + slCode);
		const data = "date=" + encodeURIComponent(date) + "&slCode="
				+ encodeURIComponent(slCode);
		getAjaxJson("getTHforT", data, "attendList");
	}
	function attendList(data) {
		let td, tr;
		for (idx = 0; idx < data.length; idx++) {
			tr = createTrTd("tr");
			for (colIdx = 0; colIdx < 2; colIdx++) {
				td = createTrTd("td");
				td.innerHTML = (colIdx == 0) ? data[idx].thStName
						: data[idx].thName;
				tr.appendChild(td);
			}
			attendance.appendChild(tr);
		}
	}

	function createTrTd(Trd) {
		const trd = document.createElement(Trd);
		return trd;
	}
</script>
<style>
#middle {
position: absolute; top:10%; left:50%; transform: translate(-50%, -50%);
	clear: both;
	float: right;
	width: 30%;
	margin: 100px 700px 0px 0px;
}

#bottom {
	clear: both;
	float: right;
	margin: 0px 200px;
}
table {
	position: absolute; top:30%; left:50%; transform: translate(-50%, -50%);
	width:50%; 
	
}
table.type01 {
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
	font-size:19pt;
}

#loginInfo {
	padding : 0.4% 1%;
	text-align : right;
	float: left;
	width: 86.5%;
	height: 60%;
}

#logOut {
	padding:0.2% 0%;
	margin:0.1% 0%;
	background-color: #EAEAEA;
	border: 1px solid #000000;
	text-align: center;
	float: left;
	width: 5%;
	height: 63%;
}

</style>
</head>
<body>
	<div id="everythings">
		<div id="top">
			<div id="academy">KSWL</div>
			<div id="loginInfo">[${accessInfo[0].emName}]님 환영합니다! 당신은[선생님]입니다.</div>
			<div type="button" id="logOut"
				onClick="accessOut('${check}','${accessInfo[0].emCode}')">로그아웃</div>
			<input type="hidden" name="emCode" value="${accessInfo[0].emCode}" />
			<input type="hidden" name="emCode" value="${slInfo.slCode}" />
		</div>
		<div id="middle">
			<span>날짜선택</span> <span><input name="date" type="date">
				<input type="button" value="조회"
				onClick="search('${slInfo.slCode}','${accessInfo[0].emCode}')">
			</span>
		</div>
		<div id="bottom">
			<table class="type01">
				<thead>
					<tr>
						<th>학생이름</th>
						<th>출결상태</th>
					</tr>
				</thead>
				<tbody id="attendance">
				</tbody>
			</table>
		</div>

	</div>



</body>
</html>