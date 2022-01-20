<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	function accessOut(check, emCode) {
		location.href = "AccessOut?emCode=" + emCode + "&people=" + check;
	}
	function getList() {
		getAjaxJson("StudyList", "", "classList");
	}
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
			attendance.appendChild(tr);
		}
	}

	function createTrTd(Trd) {
		const trd = document.createElement(Trd);
		return trd;
	}
	function search() {
		const date = document.getElementsByName("date")[0].value;
		const classes = document.getElementById("class");
		const slCode = classes.options[classes.selectedIndex].value;
		const student = document.getElementById("student");
		const stCode = student.options[student.selectedIndex].value;
		alert(date + ":" + slCode + ":" + stCode);
		const data = "date=" + encodeURIComponent(date) + "&slCode="
				+ encodeURIComponent(slCode) + "&stCode="
				+ encodeURIComponent(stCode);
		getAjaxJson("getTH", data, "attendList");
	}
	function classList(data) {
		const classes = document.getElementById("class");
		for (idx = 0; idx < data.length; idx++) {
			option = createOption(data[idx].slCode);
			option.innerHTML = data[idx].slName;
			classes.appendChild(option);
		}
		const slCode = classes.options[classes.selectedIndex].value;
		getStudentList(slCode);
	}
	function getStudentList(slCode) {
		const data = "slCode=" + encodeURIComponent(slCode);
		getAjaxJson("getStList", data, "studentList");
	}
	function studentList(data) {

		const student = document.getElementById("student");
		while (student.hasChildNodes()) {
			student.removeChild(student.lastChild);
		}
		for (idx = 0; idx < data.length; idx++) {
			option = createOption(data[idx].emCode);
			option.innerHTML = data[idx].emName;
			student.appendChild(option);
		}
	}
	function createOption(value) {
		const option = document.createElement("option");
		option.setAttribute("value", value);
		return option;
	}
</script>
<style>

#middle {
	position: absolute; top:10%; left:50%; transform: translate(-50%, -50%);
	clear: both;
	float: right;
	width: 30%;
	margin: 100px 700px 0px 0px;}
#bottom {
	clear: both;
	float: right;
	margin: 0px 200px;
}

table {
	position: absolute;
	top: 30%;
	left: 50%;
	transform: translate(-50%, -50%);
	width: 50%;
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
</style>
</head>
<body onLoad="getList()">
	<div id="everythings">
		<div id="academy">KSWL</div>
		<div id="top">
			<div id="loginInfo">[${accessInfo[0].emName}]님 환영합니다! 당신은
				[행정직원]입니다.</div>
			<div type="button" id="logOut"
				onClick="accessOut('${check}','${accessInfo[0].emCode}')">로그아웃</div>
			<input type="hidden" name="emCode" value="${accessInfo[0].emCode}" />
		</div>


		<div id="middle">
			<span>날짜선택</span> <input name="date" type="month" /> <select
				id="class" onChange="getStudentList(this.value)">
			</select> <select id="student">
				<option>학생선택</option>
			</select> <input type="button" value="조회" onClick="search()" />
		</div>
		<div id="bottom">
			<table class="type01">
				<thead>
					<tr>
						<th>날짜</th>
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