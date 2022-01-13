<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>

function totalAttendance(){
	let tr=createTrTd("tr");
	for(idx=0;idx<4;idx++){
		let td=createTrTd("td");
		td.innerHTML=(idx==0)?"1":(idx==1)?"2":(idx==2)?"3":"4";
		tr.appendChild(td);
	}	
	attendance1.appendChild(tr);
}

function attendList(){
	let td,tr;
	for(idx=0;idx<4;idx++){
		tr=createTrTd("tr");
		for(colIdx=0;colIdx<2;colIdx++){
			td=createTrTd("td");
			td.innerHTML=(colIdx==0)?"2022/01/11":"출석";
			tr.appendChild(td);
		}
		attendance2.appendChild(tr);	
	}
}

function createTrTd(Trd){
	const trd=document.createElement(Trd);
	return trd;
}

</script>
<style>
#logo {
	font-size: 30pt;
	font-weight: 800;
	float: left;
}

#info {
	width: 88%;
	height: 25px;
	line-height: 25px;
	background-color:;
	border: 2px solid #81BA7B;
	color:;
	font-size: 10pt;
	text-align: right;
	float: right;
}

#top {clear:both;float:right;width:39%;margin:50px 350px;text-align:center;}
#middle {clear:both;float:right;width:20%;margin:0px 700px 0px 0px;}
#bottom {clear:both;float:right;margin:0px 200px;}
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
#yellow {background-color:yellow}

</style>
</head>
<body onLoad="totalAttendance()">
	<div id="logo">ICIA</div>
	<div id="info">
		~님 환영합니다! 귀하의 등급은 학생입니다. <input type="button" class="btn"
			onClick="accessOut()" value="로그아웃" />
	</div>
	<div id="top">
		<div>나의 출석현황</div>
		<table class="type01">
		<thead>
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
		<span>날짜선택</span> 
		<span><input type="month"></span>
		<span><input type="button" value ="조회" onClick="attendList()"></span>
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
	
</body>
</html>