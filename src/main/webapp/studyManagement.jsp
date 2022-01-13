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
	font-size: 25pt;
	font-weight: 800;
}

#loginInfo {
	padding: 0.5% 0.5%;
	text-align: right;
	float: left;
	width: 89%;
	height: 60%;
	text-align: right;
}

#logOut {
	margin: 0.5% 0.5% 0.5% 0%;
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
	font-weight: 700;
	border-top: 2px solid #353535;
	border-bottom: 2px solid #353535;
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
	overflow-y: scroll;
}

#bottom {
	width: 100%;
	height: 5%;
}

.studyList.no {
	text-align: center;
	padding: 0.6% 0%;
	float: left;
	width: 12.6%;
	height: 50%;
}

.studyList.slCode {
	text-align: center;
	padding: 0.6% 0%;
	float: left;
	width: 12.65%;
	height: 50%;
}

.studyList.slName {
	text-align: center;
	padding: 0.6% 0%;
	float: left;
	width: 12.6%;
	height: 50%;
}

.studyList.slEmName {
	text-align: center;
	padding: 0.6% 0%;
	float: left;
	width: 12.65%;
	height: 50%;
}

.studyList.slCrName {
	text-align: center;
	padding: 0.6% 0%;
	float: left;
	width: 12.55%;
	height: 50%;
}

.studyList.slStartDate {
	text-align: center;
	padding: 0.6% 0%;
	float: left;
	width: 12.6%;
	height: 50%;
}

.studyList.slEndDate {
	text-align: center;
	padding: 0.6% 0%;
	float: left;
	width: 12.65%;
	height: 50%;
}

.studyList.mod {
	margin: 0.4% 0.6% 0.6% 0%;
	text-align: center;
	float: left;
}

.studyList.remove {
	margin: 0.4%;
	text-align: center;
	float: left;
}
</style>

<script>
function getAjaxJson(action, data, fn) {
	const ajax = new XMLHttpRequest();

	ajax.onreadystatechange = function() {
		if (ajax.readyState == 4 && ajax.status == 200) {
			window[fn](ajax.responseText);
		}
	};
	ajax.open("post", action, true);
	ajax.setRequestHeader("Content-Type",
			"application/x-www-form-urlencoded");
	ajax.send(data);
}

const itemName=["no","slCode","slName","slEmName","slCrName","slStartDate","slEndDate"]
const btnName=["mod","remove"]
let no = 0;

function StudyListCtl(){
			getAjaxJson("GSL", "", "addStudyList");
}

function insCtl(){
	const slCode=document.ElementById("slCode").value;
	const slName=document.ElementById("slName").value;
	const slEmCode=document.ElementById("slEmCode").value;
	const slCrCode=document.ElementById("slCrCode").value;
	const slStartDate=document.ElementById("slStartDate").value;
	const slEndDate=document.ElementById("slEndDate").value;
	
	alert("등록을 진행하시겠습니까?");
	
}

function inputChange(){
	let btn = document.getElementsByName("inputSpace");
	for(idx=0;idx<btn.length;idx++){
		if(btn[idx].disabled="disabled"){
			btn[idx].disabled=false;
		}else{
			btn[idx].disabled="disabled";
		}
	}
	
	getStudyListMaxCode();
	
	
}

function getStudyListMaxCode(){
	getAjaxJson("GSMC","","setMaxCode");
}

function settMaxCode(jsonData){
	
}

/* func createDiv */
function createDiv(name, className){
	const div = document.createElement("div"); // <div></div>
	div.setAttribute("name", name);// <div name=""></div>
	div.setAttribute("class", className); // <div name="" class=""></div>
	return div;
}

function createChange(name, className){
	const Button = document.createElement("input"); // <div></div>
	Button.setAttribute("value", "수정");// <div name=""></div>
	Button.setAttribute("type", "button");// <div name=""></div>
	Button.setAttribute("name", name);// <div name=""></div>
	Button.setAttribute("class", className); // <div name="" class=""></div>
	return Button;
}

function createRemove(name, className){
	const Button = document.createElement("input"); // <div></div>
	Button.setAttribute("value", "삭제");// <div name=""></div>
	Button.setAttribute("type", "button");// <div name=""></div>
	Button.setAttribute("name", name);// <div name=""></div>
	Button.setAttribute("class", className); // <div name="" class=""></div>
	return Button;
}


function addStudyList(data){
	let jsonData = JSON.parse(data);
	for(num=0;num<jsonData.length;num++){
		no++;
		
		const list = document.getElementById("list");
		
		let record = createDiv("record", "record");
		record.setAttribute("onClick", "selectRecord(this)");
		for(colIdx=0; colIdx<7; colIdx++){		
			let item = createDiv(itemName[colIdx], "studyList " + itemName[colIdx]);
			item.innerHTML = (colIdx==0)? no: (colIdx==1)? jsonData[num].slCode: (colIdx==2)? jsonData[num].slName: (colIdx==3)? jsonData[num].slEmName: (colIdx==4)? jsonData[num].slCrName:(colIdx==5)? jsonData[num].slStartDate :jsonData[num].slEndDate;
			record.appendChild(item);
		}
		let change = createChange("mod", "studyList mod");
		record.appendChild(change);	
		
		let remove = createRemove("remove", "studyList remove");
		record.appendChild(remove);
		
		list.appendChild(record);
	}
	
}
</script>
<body onload="StudyListCtl()">
	<div id="everythings">
		<div id="top">
			<div id="academy">KSWL</div>
			<div id="loginInfo">[${accessInfo[0].emName}]님 환영합니다! 당신은
				[관리자]입니다.</div>
			<input type="button" id="logOut" value="로그아웃">
		</div>
		<div id=center>
			<div id=userInput>
				<input type="button" value="확인"
					style="float: right; margin: 2.5% 2% 0% 0%;" onClick="insCtl()"/>
					<input type="button" value="등록"
					style="float: right; margin: 2.5% 1% 0% 0%;" onClick="inputChange()"/>
				<input type="text" name="inputSpace" id="slEndDate" placeholder="종강일"
					style="float: right; margin: 2.5% 1% 0% 0%;" disabled="disabled"/> <input
					type="text" name="inputSpace" id="slStartDate" placeholder="개강일"
					style="float: right; margin: 2.5% 1% 0% 0%;" disabled="disabled"/> <input
					type="text" name="inputSpace" id="slCrCode" placeholder="강의실"
					style="float: right; margin: 2.5% 1% 0% 0%;" disabled="disabled"/> <input
					type="text" name="inputSpace" id="slEmCode" placeholder="선생님이름"
					style="float: right; margin: 2.5% 1% 0% 0%;" disabled="disabled"/> <input
					type="text" name="inputSpace" id="slName" placeholder="수업명"
					style="float: right; margin: 2.5% 1% 0% 0%;" disabled="disabled"/> <input
					type="text" name="inputSpace" id="slCode" placeholder="수업코드"
					style="float: right; margin: 2.5% 1% 0% 0%;" disabled="disabled"/>
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
			<div id=list></div>
		</div>
		<div id="bottom"></div>
	</div>
</body>
</html>