<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="resource/resource.js"></script>
<script>
function accessOut(emCode){
	location.href = "AccessOut?emCode=" + emCode;
}



function moveService(action, pEmCode){
	const form = makeForm("", action, "post");
	const emCode = makeInputElement("hidden", "emCode", pEmCode, "");
	
	form.appendChild(emCode);
	
	document.body.appendChild(form);
	form.submit();
}

function getAjaxJson(action, data, fn){
	const ajax = new XMLHttpRequest();
	
	ajax.onreadystatechange = function(){
		if(ajax.readyState == 4 && ajax.status == 200){
			if(fn!=""){
				window[fn](JSON.parse(ajax.responseText));	
			}else{
				alert(ajax.responseText);
			}
		}
	};	
	ajax.open("post", action, true);
	ajax.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	ajax.send(data);
}
function getStudyList(){
	getAjaxJson("StudyList","","makeStudyList");
}
function makeStudyList(data){
	let option;
	for(idx=0;idx<data.length;idx++){
		option=createOption(data[idx].slCode);
		option.innerHTML=data[idx].slName;
		stslcode.appendChild(option);
	}	
}
function createOption(value){
	const option=document.createElement("option");
	option.setAttribute("value",value);
	return option;
}
function sendSignal(signal,pEmCode){
	const stslcode=document.getElementById("stslcode");
	const slCode=stslcode.options[stslcode.selectedIndex].value;
	const data="signal="+encodeURIComponent(signal)
				+"&emCode="+encodeURIComponent(pEmCode)
				+"&slCode="+encodeURIComponent(slCode);
	getAjaxJson("insSign",data,"");
}
</script>
</head>
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
	text-align: right;
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

#center {
	width: 100%;
	height: 90%;
}

#leftside {
	float: left;
	width: 25%;
	height: 100%;
}

#info {
	float: left;
	width: 50%;
	height: 100%;
}

#title {
	padding-color: #000000;
	padding: 15% 0%;
	text-align: center;
	width: 100%;
	height: 13.6%;
	font-size: 90pt;
	font-weight: 900;
}

#btn {
	padding: 5% 0%;
	text-align: center;
	width: 100%;
	height: 37.8%;
	font-weight: 900;
}

#startClass {
	border-radius: 10px;
	margin: 0% 1.2% 3% 0%;
	font-size: 30pt;
	padding: 2% 2%;
	border: 4px solid #353535;
	clear:both;
	float: left;
	text-align: center;
	width: 19%;
	height: 15%;
}

#middleSign {
	border-radius: 10px;
	margin: 0% 1.2% 3% 0%;
	font-size: 25pt;
	padding: 2.3% 2%;
	border: 4px solid #353535;
	float: left;
	text-align: center;
	width: 19%;
	height: 13.5%;
}

#outClass {
	border-radius: 10px;
	margin: 0% 1.2% 3% 0%;
	font-size: 30pt;
	padding: 2% 2%;
	border: 4px solid #353535;
	float: left;
	text-align: center;
	width: 19%;
	height: 15%;
}

#endClass {
	border-radius: 10px;
	margin: 0% 0% 3% 0%;
	font-size: 30pt;
	padding: 2% 2%;
	border: 4px solid #353535;
	float: left;
	text-align: center;
	width: 19%;
	height: 15%;
}

#classManagement {
	clear: both;
	border-radius: 10px;
	margin: 0% 1.3% 3% 0%;
	font-size: 30pt;
	padding: 2% 2%;
	border: 4px solid #353535;
	float: left;
	text-align: center;
	width: 44.1%;
	height: 15%;
}

#studentManagement {
	border-radius: 10px;
	margin: 0% 0% 3% 0%;
	font-size: 30pt;
	padding: 2% 2%;
	border: 4px solid #353535;
	float: left;
	text-align: center;
	width: 44.1%;
	height: 15%;
}

#myInfo {
	border-radius: 10px;
	font-size: 30pt;
	padding: 2% 0%;
	border: 4px solid #353535;
	float: left;
	text-align: center;
	width: 99%;
	height: 15%;
}

#rightside {
	float: left;
	width: 25%;
	height: 100%;
}

#bottom {
	width: 100%;
	height: 5%;
}
#stslcode {
	float: left;
	width: 25%;
	height: 100%;
}
</style>
<body onLoad="getStudyList()">
	<div id="everythings">
		<div id="top">
			<div id="academy">KSWL</div>
			<div id="loginInfo">[${accessInfo[0].emName}]님 환영합니다! 당신은
				[관리자]입니다.</div>
			<div type="button" id="logOut"
				onClick="accessOut('${accessInfo[0].emCode}')">로그아웃</div>
			<input type="hidden" name="emCode" value="${accessInfo[0].emCode}"/>
		</div>
		<div id="center">
			<div id="leftside"></div>
			<div id="info">
				<div id="title">시스템 관리</div>
				<div id="btn">
					<div class="d">
						<select id="stslcode" style="width:200px;height:80px;font-size:20pt;"></select>
					</div>
					<div id="startClass" onClick="sendSignal('2002','${accessInfo[0].emCode}')">입실</div>
					<div id="middleSign" onClick="sendSignal('3003','${accessInfo[0].emCode}')">중간신호</div>
					<div id="outClass" onClick="sendSignal('4004','${accessInfo[0].emCode}')">외출</div>
					<div id="endClass" onClick="sendSignal('5005','${accessInfo[0].emCode}')">퇴실</div>
					<div id="classManagement">수업 관리</div>
					<div id="studentManagement"
						onClick="moveService('SSI', '${accessInfo[0].emCode}')">회원
						등록</div>
					<div id="myInfo"
						onClick="moveService('SFM','${accessInfo[0].emCode}')">학생 출결
						내역</div>
				</div>
			</div>
			<div id="rightside"></div>
		</div>
		<div id="bottom"></div>
	</div>
</body>
</html>