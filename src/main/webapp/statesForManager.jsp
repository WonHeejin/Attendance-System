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
function search(){
	const date=document.getElementsByName("date")[0].value;
	const classes=document.getElementById("class");
	const cName=classes.options[classes.selectedIndex].value;
	const student=document.getElementById("student");
	const sName=student.options[student.selectedIndex].value;
	alert(date+":"+cName+":"+sName);
}
function classList(){
	const classes=document.getElementById("class");
	for(idx=0;idx<4;idx++){
		let option=createOption(4000+idx);
		option.innerHTML=(idx==0)?"수업선택":(idx==1)?"수업1":(idx==2)?"수업2":"수업3";
		classes.appendChild(option);
	}
}
function studentList(){
	const classes=document.getElementById("class");
	const cName=classes.options[classes.selectedIndex].value;
	const student=document.getElementById("student");
	while(student.hasChildNodes()){
		student.removeChild(student.lastChild);
	}
	for(idx=0;idx<3;idx++){
		if(cName==4001){
			option=createOption(5000+idx);
			option.innerHTML=(idx==0)?"김땡땡":(idx==1)?"김쌩쌩":"김뺑뺑";
			student.appendChild(option);
		}else if(cName==4002){
			option=createOption(5000+idx);
			option.innerHTML=(idx==0)?"이땡땡":(idx==1)?"이쌩쌩":"이뺑뺑";
			student.appendChild(option);
		}else if(cName==4003){
			option=createOption(5000+idx);
			option.innerHTML=(idx==0)?"박땡땡":(idx==1)?"박쌩쌩":"박뺑뺑";
			student.appendChild(option);
		}else{
			option=createOption(0);
			option.innerHTML="학생선택";
		}
	}

}
function createOption(value){
	const option=document.createElement("option");
	option.setAttribute("value",value);
	return option;
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

#middle {clear:both;width:100%;margin:100px 100px 0px 500px;}
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
<body onLoad="classList()">
	<div id="logo">ICIA</div>
	<div id="info">
		[${accessInfo[0].emName}]님 환영합니다! 귀하의 등급은 관리자입니다. <input type="button" class="btn"
			onClick="accessOut()" value="로그아웃" />
	</div>

	<div id="middle">	 
		<span>날짜선택</span> 
		<input name = "date" type="month"/>
		<select id = "class" onChange="studentList()">
		</select> 
		<select id="student">
		<option>학생선택</option>
		</select> 
		<input type="button" value="조회" onClick="search()"/> 
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
	
</body>
</html>