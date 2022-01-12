<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script> 

function attendList(){
	let td,tr;
	for(idx=0;idx<4;idx++){
		tr=createTrTd("tr");
		for(colIdx=0;colIdx<2;colIdx++){
			td=createTrTd("td");
			td.innerHTML=(colIdx==0)?"김이박":"출석";
			tr.appendChild(td);
		}
		attendance.appendChild(tr);	
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

#middle {clear:both;float:right;width:20%;margin:100px 700px 0px 0px;}
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

</style>
</head>
<body>
	<div id="logo">ICIA</div>
	<div id="info">
		~님 환영합니다! 귀하의 등급은 선생님입니다. <input type="button" class="btn"
			onClick="accessOut()" value="로그아웃" />
	</div>

	<div id="middle">	 
		<span>날짜선택</span> 
		<span><input type="month">
			<input type="button" value="조회" onClick="attendList()">
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
</body>
</html>