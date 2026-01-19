// 공지사항 목록이 들어있는 <ul> 요소를 가져옴
const list = document.querySelector(".notice-rolling-list");

//한번 이동할 거리
const itemHeight = 50;
// 롤링 멈춤 & 시작 관리용
let rollingInterval = null;

// 초기화 전용 함수
function initNoticeRolling() {
  const list = document.querySelector(".notice-rolling-list");
  const noticeBox = document.querySelector(".notice-rolling-box");

  // ⭐ 공지 영역 없는 페이지면 실행 안 함
  if (!list || !noticeBox) { //공지 영역 없는 페이지면 실행 안함
    return;
  }

  startRolling();
  addMouseEvents();
}

document.addEventListener("DOMContentLoaded", initNoticeRolling);

// 공지사항을 한 칸 위로 올리는 함수
function noticeRollingSlide() {
	// 리스트의 첫번째 <li>요소 가져오기
	const first = list.querySelector("li");
	
	// ul 전체를 위로 50px 움직이기 위한 애니메이션 설정
	list.style.transition = "transform 0.5s ease-out";
	// 음수(-) 값이 위쪽으로 이동하게 함
	list.style.transform = `translateY(-${itemHeight}px)`;
	
	//0.5초후 순서 바꾸고 초기화
	setTimeout(function() {
		// 첫 번재  li를 맨 뒤로 이동시키기
		list.appendChild(first);
		
		//transition 제거 (순간 이동 효과)
		list.style.transition = "none";
		
		//위치 초기화 (다음 슬라이드를 위해 0으로)
		list.style.transform = "translateY(0)";
	}, 500);
}
//자동 롤링 시작 함수
function startRolling() {
	
	if(rollingInterval !== null) {
		return;
	}
	rollingInterval = setInterval(noticeRollingSlide, 3000); // 3초마다 실행
}

function stopRolling() {
	// 인터벌이 존재하면 멈추기
	if(rollingInterval !== null) {
		
		clearInterval(rollingInterval);
		rollingInterval = null;
	}
}

// 마우스 올리면 멈춤, 마우스 치우면 동작
function addMouseEvents() {
	
	// 공지 영역에 전체에 적용
	const noticeBox = document.querySelector(".notice-rolling-box");
	console.log("noticeBox", noticeBox);
	// 마우스 올림 -> 정지
	noticeBox.addEventListener("mouseenter", function(){
		console.log("mouse enter detected");
		stopRolling();
	});
	
	// 마우스 치움 -> 다시 실행
	noticeBox.addEventListener("mouseleave", function(){
		console.log("mouse leave detected");
		startRolling();
	});
}

document.addEventListener("DOMContentLoaded", function(){
	startRolling(); // 자동 롤링 시작
	addMouseEvents(); // hover 이벤트 등록
});

