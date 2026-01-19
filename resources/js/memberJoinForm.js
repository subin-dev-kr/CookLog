/***** 회원 아이디 중복확인 *****/
//메세지 영역
const idMsg = document.getElementById("idCheckMsg");
 //버튼잡기
const checkIdBtn = document.getElementById("checkIdBtn");
//이벤트 주기 & 함수호출
checkIdBtn.addEventListener("click", idCheck);
//함수정의 (클릭 시 실행))
function idCheck() {

	const mId = document.getElementById("mId").value;

	if (!mId) {
		idMsg.style.color = "red";
		idMsg.innerText = "아이디를 입력해주세요!";
		return;
	}
	
	checkId(mId); //중복체크 함수 호출
	
}
// 응답 -> 문자열
function convertToText(res) { //res = response(응답)
	return res.text(); //문자열로 변환
}
// 결과처리
function handleCheckIdResult(result) {
	if(result === "OK") {
		idMsg.innerText = "사용 가능한 아이디 입니다.";
		idMsg.style.color = "green";
	} else if (result === "DUP") {
		idMsg.innerText = "이미 사용중인 아이디 입니다.";
		idMsg.style.color = "red";
	} else {
		idMsg.innerText = "서버오류";
		idMsg.style.color = "red";
	}
	
}
//아이디 중복 확인 요청
function checkId(mId) {
    fetch(contextPath + "/member/checkId?mId=" + encodeURIComponent(mId))
        .then(convertToText)
        .then(handleCheckIdResult);
}	

/***** 회원 비밀번호 재확인 *****/
//메세지 영역
const pwMsg = document.getElementById("pwCheckMsg");
//버튼 잡기
const pwCheckBtn = document.getElementById("pwCheckBtn");
//이벤트 주기
pwCheckBtn.addEventListener("click", pwCheck);
//함수정의
function pwCheck(){
	const pw = document.getElementById("mPw").value;
	const pwConfirm = document.getElementById("mPwConfirm").value;
	const pwMsg = document.getElementById("pwCheckMsg");
	
	if(pw === "" || pwConfirm === "") { //=== 은 데이터랑 타입 둘다 같은지 확인
		
		pwMsg.innerText = "비밀번호를 입력하세요";
		pwMsg.style.color = "red";
		return;
	}
	if(pw === pwConfirm) {
		pwMsg.innerText = "비밀번호가 일치합니다.";
		pwMsg.style.color = "green";
	} else {
		pwMsg.innerText = "비밀번호가 일치하지 않습니다.";
		pwMsg.style.color = "red";
	}
	
}
/***** 회원 닉네임 중복확인 *****/
//메세지 영역
const nickMsg = document.getElementById("nickCheckMsg");
//버튼 잡기
const checkNickBtn = document.getElementById("checkNickBtn");
//이벤트 주기
checkNickBtn.addEventListener("click", nickCheck);
//함수정의
function nickCheck (){
	const nick = document.getElementById("mNickName").value;
	
	if(nick === "") {
		nickMsg.innerText = "닉네임을 입력 하세요.";
		nickMsg.style.color = "red"
		return;
		
	}
	
	checkNickName(nick);
}

//결과처리함수
function handleCheckNickResult(result) {
	
	if(result === "OK") {
		nickMsg.innerText = "사용 가능한 닉네임 입니다.";
		nickMsg.style.color = "green";
	} else if (result === "DUP") {
		nickMsg.innerText = "이미 사용 중인 닉네임 입니다.";
		nickMsg.style.color = "red";
	} else {
		nickMsg.innerText = "서버오류";
		nickMsg.style.color = "red";
	}
}
//닉네임 중복확인 함수
function checkNickName(nick) {
    fetch(contextPath + "/member/checkNickName?mNickName=" + encodeURIComponent(nick))
        .then(convertToText)
        .then(handleCheckNickResult);
}


/***** 이메일 중복체크 *****/
//메세지 영역
const emailMsg = document.getElementById("emailCheckMsg");
//버튼잡기
const checkEmailBtn = document.getElementById("checkEmailBtn");
//이벤트 주기
checkEmailBtn.addEventListener("click", emailCheck);
//함수정의
function emailCheck() {
	
	const email = document.getElementById("email").value;
	
	if(email === "") {
		
		emailMsg.innerText = "이메일을 입력하세요.";
		emailMsg.style.color = "red";
		return;
	}
	
	checkEmail(email);
}

//결과처리 함수
function handleCheckEmailResult(result) {
	
	if(result === "OK") {
		
		emailMsg.innerText = "사용가능한 이메일입니다.";
		emailMsg.style.color = "green";
	} else if(result === "DUP") {
		
		emailMsg.innerText = "이미 사용중인 이메일 입니다.";
		emailMsg.style.color = "red";
	} else {
		
		emailMsg.innerText = "서버오류";
		emailMsg.style.color = "red";
	}
}
//이메일 중복 함수
function checkEmail(email) {
	
	fetch(contextPath + "/member/checkEmail?email=" + encodeURIComponent(email))
		.then(convertToText)
		.then(handleCheckEmailResult);
}
