<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="resource/resource.js"></script>
<script>
function accessOut(check,stCode){
	location.href = "AccessOut?stCode=" + stCode + "&people="+check;
}
function checkSign(pSlCode,pSsCode,pStCode){
	alert(pSsCode);
	const data="slCode="+encodeURIComponent(pSlCode)
				+"&ssCode="+encodeURIComponent(pSsCode)
				+"&stCode="+encodeURIComponent(pStCode);
	getAjaxJson("checkSign",data,"");
}

function getMyInfo(pSlCode,pStCode){
	const form=makeForm("","SFSt","post");
	const stCode=makeInputElement("hidden","stCode",pStCode,"");
	const slCode=makeInputElement("hidden","slCode",pSlCode,"");
	form.appendChild(stCode);
	form.appendChild(slCode);
	document.body.appendChild(form);
	form.submit();
}
function getAjaxJson(action,data,fn){
	const ajax = new XMLHttpRequest();
		ajax.onreadystatechange = function() {
			if(ajax.readyState == 4 && ajax.status == 200){
				if(fn!=""){
					window[fn](JSON.parse(ajax.responseText));	
				}else{
					alert(ajax.responseText);
				}
			}
		};
		ajax.open("post", action, true);
		ajax.setRequestHeader("Content-type","application/x-www-form-urlencoded");	
		ajax.send(data);
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
	font-size:19pt;
}

#loginInfo {
	padding : 0.4% 1%;
	text-align : right;
	float: left;
	width: 86.5%;
	height: 60%;
	text-align: right;
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
	padding: 10% 0%;
	text-align: center;
	width: 100%;
	height: 25.7%;
	font-size: 65pt;
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
	border-radius:10px;
	margin: 0% 1.2% 3% 0%;
	font-size: 30pt;
	padding: 2% 2%;
	border: 4px solid #353535;
	float: left;
	text-align: center;
	width: 19%;
	height: 15%;
}

#middleSign {
	border-radius:10px;
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
	border-radius:10px;
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
	border-radius:10px;
	margin: 0% 0% 3% 0%;
	font-size: 30pt;
	padding: 2% 2%;
	border: 4px solid #353535;
	float: left;
	text-align: center;
	width: 19%;
	height: 15%;
}

#myInfo {
	border-radius:10px;
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
</style>
<body>
	<div id="everythings">
		<div id="top">
			<div id="academy">KSWL</div>
			<div id="loginInfo">[${accessInfo[0].stName}]님 환영합니다! 당신은 [학생]입니다.</div>
			<div type="button" id="logOut" onClick="accessOut('${check}','${accessInfo[0].stCode}')">로그아웃</div>
		</div>
		<div id="center">
			<div id="leftside"></div>
			<div id="info">
				<div id="title">${slInfo.slName}</div>
				<input type="hidden" name="slCode" value="${slInfo.slCode}"/>
				<div id="btn">
					<div id="startClass" onClick="checkSign('${slInfo.slCode}','2002','${accessInfo[0].stCode}')">입실</div>
					<div id="middleSign" onClick="checkSign('${slInfo.slCode}','3003','${accessInfo[0].stCode}')">중간신호</div>
					<div id="outClass" onClick="checkSign('${slInfo.slCode}','4004','${accessInfo[0].stCode}')">외출</div>
					<div id="endClass" onClick="checkSign('${slInfo.slCode}','5005','${accessInfo[0].stCode}')">퇴실</div>
					<div id="myInfo" onClick="getMyInfo('${slInfo.slCode}','${accessInfo[0].stCode}')">출결 상세 내역</div>
				</div>
			</div>
			<div id="rightside"></div>
		</div>
		<div id="bottom"></div>
	</div>
</body>
</html>