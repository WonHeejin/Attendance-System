<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
function sendSign(pStCode,pSsCode,action){
	const form=makeForm("",action,"post");
	const data="stCode="+encodeURIComponent(pStCode)
				+"ssCode="+encodeURIComponent(pSsCode);
	getAjaxJson(action,data);
}

function getMyInfo(pStCode){
	const form=makeForm("","getMyInfo","post");
	const stCode=makeInputElement("hidden","emCode",pStCode,"");
	form.appendChild(emCode);
	document.body.appendChild(form);
	form.submit();
}
function getAjaxJson(action,data){
	const ajax = new XMLHttpRequest();
		ajax.onreadystatechange = function() {
			if ( ajax.readyState== 4 && ajax.status == 200) {			
				alert(JSON.parse(ajax.responseText));						
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

#userInput {
	width: 100%;
	height: 10%;
}

#square {
	float: left;
	width: 90%;
	height: 70%;
	border: 2px solid #000000;
	width: 90%;
}

#column {
font-weight:700;
	border-top:2px solid #353535;
	border-bottom:2px solid #353535;
	text-align: center;
	width: 100%;
	height: 5%;
}

#no {
	padding: 0.6% 0%;
	float: left;
	width: 12.5%;
	height: 50%;
}

#code {
	padding: 0.6% 0%;
	float: left;
	width: 12.5%;
	height: 50%;
}

#name {
	padding: 0.6% 0%;
	float: left;
	width: 12.5%;
	height: 50%;
}

#teacher {
	padding: 0.6% 0%;
	float: left;
	width: 12.5%;
	height: 50%;
}

#class {
	padding: 0.6% 0%;
	float: left;
	width: 12.5%;
	height: 50%;
}

#start {
	padding: 0.6% 0%;
	float: left;
	width: 12.5%;
	height: 50%;
}

#end {
	padding: 0.6% 0%;
	float: left;
	width: 12.5%;
	height: 50%;
}

#space {
	padding: 0.6% 0%;
	float: left;
	width: 12.5%;
	height: 50%;
}

#list {
	width: 100%;
	height: 85%;
	background-color: #FF007F;
	overflow-y:scroll;
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
			<div id="loginInfo">[성기준]님 환영합니다! 당신은 [학생]입니다.</div>
			<div type="button" id="logOut">로그아웃</div>
		</div>
		<div id=center>
			<div id=userInput>
				<input type="button" value="등록" style="float: right; margin:2.5% 2% 0% 0%;" />
				<input type="text" placeholder="종강일" style="float: right; margin:2.5% 1% 0% 0%;" />
				<input type="text" placeholder="개강일" style="float: right; margin:2.5% 1% 0% 0%;" />
				<input type="text" placeholder="강의실" style="float: right; margin:2.5% 1% 0% 0%;" />
				<input type="text" placeholder="선생님이름" style="float: right; margin:2.5% 1% 0% 0%;" />
				<input type="text" placeholder="수업명" style="float: right; margin:2.5% 1% 0% 0%;" />
				<input type="text" placeholder="수업코드" style="float: right; margin:2.5% 1% 0% 0%;" />				
			</div>
			<div id=column>
				<div id=no>No.</div>
				<div id=code>수업코드</div>
				<div id=name>수업명</div>
				<div id=teacher>선생님이름</div>
				<div id=class>강의실</div>
				<div id=start>개강일</div>
				<div id=end>종강일</div>
				<div id=space></div>
			</div>
			<div id=list>list</div>
		</div>
		<div id="bottom"></div>
	</div>
</body>
</html>